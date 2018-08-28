<?xml version="1.0" encoding="UTF-8"?>
<org.eclipse.emfforms.coffee.model:Machine xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:org.eclipse.emfforms.coffee.model="http://www.eclipse.org/emfforms/example/coffeemodel" name="Super Coffee 3000">
  <children xsi:type="org.eclipse.emfforms.coffee.model:ControlUnit">
    <activities name="start"/>
    <activities name="stop"/>
    <processor vendor="Intel 2" clockSpeed="2000" numberOfCores="2" thermalDesignPower="15"/>
    <ram clockSpeed="1600" size="2048"/>
    <display width="400" height="300"/>
  </children>
  <children xsi:type="org.eclipse.emfforms.coffee.model:BrewingUnit"/>
  <children xsi:type="org.eclipse.emfforms.coffee.model:WaterTank"/>
  <children xsi:type="org.eclipse.emfforms.coffee.model:DipTray"/>
</org.eclipse.emfforms.coffee.model:Machine>
