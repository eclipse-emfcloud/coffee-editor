# Coffee Editor IDE

An example of how to build the Theia-based tools including graphical editos, form-based editors, tree-based editors, textual DSLs, model analyisis, debugging and more. The coffee editor is part of the [emf.cloud](https://www.eclipse.org/emfcloud/) project.
Please visit the [emf.cloud home page](https://www.eclipse.org/emfcloud/#coffeeeditoroverview) for an overview of all features and an [online live demonstration](https://eclipsesource.com/coffee-editor)!

> **Note** that this branch is an example of how to add a new concept to the Coffee Model underpinning the editor, with all of the consequences that result from end to end of the application. More on that [below in this document](#example-of-adding-a-model-concept).

## Project Structure

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

### Java

You need Java 11 to build the Coffee Editor.

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
- Set target `org.eclipse.emfcloud.coffee.target.target`

#### Code Generation

You cannot debug the code generation. In order to debug it, you need to execute the `org.eclipse.emfcloud.coffee.product.codegen` with the correct parameters.

#### Workflow Analyzer

In order to debug, start the `org.eclipse.emfcloud.coffee.product.workflow.analyzer` product in debug mode. The root application is : `org.eclipse.emfcloud.coffee.workflow.analyzer.application.Application`.

Please make sure to set the `--WF_ANALYZER=5083` parameter to the browser app. In the backend you need to pass `-port` and `-host` with parameters as application arguments.

You can also simply use the predefined `WorkflowAnalyzerServer.launch` run config.

#### Workflow DSL

In order to debug, start the `org.eclipse.emfcloud.coffee.product.workflow.dsl` product in debug mode.
Please make sure to add `--WF_LSP=5017` parameter to the browser app. In the backend you need to pass `-startSocket` as an application argument.

You can also use the predefined `RunSocketServer-Headless.launch` run config.

#### Coffee Model Server

Use the `org.eclipse.emfcloud.coffee.modelserver.CoffeeModelServerLauncher` class to start the Model Server.

#### Coffee GLSP Server

Use the `org.eclipse.emfcloud.coffee.glsp.example.modelserver.workflow.WorkflowModelServerGLSPServerLauncher` class to start the GLSP Server.
On the client side, set the `isRunning` flag of the [CoffeeGlspLaunchOptions](web/coffee-server/src/node/backend-module.ts) to `true`.

### Debug Frontend

- Install VSCode
- Import projects from `web`

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

## Example of Adding a Model Concept

This branch is an example demonstrating how to add a new concept to the Coffee Model that underpins this editor, as specified in
[Issue #315](/eclipse-emfcloud/coffee-editor/issues/315). The idea is that the branch consists of a single commit that can be
conveniently compared with its parent in the GitHub UI to browse the set of changes required for complete support of a new
model concept that in this example is a new kind of workflow task: the `MenuSelectionTask`.

Below is a rough guide to the changes, by subject matter area.

### The Model Definition

In module `backend/plugins/org.eclipse.emfcloud.coffee.model`:

- [`model/Coffee.ecore`](backend/plugins/org.eclipse.emfcloud.coffee.model/model/Coffee.ecore): add the EClass defining the `MenuSelectionTask` concept, with attributes that it adds to what it inherits from `ManualTask`
- [`model/Coffee.genmodel`](backend/plugins/org.eclipse.emfcloud.coffee.model/model/Coffee.ecore): updated to implement code generation for the new `MenuSelectionTask` concept
- classes in the `org.eclipse.emfcloud.coffee` package and its sub-packages are re-generated from the model

### GLSP Integration

The GLSP back-end needs to be made aware of the new `MenuSelectionTask` concept, including bindings of the command to create a new instance of it.

In module `backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app`:

- classes [`ModelTypes`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/util/ModelTypes.java) and [`CoffeeTypeUtil`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/model/CoffeeTypeUtil.java) are updated to add mappings of the back-end `MenuSelectionTask` class to and from the GLSP node type representing it
- class [`CreateMenuSelectionTaskHandler`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/handler/operation/CreateMenuSelectionTaskHandler.java) is added to define the command for creation of instances of the new `MenuSelectionTask` concept. This is used in the diagram editor for an optional shortcut keystroke to create new instances
- class [`WorkflowGLSPModule`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/WorkflowGLSPModule.java) adds the `CreateMenuSelectionTaskHandler` class to the editor bindings
- class [`WorkflowContextMenuItemProvider`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/WorkflowContextMenuItemProvider.java) is updated to add a new action to the form editor's **New Child** context menu to create instances of the new `MenuSelectionTask` concept
- class [`WorkflowCommandPaletteActionProvider`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/WorkflowCommandPaletteActionProvider.java) is updated to add a new tool to the diagram editor's _palette_ to create instances of the new `MenuSelectionTask` concept
- class [`WorkflowDiagramNotationConfiguration`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/WorfklowDiagramNotationConfiguration.java) is updated to add GLSP type mappings and node-type hint for the new `MenuSelectionTask`. Actually, the way in which this is done here is to make the mapping registrations more generic so that it will cover future changes without further modification
- class [`WorkflowBuilder`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.glsp.server.app/src/org/eclipse/emfcloud/coffee/workflow/glsp/server/util/WorkflowBuilder.java) is updated to customize the single typographic character used as the "icon" for a `MenuSelectionTask` in the diagram editor. This is not always necessary as the default algorithm uses the first letter of the object's class name, but as `ManualTask` and `MenuSelectionTask` both have names starting with M, that algorithm here makes an exception for the latter to use S instead (for "selection")

### Web Front-end Integration

The previous section outlines the back-end changes required to add the new `MenuSelectionTask` class to the protocols used for communication with the
form and diagram editors. This section sketches the front-end changes to leverage those protocol mappings.

In module `web/coffee-editor-extension` for the tree-based form editor:

- [`coffee-model.ts`](web/coffee-editor-extension/src/browser/coffee-tree/coffee-model.ts) adds a declaration of the `MenuSelectionTask` class to the `CoffeeModel::Type` namespace and registers it in the list of workflow nodes (it not being aworkflow edge nor a coffee machine component)
- [`coffee-schemas.ts`](web/coffee-editor-extension/src/browser/coffee-tree/coffee-schemas.ts) adds the JSON schema for two models:
  - the JSON representation of the Ecore `MenuSelectionTask` class in EMF-JSON format, including the class delcaration, its properties, and the reference that contains it in the `Workflow` class
  - a `menuSelectionTaskView` class that is the form editor's _view model_ describing the form controls to edit a `MenuSelectionTask`
- [`coffee-tree-label-provider-contribution.ts`](web/coffee-editor-extension/src/browser/coffee-tree/coffee-tree-label-provider-contribution.ts) adds two mappings for the presentation of labels for `MenuSelectionTask` objects in the form editor:
  - a mapping of an icon to show in the tree (using the Font Awesome icon set)
  - a case for the common calculation of the label of a `MenuSelectionTask` in the treeas presenting the task name
- [`coffee-model-service.ts`](web/coffee-editor-extension/src/browser/coffee-tree/coffee-model-service.ts) adds the mapping of the _view model_ for the `MenuSelectionTask` class

In module `web/coffee-workflow-sprotty` for the GLSP diagram editor:

- [`diagram.css`](web/coffee-workflow-sprotty/css/diagram.css) adds styling for the `menuselection` CSS class applied by the back-end to nodes of `MenuSelectionTask` type
- [`workflow-views.tsx`](web/coffee-workflow-sprotty/src/workflow-views.tsx) adds a mapping of the `menuselection` CSS class to the GLSP `task:menuselection` node type
- [`di.config.ts`](web/coffee-workflow-sprotty/src/di.config.ts) adds registration of the `TaskNode` node type and `TaskNodeView` view type in GLSP for the `task:menuselection` node type

#### Workflow Analyzer

To support visualization of the workflow analysis, it would be nice to show the new menu selection task type in
a distinct colour in the chart. To that end, the back-end needs to be updated to add a new task type to the
JSON that it provides to the chart viewer:

- [`AnalyzeWorkflow.java`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.analyzer.coffee/src/org/eclipse/emfcloud/coffee/workflow/analyzer/coffee/AnalyzeWorkflow.java) adds a mapping of the `MenuSelectionTask` model class to the a new `menuselection` task type in the JSON
- the [`index.html`](web/coffee-workflow-analyzer/wf-analyzer-web-app/index.html) page of the Worklow Analyzer app adds this new `menuselection` task type to its map of `colors`, mapping it to the same dark sea green used in the diagram editor styling

### Workflow Code Generation

As is, the code generation emits source files for `MenuSelectionTask` identical to what it generates for
`ManualTask`s because the menu selection is a specializtion of the manual task. But the `MenuSelectionTask`
adds properties that could be useful to access in the workflow, so this example adds a code generation
template specific to it:

- [`MenuSelectionWorkflowTaskGenerator.xtend`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.generator.java/src/org/eclipse/emfcloud/coffee/workflow/generator/java/MenuSelectionWorkflowTaskGenerator.xtend) generates the abstract contract of the classes generated for each menu selection task in the workflow. It generates the `library/MenuSelectionWorkflowTask.java` file in the `src-gen/` folder
- [`JavaWorkflowGenerator.java`](backend/plugins/org.eclipse.emfcloud.coffee.workflow.generator.java/src/org/eclipse/emfcloud/coffee/workflow/generator/java/JavaWorkflowGenerator.java) is updated to generate the `MenuSelectionWorkflowTask` abstract class described in the previous item
- [TaskGenerator.xtend](backend/plugins/org.eclipse.emfcloud.coffee.workflow.generator.java/src/org/eclipse/emfcloud/coffee/workflow/generator/java/TaskGenerator.xtend) is updated to generate the implementations of the abstract API in the `MenuSelectionWorkflowTask` abstract class described in the first item in each of the classes generated in the `src-gen/` for the menu selection tasks in the model. This does incidentally include a minor refactoring of the string templates for the class contents to use Xtend's _dispatch methods_ to switch on the task type in a more maintainable way, with dispatch case added as appropriate for the `MenuSelectionTask`

### Example Model

The back-end includes an example workspace with an example Coffee model instance to try out in the editor. An instance of the new
`MenuSelectionTask` class is added to this example for demonstration purposes:

In module `backend/examples/SuperBrewer3000`:

- [`superbrewer3000.coffee`](backend/examples/SuperBrewer3000/superbrewer3000.coffee) adds an instance of the `MenuSelectionTask` into the workflow graph with edges connecting to and from it
- [`superbrewer3000.coffeenotation`](backend/examples/SuperBrewer3000/superbrewer3000.coffeenotation) includes the new task in the diagram layout
