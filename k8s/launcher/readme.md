# Readme

## Deployment steps

Setup kubectl to point to our cluster.

`mvn clean verify -f java/pom.xml`

`docker build --tag=coffee-editor-example .`

`docker tag coffee-editor-example eu.gcr.io/kubernetes-238012/coffee-editor-example`

`docker push eu.gcr.io/kubernetes-238012/coffee-editor-example`

`kubectl create serviceaccount api-service-account`

`kubectl create -f namespaces/coffee.json`

`kubectl create -f namespaces/instance.json`

`kubectl config set-context --current --namespace=coffee` or `kubectl config set-context --current --namespace=coffee-instance`

`kubectl create configmap coffee-config --from-env-file=config/coffee-env-file.properties -n coffee`

`kubectl apply -f k8s/`

`kubectl get services -w` and wait for the IP of the service


## Update docker image to use

Edit Config map

`kubectl scale --replicas=0 deployment/coffee-editor-deployment -n coffee`

`kubectl scale --replicas=1 deployment/coffee-editor-deployment -n coffee`

Go to [http://coffee-editor-deployment-service-external-ip:9091/services/launch](URL)