<?xml version="1.0" encoding="UTF-8"?>
<com.eclipsesource.modelserver.coffee.model:Machine
    xmi:version="2.0"
    xmlns:xmi="http://www.omg.org/XMI"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:com.eclipsesource.modelserver.coffee.model="http://www.eclipsesource.com/modelserver/example/coffeemodel"
    xsi:schemaLocation="http://www.eclipsesource.com/modelserver/example/coffeemodel Coffee.ecore"
    name="Super Brewer 3000">
  <children
      xsi:type="com.eclipsesource.modelserver.coffee.model:BrewingUnit"/>
  <children
      xsi:type="com.eclipsesource.modelserver.coffee.model:ControlUnit">
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
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="Preheat"/>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="Brew"/>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:ManualTask" name="AlternativeBrew"/>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:Decision"/>
    <flows xsi:type="com.eclipsesource.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.0" target="//@workflows.0/@nodes.3"/>
    <flows xsi:type="com.eclipsesource.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.3" target="//@workflows.0/@nodes.2" probability="high"/>
    <flows xsi:type="com.eclipsesource.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.3" target="//@workflows.0/@nodes.1" probability="medium"/>
  </workflows>
</com.eclipsesource.modelserver.coffee.model:Machine>
