/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.generator.impl;

import java.util.ArrayList;
import java.util.List;

public class WorkflowGeneratorOutput {

   private final List<IGeneratedFile> generatedFiles = new ArrayList<>();
   private final String packageName;
   private final String sourceFileName;

   public WorkflowGeneratorOutput(final String packageName, final String sourceFileName) {
      this.packageName = packageName;
      this.sourceFileName = sourceFileName;
   }

   public List<IGeneratedFile> getGeneratedFiles() { return generatedFiles; }

   public void addGeneratedFile(final String fileName, final String content, final boolean overwrite) {
      generatedFiles.add(new GeneratedFile(fileName, content, overwrite));
   }

   public void addGeneratedFile(final String fileName, final String content) {
      addGeneratedFile(fileName, content, true);
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("Output for '" + packageName + "/" + sourceFileName + "':\n");
      getGeneratedFiles().forEach(file -> sb.append(file.getFileName() + "\n"));
      return sb.toString();
   }

   public static class GeneratedFile implements IGeneratedFile {
      private final String fileName;
      private final String content;
      private final boolean overwrite;

      public GeneratedFile(final String fileName, final String content, final boolean overwrite) {
         this.fileName = fileName;
         this.content = content;
         this.overwrite = overwrite;
      }

      @Override
      public String getFileName() { return fileName; }

      @Override
      public String getContent() { return content; }

      @Override
      public boolean shouldOverwrite() {
         return overwrite;
      }

      @Override
      public String toString() {
         return getFileName() + "\n" + getContent();
      }
   }

   interface IGeneratedFile {
      String getFileName();

      String getContent();

      boolean shouldOverwrite();
   }
}
