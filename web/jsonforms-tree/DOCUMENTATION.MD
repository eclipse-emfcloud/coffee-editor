This is the usage documentation for the json forms tree editor.

# Configuring a tree editor
To use the tree editor for a custom model, the following classes and interfaces need to be extended resp. implemented.

## JsonFormsTreeEditorWidget
The implementation of this abstract class is the the whole editor widget containing a tree and a detail
whose content depends on the node selected in the tree.

You need to implement `addNode` and `deleteNode`define how nodes are added to and deleted from the model opened in the editor.
This includes notifying the tree which nodes it has to add resp. remove.

Furthermore, you need to implement `handleFormUpdate` to handle updating a tree node with new data.

`save` needs to be implemented if the tree editor should react to the common save command (e.g. if the user presses Ctrl+S in Theia).

## JsonFormsTree.LabelProvider
Implementing this specifies how the editor's tree derives nodes' names from their data.

## JsonFormsTree.NodeFactory
Implementing this specifies how tree nodes are created from instance data.

# Registering the custom tree editor
To use the editor, you need to bind it in your frontend module (which exports a `ContainerModule`).
However, you should not bind your implementations directly to the interfaces provided
by the json forms tree package because this creates conflicts when when using multiple tree editor implementations in the same Theia instance.

Instead, your tree editor implementation's symbol should be bound to a dynamic value
which creates the editor within an encapsulated child context.
This child context also needs to contain the `JsonFormsTreeWidget` used by the editor to render the tree.
For this, you should use the utility function `createJsonFormsTreeWidget`.
Such a registration could look like this:

```typescript
bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
  id: CoffeeTreeEditorWidget.WIDGET_ID,
  createWidget: (options: NavigatableWidgetOptions) => {
    const { container } = context;
    const child = container.createChild();

    // Create and bind tree widget only for this editor creation
    const tree = createJsonFormsTreeWidget(context.container, CoffeeTreeLabelProvider, CoffeeTreeNodeFactory);
    child.bind(JsonFormsTreeWidget).toConstantValue(tree);

    // Create and bind tree editor options
    const uri = new URI(options.uri);
    child
      .bind<JsonFormsTreeEditorWidgetOptions>(
        JsonFormsTreeEditorWidgetOptions
      )
      .toConstantValue({
        uri: uri
      });

    return child.get(CoffeeTreeEditorWidget);
  }
}));
```

## Binding for use outside the tree editor widget
If you need to use your implementations of `JsonFormsTree.NodeFactory` or `JsonFormsTree.LabelProvider`,
you have two methods to register them safely.

First, you can bind them to themselves. Then you can get them injected by referencing your implementation directly.
Bind like this:
```typescript
bind(CustomTreeNodeFactory).toSelf();
```
And inject likes this (e.g. in a constructor):
```typescript
@inject(CustomTreeNodeFactory) factory: CustomTreeNodeFactory
```

The second method is to bind an implementation to a name and add a name constraint to it.
```typescript
bind(CustomTreeNodeFactory).to(JsonFormsTree.NodeFactory).whenTargetNamed("custom-tree");
```
And inject likes this (e.g. in a constructor):
```typescript
@inject(JsonFormsTree.NodeFactory) @named("custom-tree") factory: JsonFormsTree.NodeFactor
```