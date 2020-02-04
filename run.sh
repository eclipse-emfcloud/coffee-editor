#!/bin/bash

echo "$(date +"[%T.%3N]") Evaluate Options... "
buildBackend='false'
copyBackend='false'
buildFrontend='false'
forceFrontend='false'
runFrontend='false'

if [[ "$1" == "" ]]; then
  buildBackend='true'
  copyBackend='true'
  buildFrontend='true'
  runFrontend='true'
fi

if [[ ${#1} -gt 2 ]]; then
  if [[ "$1" == -*"b"* ]]; then
    buildBackend='true'
  fi
  if [[ "$1" == -*"c"* ]]; then
    copyBackend='true'
  fi

  if [[ "$1" == -*"f"* ]]; then
    buildFrontend='true'
  fi
  if [[ "$1" == -*"r"* ]]; then
    runFrontend='true'
  fi
  if [[ "$1" == -*"ff"* ]]; then
    forceFrontend='true'
  fi
fi

while [ "$1" != "" ]; do
  case $1 in
    -b | --backend )  buildBackend='true'
                      ;;
    -c | --copy )     copyBackend='true'      
                      ;;
    -f | --frontend ) buildFrontend='true'
                      ;;
    -ff | --forcefrontend ) forceFrontend='true'
                      ;;
    -r | --run )      runFrontend='true'
                      ;;
  esac
  shift
done

[[ "$buildBackend" == "true" ]] && echo "  Build Backend (-b)" || echo "  Do not build Backend (-b)"
[[ "$copyBackend" == "true" ]] && echo "  Copy Backend (-c)" || echo "  Do not copy Backend (-c)"
[[ "$forceFrontend" == "true" ]] && echo "  Remove yarn.lock (-ff)" || echo "  Do not remove yarn.lock  (-ff)"
[[ "$buildFrontend" == "true" ]] && echo "  Build Frontend (-f)" || echo "  Do not build Frontend (-f)"
[[ "$runFrontend" == "true" ]] && echo "  Run Frontend (-r)" || echo "  Do not run Frontend (-r)"

if [ "$buildBackend" == "true" ]; then
  echo "$(date +"[%T.%3N]") Build backend products"
  cd backend/releng/com.eclipsesource.coffee.parent/
  mvn clean install -Pfatjar -U
  cd ../../../
fi

if [ "$copyBackend" == "true" ]; then
  productPath=''
  if [[ "$OSTYPE" == "linux-gnu" ]]; then
	productPath='linux/gtk'
	echo "Running on Linux"
  elif [[ "$OSTYPE" == "darwin"* ]]; then
        # Mac OSX
	productPath='macosx\cocoa'
	echo "Running on Mac"
  elif [[ "$OSTYPE" == "cygwin" ]]; then
        # POSIX compatibility layer and Linux environment emulation for Windows
	productPath='win32\win32'
	echo "Running on Windows with Cygwin"
  elif [[ "$OSTYPE" == "msys" ]]; then
        # Lightweight shell and GNU utilities compiled for Windows (part of MinGW)
	productPath='win32\win32'
	echo "Running on Windows with Msys"
  fi
  echo "$productPath"
  echo "$(date +"[%T.%3N]") Copy built products..."

  inputCodeGen=backend/releng/com.eclipsesource.coffee.product/target/products/com.eclipsesource.coffee.product.codegen/$productPath/x86_64
  outputCodeGen=web/coffee-java-extension/server
  echo "  $(date +"[%T.%3N]") Copy CodeGen to '$outputCodeGen'."
  rm -rf $outputCodeGen && mkdir -p $outputCodeGen && cp -rf $inputCodeGen $outputCodeGen

  inputWorkflowAnalyzer=backend/releng/com.eclipsesource.coffee.product/target/products/com.eclipsesource.coffee.product.workflow.analyzer/$productPath/x86_64
  outputWorkflowAnalyzer=web/coffee-workflow-analyzer/server
  echo "  $(date +"[%T.%3N]") Copy WorkflowAnalyzer to '$outputWorkflowAnalyzer'."
  rm -rf $outputWorkflowAnalyzer && mkdir -p $outputWorkflowAnalyzer && cp -rf $inputWorkflowAnalyzer $outputWorkflowAnalyzer

  inputWorkflowDSL=backend/releng/com.eclipsesource.coffee.product/target/products/com.eclipsesource.coffee.product.workflow.dsl/$productPath/x86_64
  outputWorkflowDSL=web/coffee-workflow-analyzer-editors/server
  echo "  $(date +"[%T.%3N]") Copy WorkflowDSL to '$outputWorkflowDSL'."
  rm -rf $outputWorkflowDSL && mkdir -p $outputWorkflowDSL && cp -rf $inputWorkflowDSL $outputWorkflowDSL

  inputWorkflowGLSP=backend/plugins/com.eclipsesource.workflow.glsp.server/target/com.eclipsesource.workflow.glsp.server-0.0.1-SNAPSHOT-glsp.jar
  outputWorkflowGLSP=web/coffee-server/build
  echo "  $(date +"[%T.%3N]") Copy WorkflowGLSPServer to '$outputWorkflowGLSP'."
  rm -rf $outputWorkflowGLSP && mkdir -p $outputWorkflowGLSP && cp -rf $inputWorkflowGLSP $outputWorkflowGLSP

  inputCoffeeMS=backend/plugins/com.eclipsesource.coffee.modelserver/target/com.eclipsesource.coffee.modelserver-0.1.0-SNAPSHOT-standalone.jar
  outputCoffeeMS=web/coffee-server/build
  echo "  $(date +"[%T.%3N]") Copy CoffeeModelServer to '$outputCoffeeMS'."
  cp -rf $inputCoffeeMS $outputCoffeeMS


  echo "$(date +"[%T.%3N]") Copy finished."
fi

if [ "$downloadServers" == "true" ]; then
  cd ./web/coffee-server/scripts/
  ./download-server.sh
  cd ../../../
fi

if [ "$forceFrontend" == "true" ]; then
  cd web/
  rm -f ./yarn.lock
  cd ..
fi

if [ "$buildFrontend" == "true" ]; then
  cd web/
  yarn
  cd ..
fi

if [ "$runFrontend" == "true" ]; then
  workspace=$(pwd)
  (sleep 5 && firefox 127.1:3000/#/${workspace:1}/backend/examples/SuperBrewer3000)&
  cd web/browser-app
  yarn start --hostname 0.0.0.0
fi

