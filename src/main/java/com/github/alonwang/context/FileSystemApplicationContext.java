package com.github.alonwang.context;

import com.github.alonwang.beans.core.FileSystemResource;
import com.github.alonwang.beans.core.Resource;

/**
 * @author weilong.wang Created on 2018/6/27
 */
public class FileSystemApplicationContext extends AbstractApplicationContext {
    public FileSystemApplicationContext(String filePath) {
        super(filePath);
    }

    @Override
    protected Resource getResource(String path) {
        return new FileSystemResource(path);
    }
}
