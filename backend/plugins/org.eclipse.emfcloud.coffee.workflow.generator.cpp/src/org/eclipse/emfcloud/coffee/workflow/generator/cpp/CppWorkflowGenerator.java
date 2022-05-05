/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.generator.cpp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.workflow.generator.GeneratedFile;

public class CppWorkflowGenerator {
   private static final String SRC_FOLDER = "cpp/src";
   private static final String INCLUDE_FOLDER = "cpp/inc";

   // workflow library
   private final WorkflowTaskGenerator workflowTaskGen = new WorkflowTaskGenerator(SRC_FOLDER, INCLUDE_FOLDER);
   private final AutomaticWorkflowTaskGenerator automaticWorkflowTaskGen = new AutomaticWorkflowTaskGenerator(
      INCLUDE_FOLDER);
   private final ManualWorkflowTaskGenerator manualWorkflowTaskGen = new ManualWorkflowTaskGenerator(INCLUDE_FOLDER);

   // main runner
   private final MainMethodUserTaskGenerator mainTestGen = new MainMethodUserTaskGenerator(SRC_FOLDER);
   private final CMakeGenerator cmakeGen = new CMakeGenerator("cpp", SRC_FOLDER);

   // task-specific files
   private final TaskGenerator abstractTaskGen = new TaskGenerator(INCLUDE_FOLDER);
   private final UserTaskGenerator userTaskGen = new UserTaskGenerator(SRC_FOLDER, INCLUDE_FOLDER);

   public Collection<GeneratedFile> generateClasses(final Machine machine, final String packageName,
      final String sourceFileName) {
      List<GeneratedFile> result = new ArrayList<>();

      // generate libraries
      generateWorkflowLibrary(result, packageName, SRC_FOLDER);

      List<Task> tasks = machine.getWorkflows().stream()
         .flatMap(w -> w.getNodes().stream().filter(Task.class::isInstance).map(Task.class::cast))
         .collect(Collectors.toList());

      // generate tasks
      tasks.forEach(task -> {
         result.add(new GeneratedFile(abstractTaskGen.toHeaderFileName(task.getName()),
            abstractTaskGen.toHeaderFileContent(sourceFileName, task)));

         result.add(new GeneratedFile(userTaskGen.toHeaderFileName(task.getName()),
            userTaskGen.toHeaderFileContent(sourceFileName, task)));
         result.add(new GeneratedFile(userTaskGen.toClassFileName(task.getName()),
            userTaskGen.toClassFileContent(sourceFileName, task)));
      });

      result.add(new GeneratedFile(mainTestGen.toFileName(packageName),
         mainTestGen.toFileContent(packageName, sourceFileName, tasks), true));
      result.add(new GeneratedFile(cmakeGen.toRootFileName(),
         cmakeGen.toRootFileContent(packageName, sourceFileName, tasks), true));
      result.add(new GeneratedFile(cmakeGen.toSrcFileName(),
         cmakeGen.toSrcFileContent(packageName, sourceFileName, tasks), true));

      return result;
   }

   private void generateWorkflowLibrary(final Collection<GeneratedFile> result, final String packageName,
      final String sourceDir) {
      result.add(new GeneratedFile(workflowTaskGen.toHeaderFileName(), workflowTaskGen.toHeaderFileContent(), false));

      result.add(new GeneratedFile(workflowTaskGen.toClassFileName(), workflowTaskGen.toClassFileContent(), false));

      result.add(new GeneratedFile(automaticWorkflowTaskGen.toHeaderFileName(),
         automaticWorkflowTaskGen.toHeaderFileContent(), false));

      result.add(new GeneratedFile(manualWorkflowTaskGen.toHeaderFileName(),
         manualWorkflowTaskGen.toHeaderFileContent(), false));
   }
}
