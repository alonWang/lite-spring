package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.factory.BeanFactory;
import com.github.alonwang.beans.factory.TypedStringValue;
import com.github.alonwang.beans.factory.config.RuntimeBeanReference;

/**
 * @author weilong.wang Created on 2018/6/30
 */
public class BeanDefinitionValueResolver {
	private final BeanFactory beanFactory;

	public BeanDefinitionValueResolver(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Object resolveValueIfNecessary(Object value) {
		if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference ref = (RuntimeBeanReference) value;
			String refName = ref.getBeanName();
			Object bean = this.beanFactory.getBean(refName);
			return bean;
		} else if (value instanceof TypedStringValue) {
			return ((TypedStringValue) value).getValue();
		} else {
			throw new RuntimeException(
					"the value " + value + "has not implemented");
		}
	}
}
