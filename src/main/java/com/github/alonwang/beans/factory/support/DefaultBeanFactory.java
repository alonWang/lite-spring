package com.github.alonwang.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.BeanFactory;
import com.github.alonwang.util.ClassUtils;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(
			64);

	public DefaultBeanFactory() {
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
		ClassLoader cl = ClassUtils.getDefaultClassLoader();
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
