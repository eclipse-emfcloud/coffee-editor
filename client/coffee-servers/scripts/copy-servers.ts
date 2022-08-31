/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import { join } from 'path';
import { copyBackendDirectory, copyBackendFile, getOSProductPath, log } from './copy-utils';

const start = new Date(Date.now());

// Model Server
const modelServerPath = join(__dirname, '..', '..', '..', 'backend', 'plugins', 'org.eclipse.emfcloud.coffee.modelserver');
const modelServerLogConfigPath = join(modelServerPath, 'log4j2-embedded.xml');
const modelServerJarPath = join(modelServerPath, 'target', 'org.eclipse.emfcloud.coffee.modelserver-0.1.0-SNAPSHOT-standalone.jar');

// GLSP Server
const glspServerPath = join(__dirname, '..', '..', '..', 'backend', 'plugins', 'org.eclipse.emfcloud.coffee.workflow.glsp.server');
const glspServerJarPath = join(glspServerPath, 'target', 'org.eclipse.emfcloud.coffee.workflow.glsp.server-0.1.0-SNAPSHOT-glsp.jar');

// Coffee products (Equinox servers)
const coffeeProductsPath = join(__dirname, '..', '..', '..', 'backend', 'releng', 'org.eclipse.emfcloud.coffee.product', 'target', 'products');
const osProductPath = getOSProductPath();
const workflowDSLServerPath = join(coffeeProductsPath, 'org.eclipse.emfcloud.coffee.product.workflow.dsl', osProductPath, 'x86_64');
const workflowAnalyerPath = join(coffeeProductsPath, 'org.eclipse.emfcloud.coffee.product.workflow.analyzer', osProductPath, 'x86_64');
const javaCodeGenServerPath = join(coffeeProductsPath, 'org.eclipse.emfcloud.coffee.product.codegen', osProductPath, 'x86_64');
const cppCodeGenServerPath = join(coffeeProductsPath, 'org.eclipse.emfcloud.coffee.product.codegen.cpp', osProductPath, 'x86_64');


const targetDir = join(__dirname, '..', 'servers');

log('Start copying Model Server JAR..');
copyBackendFile(modelServerJarPath, targetDir, 'org.eclipse.emfcloud.coffee.modelserver-0.1.0-SNAPSHOT-standalone.jar');

log('Start copying Model Server log4j2 config..');
copyBackendFile(modelServerLogConfigPath, targetDir, 'model-server-log4j2-embedded.xml');

log('Start copying GLSP Server JAR..');
copyBackendFile(glspServerJarPath, targetDir, 'org.eclipse.emfcloud.coffee.workflow.glsp.server-0.1.0-SNAPSHOT-glsp.jar');

log('Start copying Workflow DSL LSP backend product...');
copyBackendDirectory(workflowDSLServerPath, join(targetDir, 'wf-lsp'));

log('Start copying WorkflowAnalyzer backend product..');
copyBackendDirectory(workflowAnalyerPath, join(targetDir, 'wf-analyzer'));

log('Start copying Java CodeGen backend product..');
copyBackendDirectory(javaCodeGenServerPath, join(targetDir, 'java-codegen'));

log('Start copying C++ CodeGen backend product..');
copyBackendDirectory(cppCodeGenServerPath, join(targetDir, 'cpp-codegen'));

const end = new Date(Date.now());
const elapsedTime = ((end.getTime() - start.getTime()) / 1000);
log('Done in ' + elapsedTime + 's.');
