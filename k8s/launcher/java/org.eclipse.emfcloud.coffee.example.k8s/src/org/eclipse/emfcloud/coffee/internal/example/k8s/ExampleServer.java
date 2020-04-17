package org.eclipse.emfcloud.coffee.internal.example.k8s;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.BatchV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.models.V1Job;
import io.kubernetes.client.models.V1JobBuilder;
import io.kubernetes.client.models.V1JobList;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1PodStatus;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1ServiceBuilder;
import io.kubernetes.client.models.V1ServiceList;
import io.kubernetes.client.util.ClientBuilder;

@Path("/launch")
@Component(name = "ExampleServer", service = ExampleServer.class, immediate = true)
public class ExampleServer {

	private static final String NAMESPACE = "coffee-instance";

	private static String COFFEE_EDITOR_IMAGE_VERSION;
	private static int MAX_CONCURRENT_USERS;
	private static int MIN_AVAILABLE_SERVICES; // minimum available ips
	private static long JOB_CLEANUP_TIMEFRAME_MINUTES; // timeframe for when to cleanup resources
	private static long ACTIVE_DEADLINE_SECONDS; // minutes until Theia is brought down
	private static long CLEANUP_TIMEFRAME_HOURS; // timeframe for when to cleanup resources when this pod gets evicted
	private static Boolean DEBUGGING;

	private static String getStringProperty(String key, String def) {
		String property = System.getProperty(key);
		if (property == null) {
			log("init", MessageFormat.format("No property for key {0} found. Using {1}", key, def));
			return def;
		}
		log("init", MessageFormat.format("Property for key {0}: {1}", key, property));
		return property;
	}

	private static int getIntProperty(String key, int def) {
		String property = System.getProperty(key);
		if (property == null) {
			log("init", MessageFormat.format("No property for key {0} found. Using {1}", key, def));
			return def;
		}
		try {
			int intValue = Integer.valueOf(property).intValue();
			log("init", MessageFormat.format("Property for key {0}: {1}", key, intValue));
			return intValue;
		} catch (NumberFormatException e) {
			log("init", MessageFormat.format("Could not transform property for key {0}: {2}. Using {1} instead", key,
					def, property));
			return def;
		}
	}

	private static long getLongProperty(String key, long def) {
		String property = System.getProperty(key);
		if (property == null) {
			log("init", MessageFormat.format("No property for key {0} found. Using {1}", key, def));
			return def;
		}
		try {
			long longValue = Long.valueOf(property).longValue();
			log("init", MessageFormat.format("Property for key {0}: {1}", key, longValue));
			return longValue;
		} catch (NumberFormatException e) {
			log("init", MessageFormat.format("Could not transform property for key {0}: {2}. Using {1} instead", key,
					def, property));
			return def;
		}
	}

	private static boolean getBooleanProperty(String key, boolean def) {
		String property = System.getProperty(key);
		if (property == null) {
			log("init", MessageFormat.format("No property for key {0} found. Using {1}", key, def));
			return def;
		}
		try {
			Boolean valueOf = Boolean.valueOf(property);
			log("init", MessageFormat.format("Property for key {0}: {1}", key, valueOf));
			return valueOf;
		} catch (Exception e) {
			log("init", MessageFormat.format("Could not transform property for key {0}: {2}. Using {1} instead", key,
					def, property));
			return def;
		}

	}

	static {
		COFFEE_EDITOR_IMAGE_VERSION = getStringProperty("coffee.editor", "0.7.8");
		MAX_CONCURRENT_USERS = getIntProperty("max.concurrent.users", 10);
		MIN_AVAILABLE_SERVICES = getIntProperty("min.available.services", 11);
		JOB_CLEANUP_TIMEFRAME_MINUTES = getLongProperty("job.cleanup.minutes", 15l);
		ACTIVE_DEADLINE_SECONDS = getLongProperty("job.ttl.seconds", 30 * 60l);
		CLEANUP_TIMEFRAME_HOURS = getLongProperty("cleanup.hours", 6);
		DEBUGGING = getBooleanProperty("debugging", false);
	}

