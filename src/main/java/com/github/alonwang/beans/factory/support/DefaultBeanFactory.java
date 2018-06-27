package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.ConfigurableBeanFactory;
import com.github.alonwang.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(
            64);
    private ClassLoader beanClassLoader;

    public DefaultBeanFactory() {
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        beanClassLoader = classLoader;
    }

    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    public void registerBeanDefinition(String beanID,
                                       BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanID, beanDefinition);

    }

    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanID);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        ClassLoader cl = getBeanClassLoader();
        String beanName = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanName);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BeanCreationException(
                    "Create bean for [" + beanName + "] failed", e);
        }
    }
}
