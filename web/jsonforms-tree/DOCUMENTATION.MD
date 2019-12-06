This package provides a framework for the creation of tree master detail editors in Theia.
The detail is rendered with JSON Forms.

# Configuring a tree editor
To use the tree editor for a custom model, the following classes and interfaces need to be extended resp. implemented.

## JsonFormsTreeEditorWidget
The implementation of this abstract class is the the whole editor widget containing a tree and a detail
whose content depends on the node selected in the tree.

You need to implement `addNode` and `deleteNode` to define how nodes are added to and deleted from the model opened in the editor.
This includes notifying the tree which nodes it has to add resp. remove.

Furthermore, you need to implement `handleFormUpdate`. This is called whenever the user changes data in the detail view.

`save` needs to be overridden if the tree editor should react to the common save command (e.g. if the user presses Ctrl+S in Theia).
By default nothing is done when save is called.

If you want to implement an editor which uses a ressource which is loaded from a uri,
you should consider extending [NavigatableTreeEditorWidget](src/browser/navigatable-tree-editor-widget.ts).

## Services in the TreeEditor namespace
These services need to be implemented to define how the tree editor retrieves and presents the data.
All these interfaces and a definition of the tree editor's node model can be found in
[interfaces.ts](src/browser/interfaces.ts)

### TreeEditor.LabelProvider
The `LabelProvicer` specifies how the editor's tree derives nodes' names from their data.

### TreeEditor.NodeFactory
The `NodeFactory` specifies how tree nodes are created from instance data.

### TreeEditor.ModelService
The `ModelService` provides logic to get everything needed to render the detail for a tree node.
This includes providing the data, a schema for the data, and a UI schema specifying how the data is rendered.

# Registering a custom tree editor
To use the editor, you need to bind it in your frontend module (which exports a `ContainerModule`).
However, you should not bind your implementations directly to the interfaces provided
by the json forms tree package because this creates conflicts when when using multiple tree editor implementations in the same Theia instance.

Instead, we provided a utility method `createBasicTreeContainter` that creates a new child container of the current context
and binds all needed services inside the container.
Because the bindings are then encapsulated in the child container, other editors are not affected by them.
If you have additional services or values you need to bind for the creation of your editor,
you can also bind them in the child container.

The following code snipped shows a container module which registers a widget factory for a custom editor.

```typescript
export default new ContainerModule(bind => {
  // Bind services to themselves in case you use them in your extension outside of the editor, too.
  // In this example we only need the label provider somewhere else.
  bind(MyLabelProvider).toSelf();

  bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
    id: 'my-theia-tree-editor',
    createWidget: (options: NavigatableWidgetOptions) => {

      // This creates a new inversify Container with all the basic services needed for a theia tree editor.
      const treeContainer = createBasicTreeContainter(
        context.container,
        MyEditorWidget,
        MyModelService,
        MyLabelProvider,
        MyNodeFactory
      );

      // Our example tree editor needs additional options. So, we bind them in the container created before
      const uri = new URI(options.uri);
      treeContainer.bind(NavigatableTreeEditorWidgetOptions).toConstantValue({ uri });

      // Finally, we create a new editor by telling the container to retrieve an instance of our editor implementation
      return treeContainer.get(MyTreeEditorWidget);
    }
  }));
});
```

## Binding for use outside the tree editor widget
If you need to use your implementations of `TreeEditor.NodeFactory` or `TreeEditor.LabelProvider`,
you have two methods to register them safely.

### Bind to self
You can bind a service to itself. Then you can get them injected by referencing your implementation directly.
```typescript
// bind (e.g. when creating a ContainerModule)
bind(MyNodeFactory).toSelf();

// inject (e.g. in a constructor)
@inject(MyNodeFactory) factory: MyNodeFactory
```

### Bind to name
The second method is to the original interface and specifying a name constraint that needs to be provided when getting the service injected.
```typescript
// bind (e.g. when creating a ContainerModule)
bind(MyNodeFactory).to(TreeEditor.NodeFactory).whenTargetNamed("my-tree");

// inject (e.g. in a constructor)
@inject(TreeEditor.NodeFactory) @named("my-tree") factory: TreeEditor.NodeFactory
```
