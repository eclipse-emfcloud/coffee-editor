#!/bin/bash
echo [~] CLEAN-UP [~]
rm -f com.eclipsesource.modelserver.example-0.0.1-20190731.133707-35-standalone.jar

echo [~] BUILDING COFFEE-EDITOR [~]
cd ..
./run.sh -b -c -d -f 

echo [~] REPLACE MODELSERVER EXAMPLE [~]
cd dockerfiles
wget https://oss.sonatype.org/content/repositories/snapshots/com/eclipsesource/modelserver/com.eclipsesource.modelserver.example/0.0.1-SNAPSHOT/com.eclipsesource.modelserver.example-0.0.1-20190731.133707-35-standalone.jar
cp -f com.eclipsesource.modelserver.example-0.0.1-20190731.133707-35-standalone.jar ../web/coffee-server/build/com.eclipsesource.modelserver.example-0.0.1-SNAPSHOT-standalone.jar 

echo [~] BUILD DOCKER [~]
cd ..
docker build --tag=coffee-editor .
