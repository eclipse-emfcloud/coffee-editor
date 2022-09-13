<?xml version="1.0" encoding="UTF-8"?>
<org.eclipse.emfcloud.coffee.model:Machine
    xmi:version="2.0"
    xmlns:xmi="http://www.omg.org/XMI"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:org.eclipse.emfcloud.coffee.model="http://www.eclipse.org/emfcloud/coffee/model"
    id="bb9b7c44-9838-4335-b986-043382534130"
    xsi:schemaLocation="http://www.eclipse.org/emfcloud/coffee/model Coffee.ecore"
    name="SuperBrewer3000">
  <children xsi:type="org.eclipse.emfcloud.coffee.model:BrewingUnit" id="4edecb17-80bf-4938-949b-723468322ecf">
    <children xsi:type="org.eclipse.emfcloud.coffee.model:ControlUnit" id="5058c1f8-d292-49b5-ac24-8efba98e242a">
      <processor
          id="099634c6-c759-4da4-b482-902a0f85a711"
          clockSpeed="5"
          numberOfCores="10"
          socketconnectorType="Z51"
          thermalDesignPower="100"/>
      <display
          id="b99b3974-e3da-490a-ac69-ef921c0f9707"
          width="10"
          height="20"/>
    </children>
  </children>
  <workflows id="47501d49-3a8d-4cb2-bed5-6263f5e5733b" name="BrewingFlow">
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:AutomaticTask" id="6e30dc6e-cc62-4287-a6d4-2b25e58e427d" name="Preheat"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:AutomaticTask" id="254d603a-f76c-4a22-8912-b87ed51b620d" name="Brew"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:ManualTask" id="8de0c429-c144-4492-938b-6bb0968eabd3" name="AlternativeBrew"/>
    <nodes xsi:type="org.eclipse.emfcloud.coffee.model:Decision" id="a73092cf-37e8-4bc3-a36d-5e9609c2ca2d"/>
    <flows id="3b43103b-cc04-45af-99d9-1940687109e9" source="6e30dc6e-cc62-4287-a6d4-2b25e58e427d" target="a73092cf-37e8-4bc3-a36d-5e9609c2ca2d"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.model:WeightedFlow" id="f410d003-8f18-4caf-bdef-5e1478094611" source="a73092cf-37e8-4bc3-a36d-5e9609c2ca2d" target="8de0c429-c144-4492-938b-6bb0968eabd3" probability="high"/>
    <flows xsi:type="org.eclipse.emfcloud.coffee.model:WeightedFlow" id="727e2f4a-69ba-47f2-b1b0-1e7b7df564a9" source="a73092cf-37e8-4bc3-a36d-5e9609c2ca2d" target="254d603a-f76c-4a22-8912-b87ed51b620d" probability="medium"/>
  </workflows>
</org.eclipse.emfcloud.coffee.model:Machine>
