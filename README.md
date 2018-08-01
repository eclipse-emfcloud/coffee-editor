# Coffee Editor Extension Example
An example of how to build the Theia-based applications with the tree-editor-extension.

## Getting started

Install [nvm](https://github.com/creationix/nvm#install-script).

    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash

Install npm and node.

    nvm install 8
    nvm use 8

Install yarn.

    npm install -g yarn

## First Time Setup

    git clone https://github.com/eclipsesource/coffee-editor.git
    cd coffee-editor/coffee-editor-extension

`theia-tree-editor-extension-1.0.0.tgz` is a temporary solution to add tree editor extension until theia-tree-editor-extension is published
Please check this [link](https://github.com/eclipsesource/theia-tree-editor) to learn how to create theia-tree-editor-extension tarball

    yarn workspace coffee-editor-extension add ./theia-tree-editor-extension-1.0.0.tgz
    cd ..
    yarn install

## Running the browser example

    lerna run prepare
    cd browser-app
    yarn start

Open http://localhost:3000 in the browser.

On the `File Menu`, open a JSON file
On the `File Menu`, open a project and right click to a JSON file and select `Open With -> Open With Tree Editor`


## Developing with the browser example

Open a new terminal and start watching of the browser example. It may take time to rebuild the app.

    cd path_to_root_directory/browser-app
    yarn watch

Open a new terminal and start watching of coffee-editor-extension.

    cd path_to_root_directory/coffee-editor-extension
    yarn watch

Open a new terminal and start browser example

    cd path_to_root_directory/browser-app
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
