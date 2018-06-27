package com.github.alonwang.context;

import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.xml.XmlBeanDefinitionReader;

/**
 * @author weilong.wang Created on 2018/6/27
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResource(configFile);
        reader.loadBeanDefinitions(resource);
    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected abstract Resource getResource(String path);
}
