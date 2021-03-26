#!/bin/bash
echo [~] BUILDING COFFEE-EDITOR [~]
cd ..
./run.sh -b -c -d

echo [~] BUILD DOCKER [~]
docker build --tag=coffee-editor .
