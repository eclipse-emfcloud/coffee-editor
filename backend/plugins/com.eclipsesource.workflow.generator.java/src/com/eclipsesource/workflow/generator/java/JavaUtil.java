package com.eclipsesource.workflow.generator.java;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class JavaUtil {
	private static final String JAVA_EXTENSION = ".java";

	public static String normalize(final String string) {
		return Arrays.stream(string.split("[ -]")).map(StringUtils::capitalize)
				.map(part -> part.replaceAll("[^A-Za-z0-9]", "")).collect(Collectors.joining());
	}

	public static String getJavaFileName(final String fileName) {
		String javaFileName = fileName;
		if(javaFileName.endsWith(JAVA_EXTENSION)) {
			javaFileName = javaFileName.substring(0, javaFileName.length() - JAVA_EXTENSION.length());
		}
		return normalize(javaFileName) + JAVA_EXTENSION;
	}

	public static String getFilePath(final String packageName) {
		return Arrays.stream(packageName.split("\\.")).collect(Collectors.joining("/")) + "/";
	}
}
