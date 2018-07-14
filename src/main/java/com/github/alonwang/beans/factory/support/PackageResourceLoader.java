package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.core.FileSystemResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.util.Assert;
import com.github.alonwang.util.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author weilong.wang Created on 2018/7/14
 */
public class PackageResourceLoader {
	private static final Log logger = LogFactory
			.getLog(PackageResourceLoader.class);
	private final ClassLoader classLoader;

	public PackageResourceLoader() {
		this.classLoader = ClassUtils.getDefaultClassLoader();
	}

	public PackageResourceLoader(ClassLoader classLoader) {
		Assert.notNull(classLoader, "ResourceLoader must not be null");
		this.classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public Resource[] getResources(String basePackage) throws IOException {
		Assert.notNull(basePackage, "basePackage must not be null");
		String location = ClassUtils
				.convertClassNameToResourcePath(basePackage);
		URL url = classLoader.getResource(location);
		File rootDir = new File(url.getFile());

		Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
		Resource[] result = new Resource[matchingFiles.size()];
		int i = 0;
		for (File file : matchingFiles) {
			result[i++] = new FileSystemResource(file);
		}
		return result;
	}

	private Set<File> retrieveMatchingFiles(File rootDir) throws IOException {
		if (!rootDir.exists()) {
			logger.warn("Skipping [" + rootDir.getAbsolutePath()
					+ "] because it does not exist");
			return Collections.emptySet();
		}
		if (!rootDir.isDirectory()) {
			logger.warn("Skipping [" + rootDir.getAbsolutePath()
					+ "] because it does not denote a directory");
			return Collections.emptySet();
		}
		if (!rootDir.canRead()) {
			logger.warn("Can't search for matching files underneath directory ["
					+ rootDir.getAbsolutePath()
					+ "] because the application is not allowed to read the directory");
			return Collections.emptySet();
		}
		Set<File> result = new LinkedHashSet<File>(8);
		doRetrieveMatchingFiles(rootDir, result);
		return result;

	}

	private void doRetrieveMatchingFiles(File dir, Set<File> result)
			throws IOException {
		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			logger.warn("Could not retrieve contents of directory ["
					+ dir.getAbsolutePath() + "]");
			return;
		}
		for (File content : dirContents) {
			if (content.isDirectory()) {
				if (!content.canRead()) {
					logger.warn("Skipping subdirectory ["
							+ dir.getAbsolutePath()
							+ "] because the application is not allowed to read the directory");
				} else {
					doRetrieveMatchingFiles(content, result);
				}
			} else {
				result.add(content);
			}
		}
	}

}
