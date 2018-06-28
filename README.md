# Coffee Editor Extension Example
The example of how to build the Theia-based applications with the tree-editor-extension.

## Getting started

Install [nvm](https://github.com/creationix/nvm#install-script).

    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash

Install npm and node.

    nvm install 8
    nvm use 8

Install yarn.

    npm install -g yarn

## First Time Setup and Running the browser example afterwards

    cd coffee-editor-extension
    yarn workspace coffee-editor-extension add ./theia-tree-editor-extension-1.0.0.tgz
    cd ..
    yarn install
    lerna run prepare (if it is not automatically triggered after `yarn install`)
    cd browser-app
    yarn start

Open http://localhost:3000 in the browser.

On the `File Menu`, open a valid UI Schema JSON file
On the `File Menu`, open a project and right click to a valid UI Schema JSON file and select `Open With -> Open With Tree Editor`

## Running the browser example

    lerna run prepare
    cd browser-app
    yarn start

Open http://localhost:3000 in the browser.

## Developing with the browser example

Open a new terminal and start watching of the browser example. It may take time to rebuild the app.

    npx lerna run watch --scope=browser-app --include-filtered-dependencies --parallel

Open a new terminal and start browser example

    cd browser-app
    yarn start

For each change in coffee-editor-extension

**Reload the page**

Open http://localhost:3000 in the browser.

## Developing with the Electron example

Start watching of the coffee editor extension.

    cd coffee-editor-extension
    yarn watch


## Publishing coffee-editor-extension

Create a npm user and login to the npm registry, [more on npm publishing](https://docs.npmjs.com/getting-started/publishing-npm-packages).

    npm login

Publish packages with lerna to update versions properly across local packages, [more on publishing with lerna](https://github.com/lerna/lerna#publish).

    npx lerna publish