	private Queue<AvailableService> availableServices = new LinkedBlockingQueue<>();
	private Set<String> spawningServiceNames = new CopyOnWriteArraySet<>();
	private int spawningServices = 0;

	private Map<String, AvailableService> servicesInUse = new ConcurrentHashMap<>();

	private ExecutorService requestSpawner = Executors.newCachedThreadPool();
	private ExecutorService serviceSpawner = Executors.newFixedThreadPool(10);

	private static class AvailableService {
		String uuid;
		String ip;

		AvailableService(String uuid, String ip) {
			this.uuid = uuid;
			this.ip = ip;
		}

		public String uuid() {
			return uuid;
		}
	}

	private static class CoffeeLaunchException extends Exception {
		public CoffeeLaunchException(String msg) {
			super(msg);
		}

		private static final long serialVersionUID = -6425915796611641245L;

	}

	private synchronized int incSpawn(int minAvailableServices) {
		int inc = minAvailableServices - spawningServices - availableServices.size();
		if (inc < 1) {
			return 0;
		}
		spawningServices += inc;
		return inc;
	}

	private synchronized void decSpawn() {
		spawningServices -= 1;
	}

	private synchronized void addAvailableServiceAndDecSpawn(String uuid, String ip) {
		availableServices.add(new AvailableService(uuid, ip));
		spawningServiceNames.remove(createServiceName(uuid));
		decSpawn();
	}

	@Activate
	public void init() throws IOException, ApiException {
		ApiClient client = ClientBuilder.cluster().build();
		client.setDebugging(DEBUGGING);
		Configuration.setDefaultApiClient(client);

		spawnServices("init", MIN_AVAILABLE_SERVICES);

		startWatchingJobs();
		startCleanupWatch();
	}

	@GET
	@Produces("text/plain")
	public void startCoffeeEditorInstance(@Suspended final AsyncResponse response) throws IOException, ApiException {
		requestSpawner.execute(() -> {
			try {
				String result = doStartCoffeeEditorInstance();
				Response jaxrs = Response.ok(result).type(MediaType.TEXT_PLAIN).build();
				response.resume(jaxrs);
			} catch (CoffeeLaunchException e) {
				Response.status(503).entity(e.getMessage()).build();
				Response jaxrs = Response.status(503).entity(e.getMessage()).build();
				response.resume(jaxrs);
			}
		});
	}

	private String doStartCoffeeEditorInstance() throws CoffeeLaunchException {
		String requestUUID = UUID.randomUUID().toString();
		try {
			// TODO unit test this
			// TODO clean up evicted pods
			// TODO change props in this file to come from kubernetes configuration
			// TODO what happens when this pod get killed? how do we clean up
			// TODO shuffle exposed port

			log(requestUUID,
					MessageFormat.format("Handling the request on thread {0}", Thread.currentThread().getName()));
			log(requestUUID, MessageFormat.format("Currently there are {0} services in use. Max is {1}",
					servicesInUse.size(), MAX_CONCURRENT_USERS));

			if (servicesInUse.size() >= MAX_CONCURRENT_USERS) {
				log(requestUUID, "Too many users");
				throw new CoffeeLaunchException("Too many concurrent users at the moment. Please try again later.");
			}

			/*
			 * first make sure that we have IPs available, so spawn up some service for
			 * future requests
			 */
			spawnServices(requestUUID, MIN_AVAILABLE_SERVICES + 1 /* +1 because we will take one */);

			/* pick one of the dedicated services */
			AvailableService serviceDescription = getDedicatedService(requestUUID);

			/* start up job */
			String jobName = createJob(requestUUID, serviceDescription.uuid());

			servicesInUse.put(jobName, serviceDescription);

			if (!waitForJobPod(requestUUID, jobName)) {
				throw new CoffeeLaunchException(
						"There was a problem with your request, please try again. If it continues to fail, please try again later");
			}

			waitForURL(requestUUID, "http://" + serviceDescription.ip + ":4000/");

			return "http://" + serviceDescription.ip + ":4000/#/coffee-editor/backend/examples/SuperBrewer3000";
		} catch (Exception e) {
			if (e instanceof CoffeeLaunchException) {
				throw (CoffeeLaunchException) e;
			}
			log(requestUUID, "There was an exception:");
			e.printStackTrace();

			throw new CoffeeLaunchException(
					"There was a problem with your request, please try again. If it continues to fail, please try again later");
		}
	}

