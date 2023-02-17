# Coffee Editor IDE - Backend

The backend is implemented as an Eclipse product. The custom plugins are located in `plugins/` and `releng/` contains build information like the target platform and the product configuration.

## Build the backend

There are two ways to build the backend.

1.  Running the following command in the root folder of the coffee-editor IDE

        yarn build:backend

2.  Use maven directly and build the parent project

        cd releng/org.eclipse.emfcloud.coffee.parent
        mvn clean verify

**Note:** To use the newly built backend in the coffee-editor IDE you also need to copy it to the frontend after building it.
This can be done by running the command `yarn copy:servers` in the root folder.
If you run `yarn build` in the root folder, all necessary build steps are executed automatically (backend and client are built and the backend artifacts are copied to the client).

## Update backend versions

To update the versions of all backend modules, run the following command, using the specific version, e.g. `0.8.0-SNAPSHOT`.

    cd releng/org.eclipse.emfcloud.coffee.parent
    mvn tycho-versions:set-version -DnewVersion=<VERSION>
