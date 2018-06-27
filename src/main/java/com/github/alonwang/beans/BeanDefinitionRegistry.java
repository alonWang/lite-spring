package com.github.alonwang.beans;

public interface BeanDefinitionRegistry {
	BeanDefinition getBeanDefinition(String beanID);

	void registerBeanDefinition(String beanID, BeanDefinition beanDefinition);
}
