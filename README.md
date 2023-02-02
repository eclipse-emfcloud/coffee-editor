# Eclipse EMF.cloud Coffee Editor IDE

An **example** of how to build the Theia-based tools including graphical editos, form-based editors, tree-based editors, textual DSLs, model analyisis, debugging and more. The coffee editor is part of the [emf.cloud](https://www.eclipse.org/emfcloud/) project.
Please visit the [emf.cloud home page](https://www.eclipse.org/emfcloud/#coffeeeditoroverview) for an overview of all features and an [online live demonstration](https://eclipsesource.com/coffee-editor)!

If you have questions, contact us on our [discussions page](https://github.com/eclipse-emfcloud/emfcloud/discussions)
and have a look at our [communication and support options](https://www.eclipse.org/emfcloud/contact/).

## Running Coffee Editor example

To run the coffee editor locally, we recommend to use a Docker image.

### Building and running the Docker image

To build the docker container run the following command once:

    docker build -t "coffee-editor:latest" .

or use the provided yarn script:

    yarn build:docker

Now you can start the Theia Demo app using the following command (or change the host port to your preferences):

    docker run -it -p 0.0.0.0:3000:3000 coffee-editor:latest

or use the provided yarn script:

    yarn docker:run

Next, open a browser pointing to <http://localhost:3000/#/coffee-editor/client/workspace/SuperBrewer3000> in your host.
You should see a Theia application with an example project being loaded.
Refer to the `Getting Started` widget in the right area of the application for more details on how to use the example project.

### Docker Image information

The docker image includes the Theia application and the following VSCode plugins (among others):

- Clangd for C/C++ editing support
- Debug for C/C++ debugging support
- Java LSP
- Java Debug

The runtime environment has installed (among others)

- CMake for building C/C++ projects
- GBD for debugging C/C++ projects

## Project Structure

The coffee-editor consists of a frontend and a backend.

The frontend is located in the [`client/`](./client/) folder and frontend specific documentation can be found in the [client README](client/README.md)
The backend is located in the [`backend/`](./backend/) folder and backend specific documentation can be found in the [backend README](backend/README.md)

## Used Projects

We are relying on a bunch of projects:

- <https://github.com/eclipsesource/jsonforms>
- <https://github.com/eclipse-glsp/glsp>
- <https://github.com/eclipse-emfcloud/emfcloud-modelserver>
- <https://github.com/eclipse-emfcloud/emfcloud-modelserver-theia>
- <https://github.com/eclipse-emfcloud/theia-tree-editor>

If you encounter issues please report them in the corresponding project.
This project should not contain much code and should mostly consist of 'glue' code to combine the different components.

## Prerequisites

The following libraries/frameworks need to be installed on your system:

|                                       |             |
| ------------------------------------- | ----------- |
| [Java](https://jdk.java.net/archive/) | `17`        |
| [Maven](https://maven.apache.org/)    | `>=3.8.6`   |
| [Node](https://nodejs.org/en/)        | `>=14.18.0` |

### Java

You need Java 17 to build the Coffee Editor.

### Install [nvm](https://github.com/creationix/nvm#install-script)

    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash

### Install npm and node

    nvm install 16
    nvm use 16

### Install yarn

    npm install -g yarn

### Install maven

Please check the installation documentation for [maven](http://maven.apache.org/install.html).

On Ubuntu you can use:
`sudo apt-get install maven`

### Install linux packages (if necessary)

    sudo apt-get install g++-4.8 libsecret-1-dev xvfb libx11-dev libxkbfile-dev libxml2-utils

### Install python (needed from theia dependencies)

Please check the installation description [here](https://github.com/nodejs/node-gyp#installation).

On Windows the most reliable way seems to be to install Python and set `npm config set python "C:\Path\To\python.exe"`.

## Getting started

Clone the coffee-editor:

    git clone https://github.com/eclipsesource/coffee-editor.git
    cd coffee-editor

Build the coffee-editor:

    yarn build

Run the built coffee-editor:

    yarn start

Open <http://localhost:3000> in the browser.

In Theia open the example workspace `client/workspace/SuperBrewer3000` and double click a `.coffee` file. This opens it in a tree master detail editor.

## Publishing the coffee-editor-extension

Create a npm user and login to the npm registry, [more on npm publishing](https://docs.npmjs.com/getting-started/publishing-npm-packages).

    npm login

Publish packages with lerna to update versions properly across local packages, [more on publishing with lerna](https://github.com/lerna/lerna#publish).

    npx lerna publish

## Debug the application

### Debug Backend

- Install Eclipse
- Import projects from `backend`
- Set target `org.eclipse.emfcloud.coffee.target.target`
- All backend applications are bundles as eclipse products and can be found in `org.eclipse.emfcloud.coffee.product`

#### Code Generation

To debug the code generation you would need to attach to the process when it is started from the backend.
Alternatively you can run the `codegen.product` or `codegen_cpp.product` with the correct parameters.

#### Workflow Analyzer

In order to debug, start the `workflowanalyzer.product` product in debug mode. The root application is : `org.eclipse.emfcloud.coffee.workflow.analyzer.application.application`.

Please make sure to set the `--WF_ANALYZER=5083` parameter to the browser app. In the backend you need to pass `-port` and `-host` with parameters as application arguments.

You can also simply use the predefined `WorkflowAnalyzerServer.launch` run config.

#### Workflow DSL

In order to debug, start the `workflowdsl.product` product in debug mode.
Please make sure to add `--WF_LSP=5017` parameter to the browser app. In the backend you need to pass `-startSocket` as an application argument.

You can also use the predefined `RunSocketServer-Headless.launch` run config.

#### Coffee Model Server

Use the `CoffeeModelServer` launch config to start the Model Server.

#### Coffee GLSP Server

Use the `WorkflowGLSPServer` launch config to start the GLSP Server.

### Debug Client

- Install VSCode
- Open folder `client`

#### Debug Theia Backend

Use the `Launch Browser Backend` launch config inside VSCode.

#### Debug Theia Frontend

Use the `Launch Browser Frontend` launch config inside VSCode. This will open Chrome and attach a debug listener.

## Example Branches

This project's repository has branches prefixed with `examples/` that serve to demonstrate common use cases of expansion or customization of
the capabilities of the frameworks on which the Coffee Editor is based. Each of these comprises a single commit that can be compared with its
base to see the changes from end to end that accomplish some developer use case. Often these examples will cross-cut the whole Coffee Editor
technology stack, but some will have a smaller focus. It all depends on the use case. The names of these branches are succinct but descriptive
of the use cases that they illustrate.

Because these examples are branches and not integrated into the main-line, they will be based on some past commit in the history of the
project. It is strongly encouraged when contributing changes to the Coffee Editor that, where such changes would meaningfully impact a use
case that is manifest in one of these example branches, that such example branch be rebased and updated as a part of that contribution.
Thus it is important that users of these example branches not base their own work on them (in git terms) because they **will be force-pushed**
from time to time, rewriting their history to rebase them on more recent baselines of the Coffee Editor.
