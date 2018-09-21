#!/bin/bash

echo "$(date +"[%T.%3N]") Evaluate Options... "
buildBackend='false'
copyBackend='false'
buildFrontend='false'
runFrontend='false'

while [ "$1" != "" ]; do
  case $1 in
    -b | --backend )  buildBackend='true'
                      ;;
    -c | --copy )     copyBackend='true'
                      ;;
    -f | --frontend ) buildFrontend='true'
                      ;;
    -r | --run )      runFrontend='true'
                      ;;
  esac
  shift
done
[[ "$buildBackend" == "true" ]] && echo "  Build Backend (-b)" || echo "  Do not build Backend (-b)"
[[ "$copyBackend" == "true" ]] && echo "  Copy Backend (-c)" || echo "  Do not copy Backend (-c)"
[[ "$buildFrontend" == "true" ]] && echo "  Build Frontend (-f)" || echo "  Do not build Frontend (-f)"
[[ "$runFrontend" == "true" ]] && echo "  Run Frontend (-r)" || echo "  Do not run Frontend (-r)"

if [ "$buildBackend" == "true" ]; then
  echo "$(date +"[%T.%3N]") Build backend products"
  cd backend/releng/com.eclipsesource.coffee.parent/
  mvn clean install
  cd ../../../
fi

if [ "$copyBackend" == "true" ]; then
  echo "$(date +"[%T.%3N]") Copy built products..."

  inputCodeGen=backend/releng/com.eclipsesource.coffee.product/target/products/com.eclipsesource.coffee.product.codegen/linux/gtk/x86_64
  outputCodeGen=web/coffee-java-extension/server
  echo "  $(date +"[%T.%3N]") Copy CodeGen to '$outputCodeGen'."
  mkdir -p $outputCodeGen && cp -rf $inputCodeGen $outputCodeGen

  inputWorkflowAnalyzer=backend/releng/com.eclipsesource.coffee.product/target/products/com.eclipsesource.coffee.product.workflow.analyzer/linux/gtk/x86_64
  outputWorkflowAnalyzer=web/coffee-workflow-analyzer/server
  echo "  $(date +"[%T.%3N]") Copy WorkflowAnalyzer to '$outputWorkflowAnalyzer'."
  mkdir -p $outputWorkflowAnalyzer && cp -rf $inputWorkflowAnalyzer $outputWorkflowAnalyzer

  inputWorkflowDSL=backend/releng/com.eclipsesource.coffee.product/target/products/com.eclipsesource.coffee.product.workflow.dsl/linux/gtk/x86_64
  outputWorkflowDSL=web/coffee-workflow-analyzer-editors/server
  echo "  $(date +"[%T.%3N]") Copy WorkflowDSL to '$outputWorkflowDSL'."
  mkdir -p $outputWorkflowDSL && cp -rf $inputWorkflowDSL $outputWorkflowDSL

  echo "$(date +"[%T.%3N]") Copy finished."
fi

if [ "$buildFrontend" == "true" ]; then
  cd web/
  yarn
  cd ..
fi

if [ "$runFrontend" == "true" ]; then
  cd web/browser-app
  yarn start
fi
