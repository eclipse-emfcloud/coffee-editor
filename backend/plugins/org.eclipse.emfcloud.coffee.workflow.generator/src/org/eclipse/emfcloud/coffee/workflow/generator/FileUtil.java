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

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class FileUtil {
	private static final String JAVA_EXTENSION = ".java";
	private static final String CPP_HEADER_EXTENSION = ".h";
	private static final String CPP_CLASS_EXTENSION = ".cpp";

	public static String normalize(final String string) {
		return Arrays.stream(string.split("[ -]")).map(StringUtils::capitalize)
				.map(part -> part.replaceAll("[^A-Za-z0-9]", "")).collect(Collectors.joining());
	}

	public static String getJavaFileName(final String fileName) {
		return getFileName(fileName, JAVA_EXTENSION);
	}
	
	public static String getCppHeaderFileName(final String fileName) {
		return getFileName(fileName, CPP_HEADER_EXTENSION);
	}
	
	public static String getCppClassFileName(final String fileName) {
		return getFileName(fileName, CPP_CLASS_EXTENSION);
	}
	
	private static String getFileName(final String fileName, final String extension) {
		String newFileName = fileName;
		if(newFileName.endsWith(extension)) {
			newFileName = newFileName.substring(0, newFileName.length() - extension.length());
		}
		return normalize(newFileName) + extension;
	}

	public static String getFilePath(final String packageName) {
		return Arrays.stream(packageName.split("\\.")).collect(Collectors.joining("/")) + "/";
	}
}
