<?xml version="1.0" encoding="ASCII"?>
<com.eclipsesource.modelserver.coffee.model:Machine xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.eclipsesource.modelserver.coffee.model="http://www.eclipsesource.com/modelserver/example/coffeemodel" name="SuperBrewer3000">
  <children xsi:type="com.eclipsesource.modelserver.coffee.model:BrewingUnit">
    <children xsi:type="com.eclipsesource.modelserver.coffee.model:ControlUnit">
      <processor vendor="Qualcomm" clockSpeed="5" numberOfCores="10" socketconnectorType="Z51" thermalDesignPower="1000"/>
      <dimension width="10" height="12" length="13"/>
      <display width="10" height="20"/>
    </children>
  </children>
  <workflows>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="Preheat"/>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="Brew"/>
    <flows xsi:type="com.eclipsesource.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.0" target="//@workflows.0/@nodes.1"/>
  </workflows>
  <workflows>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="Preheat 2"/>
    <nodes xsi:type="com.eclipsesource.modelserver.coffee.model:AutomaticTask" name="Brew 2"/>
    <flows xsi:type="com.eclipsesource.modelserver.coffee.model:WeightedFlow" source="//@workflows.1/@nodes.0" target="//@workflows.1/@nodes.1"/>
  </workflows>
</com.eclipsesource.modelserver.coffee.model:Machine>
