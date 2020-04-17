#!/bin/bash
echo [~] CLEAN-UP [~]
#rm -f com.eclipsesource.modelserver.example-0.0.1-20190731.133707-35-standalone.jar

echo [~] BUILDING COFFEE-EDITOR [~]
cd ..
./run.sh -b -c -d -f 

echo [~] BUILD DOCKER [~]
docker build --tag=coffee-editor .
