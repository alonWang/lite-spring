package com.github.alonwang.beans.core;

import com.github.alonwang.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {
	private String path;
	private File file;

	public FileSystemResource(File file) {
		this.path = file.getPath();
		this.file = file;
	}

	public FileSystemResource(String path) {
		Assert.notNull(path, "path can't be null");
		this.file = new File(path);
		this.path = path;
	}

	public InputStream getInputStream() throws IOException {

		return new FileInputStream(file);
	}

	public String getDescription() {
		return "file:[" + file.getAbsolutePath() + "]";
	}
}
