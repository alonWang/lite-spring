package com.github.alonwang.context;

import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.xml.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;

    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(configFile);
    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
