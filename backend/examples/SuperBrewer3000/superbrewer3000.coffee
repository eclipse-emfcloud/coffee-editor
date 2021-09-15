<?xml version="1.0" encoding="ASCII"?>
<org.eclipse.emfcloud.coffee.model:Machine xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:org.eclipse.emfcloud.coffee.model="http://www.eclipse.org/emfcloud/coffee/model" xmi:id="_uTowcMnNEeuzYY8U8dVg6Q" name="SuperBrewer3000">
  <children xsi:type="org.eclipse.emfcloud.coffee.model:BrewingUnit" xmi:id="_glMiUMHlEeuOQ_oVeIeN4A">
    <children xsi:type="org.eclipse.emfcloud.coffee.model:ControlUnit" xmi:id="_OYWC8MqOEeuYap5GtvNPrw">
      <processor xmi:id="_fy6cYMqOEeuYap5GtvNPrw" vendor="Qualcommm" clockSpeed="5" numberOfCores="10" socketconnectorType="Z51" thermalDesignPower="1000"/>
      <dimension xmi:id="_sZx2QMqOEeuYap5GtvNPrw" width="10" height="12" length="13"/>
      <display xmi:id="_uj_EAMqOEeuYap5GtvNPrw" width="10" height="20"/>
    </children>
  </children>
  <workflows xmi:id="_KjaRcMnNEeuzYY8U8dVg6Q" name="BrewingFlow">
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:AutomaticTask" xmi:id="_5oz_cMKjEeup06HqRBVc_g" name="Preheat"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:AutomaticTask" xmi:id="_MrtooMKkEeup06HqRBVc_g" name="Brew"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:ManualTask" xmi:id="_O7Vh4MKkEeup06HqRBVc_g" name="Refill water"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:ManualTask" xmi:id="_8lEZIMqOEeuYap5GtvNPrw" name="Drink"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:ManualTask" xmi:id="_QxCfQMKkEeup06HqRBVc_g" name="Push"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:ManualTask" xmi:id="_itPXMMnYEeuzYY8U8dVg6Q" name="Check drip tray"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:AutomaticTask" xmi:id="_glMiUMHlEeuOQ_oVeIeN4A" name="Check Water"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:AutomaticTask" xmi:id="_jc7kcMHlEeuOQ_oVeIeN4A" name="Water Ok"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:Decision" xmi:id="_Y16bQMINEeuM9JtVafDACQ"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:Merge" xmi:id="_hd5kwMINEeuM9JtVafDACQ"/>
    <flows xmi:id="_tVvcoMHlEeuOQ_oVeIeN4A" source="//@workflows.0/@nodes.4" target="//@workflows.0/@nodes.6"/>
    <flows xmi:id="_7z8LkMHlEeuOQ_oVeIeN4A" source="//@workflows.0/@nodes.6" target="//@workflows.0/@nodes.8"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.model:WeightedFlow" xmi:id="_9dJlEMHlEeuOQ_oVeIeN4A" source="//@workflows.0/@nodes.8" target="//@workflows.0/@nodes.2"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.model:WeightedFlow" xmi:id="_OD_3AMHwEeuwMI7tijX71Q" source="//@workflows.0/@nodes.8" target="//@workflows.0/@nodes.7"/>
    <flows xmi:id="_O3eiYMHwEeuwMI7tijX71Q" source="//@workflows.0/@nodes.2" target="//@workflows.0/@nodes.9"/>
    <flows xmi:id="_TrV-YMIOEeuM9JtVafDACQ" source="//@workflows.0/@nodes.7" target="//@workflows.0/@nodes.9"/>
    <flows xmi:id="_V5JBgMKkEeup06HqRBVc_g" source="//@workflows.0/@nodes.9" target="//@workflows.0/@nodes.5"/>
    <flows xmi:id="_ar-eYMKkEeup06HqRBVc_g" source="//@workflows.0/@nodes.5" target="//@workflows.0/@nodes.0"/>
    <flows xmi:id="_cOp-gMj5EeuOnZSlSBIr0g" source="//@workflows.0/@nodes.0" target="//@workflows.0/@nodes.1"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.model:WeightedFlow" xmi:id="_c9G_8Mj5EeuOnZSlSBIr0g" source="//@workflows.0/@nodes.1" target="//@workflows.0/@nodes.3"/>
  </workflows>
</org.eclipse.emfcloud.coffee.model:Machine>
