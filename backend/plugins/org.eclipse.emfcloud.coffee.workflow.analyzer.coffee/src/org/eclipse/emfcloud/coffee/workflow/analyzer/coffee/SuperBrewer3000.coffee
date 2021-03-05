<?xml version="1.0" encoding="UTF-8"?>
<org.eclipse.emfcloud.coffee.modelserver.coffee.model:Machine
    xmi:version="2.0"
    xmlns:xmi="http://www.omg.org/XMI"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:org.eclipse.emfcloud.coffee.modelserver.coffee.model="http://www.eclipse.org/emfcloud/coffee/model"
    xsi:schemaLocation="http://www.eclipse.org/emfcloud/coffee/model Coffee.ecore"
    name="Super Brewer 3000">
  <children
      xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:BrewingUnit"/>
  <children
      xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:ControlUnit">
    <processor
        clockSpeed="5"
        numberOfCores="10"
        socketconnectorType="Z51"
        thermalDesignPower="100"/>
    <display
        width="10"
        height="20"/>
  </children>
  <workflows>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:AutomaticTask" name="Preheat"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:AutomaticTask" name="Brew"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:ManualTask" name="AlternativeBrew"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:Decision"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.0" target="//@workflows.0/@nodes.3"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.3" target="//@workflows.0/@nodes.2" probability="high"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.3" target="//@workflows.0/@nodes.1" probability="medium"/>
  </workflows>
</org.eclipse.emfcloud.coffee.modelserver.coffee.model:Machine>
