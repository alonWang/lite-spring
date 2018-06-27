package com.github.alonwang.beans.core;

import com.github.alonwang.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {
    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    public ClassPathResource(String path) {
        this(path, null);
    }

    public InputStream getInputStream() throws IOException

    {
        InputStream is = this.classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(path + "can't be open!");
        }
        return is;
    }

    public String getDescription() {
        return this.path;
    }
}
