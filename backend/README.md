# Coffee Editor IDE - Backend

The backend is implemented as an Eclipse product. The custom plugins are located in `plugins/` and `releng/` contains build information like the target platform and the product configuration.

## Build the backend
There are two ways to build the backend.
1. Running `./run.sh -b` in the root folder of the coffee-editor ide
2. Use maven directly and build the project releng/org.eclipse.emfcloud.coffee.target

**Note:** To use the newly built backend in the coffee-editor IDE you also need to copy it to the frontend after building it. This can be done with running `./run.sh -c` in the root folder.