	private void waitForURL(String requestUUID, String url) {
		for (int i = 0; i < 150; i++) {
			try {
				final URLConnection connection = new URL(url).openConnection();
				connection.connect();
				return;
			} catch (final MalformedURLException e) {
			} catch (final IOException e) {
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
		log(requestUUID, MessageFormat.format("Waiting for url {0} timed out".toUpperCase(), url));
	}

	private boolean waitForJobPod(String requestUUID, String jobName) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1500; i++) {
			try {
				// TODO watch
				Thread.sleep(200);
				CoreV1Api coreV1Api = new CoreV1Api();
				V1PodList podList = coreV1Api.listNamespacedPod(NAMESPACE, Boolean.TRUE, null, null, null, null, null,
						null, null, Boolean.FALSE);
				for (V1Pod pod : podList.getItems()) {
					String name = pod.getMetadata().getGenerateName();
					if (name == null) {
						continue;
					}
					if (!name.startsWith(jobName)) {
						continue;
					}
					V1PodStatus status = pod.getStatus();
					if (status == null) {
						continue;
					}
					String phase = status.getPhase();
					if ("Running".equals(phase)) {
						log(requestUUID, MessageFormat.format("Waiting for job {0} to be running took {1}ms", jobName,
								(System.currentTimeMillis() - start)));
						return true;
					}
				}
			} catch (InterruptedException e) {
			} catch (ApiException e) {
			}
		}
		log(requestUUID, MessageFormat.format("Waiting for job {0} to be running failed and took {1}ms".toUpperCase(),
				jobName, (System.currentTimeMillis() - start)));
		return false;
	}

	private void spawnServices(String requestUUID, int minAvailableServices) throws ApiException {
		log(requestUUID, MessageFormat.format("Currently we have {0} available services", availableServices.size()));
		int servicesToSpawn = incSpawn(minAvailableServices);
		log(requestUUID, MessageFormat.format("Spawning {0} additional services", servicesToSpawn));
		for (int i = 0; i < servicesToSpawn; i++) {
			String uuid = UUID.randomUUID().toString();
			long start = System.currentTimeMillis();
			String serviceName = createService(uuid);
			addToAvailableOnceReady(uuid, serviceName);
			log(requestUUID, MessageFormat.format("Initiating spawning a service with uuid {0} took {1}ms", uuid,
					(System.currentTimeMillis() - start)));
		}
	}

