# Coffee Editor Extension Example
An example of how to build the Theia-based applications with the tree-editor-extension.
The coffee-editor consists of a frontend and a backend.

The frontend is located in the `web/` folder and frontend specific documentation can be found in the [backend README](backend/README.md)

The backend is located in the `backend/` folder and backend specific documentation can be found in the [frontend README](web/README.md)

## Used Projects
We are relying on a bunch of projects:
* https://github.com/eclipsesource/jsonforms
* https://github.com/eclipsesource/graphical-lsp
* https://github.com/eclipsesource/modelserver
* https://github.com/eclipsesource/modelserver-theia

If you encounter issues please report them in the corresponding project.
This project should not contain much code and should mostly consist of 'glue' code to combine the different components.

## Prerequisites

Install [nvm](https://github.com/creationix/nvm#install-script).

    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash

Install npm and node.

    nvm install 10
    nvm use 10

Install yarn.

    npm install -g yarn

Install linux packages (if necessary).

    sudo apt-get install g++-4.8 libsecret-1-dev xvfb libx11-dev libxkbfile-dev

## Getting started

Clone and build the coffee-editor:

    git clone https://github.com/eclipsesource/coffee-editor.git
    cd coffee-editor
    ./run.sh -b -c -d -f
    
Run the built coffee-editor:

    ./run.sh -r

Open http://localhost:3000 in the browser.

On the `File Menu`, open a project and double click a `.coffee` file. This opens it in a tree master detail editor.

## The build and run script
The `run.sh` script provides funtionality to build the coffee-editor, download used libraries, and run the IDE.
Every part step can be executed independently from each other by using the corresponding paramater:

`-b`: Builds the backend services

`-c`: Integrates the built backend artifacts in the coffee-editor IDE

`-d`: Downloads the current version of the Model and GLSP servers. These are not required for building the coffee-editor but are used at runtime

`-f`: Builds the frontend shown in the web browser

`-r`: Runs the coffee-editor and exposes it at http://localhost:3000

## Publishing the coffee-editor-extension

Create a npm user and login to the npm registry, [more on npm publishing](https://docs.npmjs.com/getting-started/publishing-npm-packages).

    npm login

Publish packages with lerna to update versions properly across local packages, [more on publishing with lerna](https://github.com/lerna/lerna#publish).

    npx lerna publish
