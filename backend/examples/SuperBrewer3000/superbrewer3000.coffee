<?xml version="1.0" encoding="ASCII"?>
<org.eclipse.emfcloud.modelserver.coffee.model:Machine xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:org.eclipse.emfcloud.modelserver.coffee.model="http://www.eclipsesource.com/modelserver/example/coffeemodel" name="SuperBrewer3000">
  <children xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:BrewingUnit">
    <children xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:ControlUnit">
      <processor vendor="Qualcomm" clockSpeed="5" numberOfCores="10" socketconnectorType="Z51" thermalDesignPower="1000"/>
      <dimension width="10" height="12" length="13"/>
      <display width="10" height="20"/>
    </children>
  </children>
  <workflows name="BrewingFlow">
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:AutomaticTask" name="Preheat"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:AutomaticTask" name="Brew"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:ManualTask" name="Refill water"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:ManualTask" name="Drink"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:ManualTask" name="Push"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:ManualTask" name="Check drip tray"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:AutomaticTask" name="Check Water"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:AutomaticTask" name="Water Ok"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:Decision"/>
    <nodes xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:Merge"/>
    <flows source="//@workflows.0/@nodes.4" target="//@workflows.0/@nodes.6"/>
    <flows source="//@workflows.0/@nodes.6" target="//@workflows.0/@nodes.8"/>
    <flows xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.8" target="//@workflows.0/@nodes.2"/>
    <flows xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.8" target="//@workflows.0/@nodes.7"/>
    <flows source="//@workflows.0/@nodes.2" target="//@workflows.0/@nodes.9"/>
    <flows source="//@workflows.0/@nodes.7" target="//@workflows.0/@nodes.9"/>
    <flows source="//@workflows.0/@nodes.9" target="//@workflows.0/@nodes.5"/>
    <flows source="//@workflows.0/@nodes.5" target="//@workflows.0/@nodes.0"/>
    <flows source="//@workflows.0/@nodes.0" target="//@workflows.0/@nodes.1"/>
    <flows xsi:type="org.eclipse.emfcloud.modelserver.coffee.model:WeightedFlow" source="//@workflows.0/@nodes.1" target="//@workflows.0/@nodes.3"/>
  </workflows>
</org.eclipse.emfcloud.modelserver.coffee.model:Machine>
