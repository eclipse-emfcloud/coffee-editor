<?xml version="1.0" encoding="UTF-8"?>
<com.eclipsesource.modelserver.coffee.model:Machine xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.eclipsesource.modelserver.coffee.model="http://www.eclipsesource.com/modelserver/example/coffeemodel" name="Super Brewer 3000">
  <children xsi:type="com.eclipsesource.modelserver.coffee.model:BrewingUnit"/>
  <children xsi:type="com.eclipsesource.modelserver.coffee.model:ControlUnit">
    <processor clockSpeed="5" numberOfCores="10" socketconnectorType="Z51" thermalDesignPower="100"/>
    <display width="10" height="20"/>
  </children>
  <workflows>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="PreHeat"/>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="Brew"/>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:ManualTask" name="CheckWt"/>
    <flows source="//@workflows.0/@nodes.0" target="//@workflows.0/@nodes.1"/>
    <flows xsi:type="com.eclipsesource.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.0" target="//@workflows.0/@nodes.1"/>
  </workflows>
</com.eclipsesource.modelserver.coffee.model:Machine>
