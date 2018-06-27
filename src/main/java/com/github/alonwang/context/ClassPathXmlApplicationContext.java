package com.github.alonwang.context;

import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.xml.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;

    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinitions(resource);
    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
