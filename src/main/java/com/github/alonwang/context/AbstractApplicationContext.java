package com.github.alonwang.context;

import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.ConfigurableBeanFactory;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.context.annotation.AutowiredAnnotationProcessor;
import com.github.alonwang.util.ClassUtils;
import com.github.alonwang.xml.XmlBeanDefinitionReader;

/**
 * @author weilong.wang Created on 2018/6/27
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;
    private ClassLoader beanClassLoader;

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResource(configFile);
        reader.loadBeanDefinitions(resource);
        registerBeanPostProcessors(factory);
    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {

        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);

    }

    protected abstract Resource getResource(String path);
}
