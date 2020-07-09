# Coffee Editor IDE

An example of how to build the Theia-based applications with the tree-editor-extension.
The coffee-editor consists of a frontend and a backend.

The frontend is located in the `web/` folder and frontend specific documentation can be found in the [frontend README](web/README.md)
The backend is located in the `backend/` folder and backend specific documentation can be found in the [backend README](backend/README.md)

## Used Projects

We are relying on a bunch of projects:

- https://github.com/eclipsesource/jsonforms
- https://github.com/eclipse-glsp/glsp
- https://github.com/eclipse-emfcloud/emfcloud-modelserver
- https://github.com/eclipse-emfcloud/emfcloud-modelserver-theia
- https://github.com/eclipse-emfcloud/theia-tree-editor

If you encounter issues please report them in the corresponding project.
This project should not contain much code and should mostly consist of 'glue' code to combine the different components.

## Prerequisites

### Install [nvm](https://github.com/creationix/nvm#install-script).

    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash

### Install npm and node.

    nvm install 10
    nvm use 10

### Install yarn.

    npm install -g yarn

### Install maven

Please check the installation documentation for [maven](http://maven.apache.org/install.html).

On Ubuntu you can use:
`sudo apt-get install maven`

### Install linux packages (if necessary).

    sudo apt-get install g++-4.8 libsecret-1-dev xvfb libx11-dev libxkbfile-dev libxml2-utils

### Install python (needed from theia dependencies):

Please check the installation description [here](https://github.com/nodejs/node-gyp#installation).

On Windows the most reliable way seems to be to install Python and set `npm config set python "C:\Path\To\python.exe"`.

## Getting started

Clone and build the coffee-editor:

    git clone https://github.com/eclipsesource/coffee-editor.git
    cd coffee-editor
    ./run.sh

Run the built coffee-editor:

    ./run.sh -r

Open http://localhost:3000 in the browser.

In Theia open the example workspace `backend/examples/SuperBrewer3000` and double click a `.coffee` file. This opens it in a tree master detail editor.

## The build and run script

The `run.sh` script provides funtionality to build the coffee-editor, download used libraries, and run the IDE.
Every part step can be executed independently from each other by using the corresponding paramater:

`-b`: Builds the backend services

`-c`: Integrates the built backend artifacts in the coffee-editor IDE

`-f`: Builds the frontend shown in the web browser

`-r`: Runs the coffee-editor and exposes it at http://localhost:3000

## Publishing the coffee-editor-extension

Create a npm user and login to the npm registry, [more on npm publishing](https://docs.npmjs.com/getting-started/publishing-npm-packages).

    npm login

Publish packages with lerna to update versions properly across local packages, [more on publishing with lerna](https://github.com/lerna/lerna#publish).

    npx lerna publish

## Debug the application

### Debug Backend

- Install Eclipse
- Import projects from `backend`
- Set target `com.eclipsesource.coffee.target.target`

#### Code Generation

You cannot debug the code generation. In order to debug it, you need to execute the `com.eclipsesource.coffee.product.codegen` with the correct parameters.

#### Workflow Analyzer

In order to debug, start the `com.eclipsesource.coffee.product.workflow.analyzer` product in debug mode. The root application is : `com.eclipsesource.workflow.analyzer.application.Application`.

Please make sure to set the `--WF_ANALYZER=5083` parameter to the browser app. In the backend you need to pass `-port` and `-host` with parameters as application arguments.

You can also simply use the predefined `WorkflowAnalyzerServer.launch` run config.

#### Workflow DSL

In order to debug, start the `com.eclipsesource.coffee.product.workflow.dsl` product in debug mode.
Please make sure to add `--WF_LSP=5017` parameter to the browser app. In the backend you need to pass `-startSocket` as an application argument.

You can also use the predefined `RunSocketServer-Headless.launch` run config.

#### Coffee Model Server

Use the `com.eclipsesource.coffee.modelserver.CoffeeModelServerLauncher` class to start the Model Server.

#### Coffee GLSP Server

Use the `com.eclipsesource.glsp.example.modelserver.workflow.WorkflowModelServerGLSPServerLauncher` class to start the GLSP Server.

### Debug Frontend

- Install VSCode
- Import projects from `web`

#### Debug Theia Backend

Use the `Debug Browser Backend` launch config inside VSCode.

#### Debug Theia Frontend

Use the `Launch Browser Frontend` launch config inside VSCode. This will open Chrome and attach a debug listener.
