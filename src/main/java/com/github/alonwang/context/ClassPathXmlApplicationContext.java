package com.github.alonwang.context;

import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResource(String path) {
        return new ClassPathResource(path);
    }
}
