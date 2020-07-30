# Docker

## Build

Make sure that you have Java10+ and Node10 installed

run `./build.sh`

This will create the image as `coffee-editor:latest`

## Run

`docker run -p 0.0.0.0:3000:3000 --rm coffee-editor`

Go to [http://localhost:3000](URL)

## Stop

Run `docker ps` to get the container id, then run `docker stop <container-id>`

## Debugging

Run `docker ps` to get the container id, then run `docker exec -it <container-id> /bin/bash` in order to get to the bash in the running container.

## Cleanup

    docker rm  $(docker ps -q -a)
    docker rmi $(docker images -f "dangling=true" -q)
