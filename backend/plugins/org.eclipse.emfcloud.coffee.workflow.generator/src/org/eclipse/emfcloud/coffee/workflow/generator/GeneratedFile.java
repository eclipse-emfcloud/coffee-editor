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
package org.eclipse.emfcloud.coffee.workflow.generator;

public class GeneratedFile {
	private String fileName;
	private String content;
	private boolean overwrite;

	public GeneratedFile(String fileName, String content) {
		this(fileName, content, true);
	}
	
	public GeneratedFile(String fileName, String content, boolean overwrite) {
		this.fileName = fileName;
		this.content = content;
		this.overwrite = overwrite;
	}

	public String getFileName() {
		return fileName;
	}

	public String getContent() {
		return content;
	}

	public boolean shouldOverwrite() {
		return overwrite;
	}

	@Override
	public String toString() {
		return getFileName() + "\n" + getContent();
	}
}