	private void addToAvailableOnceReady(String uuid, String serviceName) {
		serviceSpawner.execute(() -> {
			try {
				for (int i = 0; i < 3600; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					CoreV1Api coreV1Api = new CoreV1Api();
					V1ServiceList listNamespacedService = coreV1Api.listNamespacedService(NAMESPACE, Boolean.TRUE, null,
							null, null, null, null, null, null, Boolean.FALSE);
					for (V1Service v1Service : listNamespacedService.getItems()) {
						if (serviceName.equals(v1Service.getMetadata().getName())) {
							if (v1Service.getStatus().getLoadBalancer() == null
									|| v1Service.getStatus().getLoadBalancer().getIngress() == null) {
								break;
							}
							String ip = v1Service.getStatus().getLoadBalancer().getIngress().get(0).getIp();
							if (ip != null && !ip.isEmpty()) {
								log("init", MessageFormat.format("Adding a new service. {0} are available",
										availableServices.size() + 1));
								addAvailableServiceAndDecSpawn(uuid, ip);
								return;
							}
							break;
						}
					}
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}
			decSpawn();
			log("init", MessageFormat.format("Adding a new service with id {0} failed. {1} are available".toUpperCase(),
					uuid, availableServices.size()));
		});
	};

	private AvailableService getDedicatedService(String requestUUID) {
		log(requestUUID, MessageFormat.format("Currently we have {0} available services", availableServices.size()));
		long start = System.currentTimeMillis();
		AvailableService dedicatedService = availableServices.poll();
		while (dedicatedService == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			dedicatedService = availableServices.poll();
		}
		log(requestUUID, MessageFormat.format("Getting a dedicated service to use with id {0} took {1}ms",
				dedicatedService.uuid(), (System.currentTimeMillis() - start)));
		return dedicatedService;
	}

	private void startWatchingJobs() {
		new Thread(this::watchJobs).start();
	}

	private void startCleanupWatch() {
		new Thread(this::cleanUpWatch).start();
	}

	private void cleanUpWatch() {
		try {
			Thread.sleep(TimeUnit.MINUTES.toMillis(5));
		} catch (InterruptedException e) {
		}

		while (true) {
			Set<String> knownServiceUUIDs = Stream.concat(//
					Stream.concat(//
							availableServices.stream().map(AvailableService::uuid), //
							servicesInUse.values().stream().map(AvailableService::uuid))
							.map(ExampleServer.this::createServiceName), //
					spawningServiceNames.stream())

					.collect(Collectors.toSet());

			CoreV1Api coreV1Api = new CoreV1Api();
			try {
				V1ServiceList listNamespacedService = coreV1Api.listNamespacedService(NAMESPACE, Boolean.TRUE, null,
						null, null, null, null, null, null, Boolean.FALSE);
				Set<String> unknownServices = new LinkedHashSet<>();
				for (V1Service v1Service : listNamespacedService.getItems()) {
					if (knownServiceUUIDs.contains(v1Service.getMetadata().getName())) {
						continue;
					}
					unknownServices.add(v1Service.getMetadata().getName());
				}

				BatchV1Api batchV1Api = new BatchV1Api();
				V1JobList listNamespacedJob = batchV1Api.listNamespacedJob(NAMESPACE, Boolean.TRUE, null, null, null,
						null, null, null, null, Boolean.FALSE);
				for (V1Job v1Job : listNamespacedJob.getItems()) {
					String serviceName = v1Job.getMetadata().getName().replace("coffee-editor-demo-job",
							"coffee-editor-demo-service");
					if (unknownServices.contains(serviceName)) {
						// job for unknown service. is this job still running?
						if (v1Job.getStatus() != null) {
							Integer activePods = v1Job.getStatus().getActive();
							if (activePods != null && activePods > 0) {
								unknownServices.remove(serviceName);
								System.out.println(serviceName + " is unknown but there is a running job");
							}
						}

					}
				}
				unknownServices.forEach(ExampleServer.this::deleteServiceWithName);
			} catch (Exception e) {
			}

			try {
				Thread.sleep(TimeUnit.HOURS.toMillis(CLEANUP_TIMEFRAME_HOURS));
			} catch (InterruptedException e) {
			}
		}
	}

	private void watchJobs() {
		try {
			while (true) {
				try {
					Thread.sleep(TimeUnit.MINUTES.toMillis(JOB_CLEANUP_TIMEFRAME_MINUTES));
				} catch (InterruptedException e) {
				}
				if (servicesInUse.isEmpty()) {
					continue;
				}

				Set<String> finishedJobs = new LinkedHashSet<>(servicesInUse.keySet());

				/* check if job associated with a service in use has finished */
				BatchV1Api batchV1Api = new BatchV1Api();
				V1JobList listNamespacedJob = batchV1Api.listNamespacedJob(NAMESPACE, Boolean.TRUE, null, null, null,
						null, null, null, null, Boolean.FALSE);
				for (V1Job v1Job : listNamespacedJob.getItems()) {
					/* filter out the running jobs */
					if (v1Job.getStatus() != null) {
						Integer activePods = v1Job.getStatus().getActive();
						if (activePods != null && activePods > 0) {
							finishedJobs.remove(v1Job.getMetadata().getName());
						}
					}
				}

				for (String job : finishedJobs) {
					AvailableService service = servicesInUse.remove(job);
					if (service != null) {
						deleteService(service.uuid());
					}
					deleteJob(job);
				}

			}
		} catch (Exception e) {
		}
	}

	private void deleteJob(String job) {
		BatchV1Api batchV1Api = new BatchV1Api();
		try {
			batchV1Api.deleteNamespacedJob(job, NAMESPACE, null, null, null, null, null, null);
		} catch (ApiException e) {
		} catch (Exception e) {
		}
	}

	private void deleteService(String uuid) {
		deleteServiceWithName("coffee-editor-demo-service-" + uuid);
	}

	private void deleteServiceWithName(String name) {
		System.err.println("DELETING SERVICE WITH NAME " + name);
		CoreV1Api coreV1Api = new CoreV1Api();
		try {
			coreV1Api.deleteNamespacedService(name, NAMESPACE, null, null, null, null, null, null);
		} catch (ApiException e) {
		} catch (Exception e) {
		}
	}

	private String createServiceName(String uuid) {
		String name = "coffee-editor-demo-service-" + uuid;
		return name;
	}

	private String createService(String uuid) throws ApiException {
		CoreV1Api coreV1Api = new CoreV1Api();
		String name = createServiceName(uuid);
		spawningServiceNames.add(name);
		V1Service service = new V1ServiceBuilder()//
				.withApiVersion("v1")//
				.withNewMetadata()//
				/**/.withName(name)//
				/**/.withLabels(Collections.singletonMap("app", "coffee-editor-" + uuid))//
				/**/.withNamespace(NAMESPACE).endMetadata()//
				.withNewSpec()//
				/**/.withExternalTrafficPolicy("Cluster")//
				/**/.addNewPort()//
				/*    */.withPort(4000)//
				/*    */.withProtocol("TCP")//
				/*    */.withTargetPort(new IntOrString(3000)).endPort()//
				/**/.withSelector(Collections.singletonMap("app", "coffee-editor-" + uuid))//
				/**/.withSessionAffinity("None")//
				/**/.withType("LoadBalancer").endSpec().build();
		coreV1Api.createNamespacedService(NAMESPACE, service, null, null, null);
		return name;
	}

	private String createJob(String requestUUID, String uuid) throws ApiException {
		long start = System.currentTimeMillis();
		BatchV1Api batchV1Api = new BatchV1Api();
		V1Job v1Job = new V1JobBuilder()//
				.withApiVersion("batch/v1")//
				.withNewMetadata()//
				/**/.withName("coffee-editor-demo-job-" + uuid)//
				/**/.withLabels(Collections.singletonMap("app", "coffee-editor-" + uuid))//
				/**/.withNamespace(NAMESPACE).endMetadata()//
				.withNewSpec()//
				/**/.withBackoffLimit(1)//
				/**/.withActiveDeadlineSeconds(ACTIVE_DEADLINE_SECONDS)//
				/**/.withTtlSecondsAfterFinished(60)//
				/**/.withNewTemplate()//
				/*    */.withNewMetadata()//
				/*        */.withLabels(Collections.singletonMap("app", "coffee-editor-" + uuid)).endMetadata()//
				/*    */.withNewSpec()//
				/*        */.withRestartPolicy("Never")//
				/*        */.withAutomountServiceAccountToken(false)//
				/*        */.addNewContainer()//
				/*            */.withName("coffee-editor-demo")//
				/*            */.withImage("eu.gcr.io/kubernetes-238012/coffee-editor:" + COFFEE_EDITOR_IMAGE_VERSION)//
				/*            */.withNewResources()//
				/*                */.addToRequests("memory", Quantity.fromString("1.5G"))//
				/*                */.addToLimits("memory", Quantity.fromString("2G")).endResources()//
				/*            */.addNewPort()//
				/*                */.withContainerPort(3000).endPort().endContainer().endSpec().endTemplate().endSpec()
				/*                */.build();
		batchV1Api.createNamespacedJob(NAMESPACE, v1Job, true, "true", null);
		log(requestUUID, MessageFormat.format("Creating a job with UUID {0} took {1}ms", uuid,
				(System.currentTimeMillis() - start)));
		return "coffee-editor-demo-job-" + uuid;
	}

	private static void log(String requestUUID, String msg) {
		System.out.println(MessageFormat.format("REQ[{0}] --- {1}", requestUUID, msg));
	}

}
