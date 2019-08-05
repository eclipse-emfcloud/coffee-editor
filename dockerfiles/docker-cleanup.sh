#!/bin/bash
docker rm  $(docker ps -q -a)
docker rmi $(docker images -f "dangling=true" -q)
