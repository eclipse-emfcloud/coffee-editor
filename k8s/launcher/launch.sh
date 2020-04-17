#!/bin/sh

echo Echo environment
echo $COFFEE_EDITOR
echo $MAX_CONCURRENT_USERS
echo $MIN_AVAILABLE_SERVICES
echo $JOB_CLEANUP_MINUTES
echo $JOB_TTL_SECONDS
echo $CLEANUP_HOURS
echo $DEBUGGING

java -Declipse.ignoreApp=true -Dosgi.noShutdown=true -Dorg.osgi.service.http.port=9091 -Dorg.eclipse.rwt.clientLibraryVariant=DEBUG -Dorg.eclipse.equinox.http.jetty.log.stderr.threshold=info -Dcoffee.editor=$COFFEE_EDITOR -Dmax.concurrent.users=$MAX_CONCURRENT_USERS -Dmin.available.services=$MIN_AVAILABLE_SERVICES -Djob.cleanup.minutes=$JOB_CLEANUP_MINUTES -Djob.ttl.seconds=$JOB_TTL_SECONDS -Dcleanup.hours=$CLEANUP_HOURS -Ddebugging=$DEBUGGING -jar /coffee-editor//plugins/org.eclipse.equinox.launcher_1.5.500.v20190715-1310.jar -os linux -ws gtk -arch -showsplash -name Eclipse -startup /coffee-editor//plugins/org.eclipse.equinox.launcher_1.5.500.v20190715-1310.jar --launcher.overrideVmargs -exitdata 888021 -consoleLog -console 
