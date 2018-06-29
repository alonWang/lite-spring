package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.ConfigurableBeanFactory;
import com.github.alonwang.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {
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
        BeanDefinition bd = this.beanDefinitionMap.get(beanID);
        if (bd == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if (bd.isSingleton()) {
            Object bean = this.getSingleton(beanID);
            if (bean == null) {
                bean = createBean(bd);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    private Object createBean(BeanDefinition bd) {
        ClassLoader cl = getBeanClassLoader();
        String beanName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanName);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BeanCreationException(
                    "Create bean for [" + beanName + "] failed", e);
        }
    }
}
