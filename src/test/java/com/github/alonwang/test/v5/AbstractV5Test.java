package com.github.alonwang.test.v5;

import com.github.alonwang.aop.config.AspectInstanceFactory;
import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.BeanFactory;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.tx.TransactionManager;
import com.github.alonwang.xml.XmlBeanDefinitionReader;

import java.lang.reflect.Method;

public class AbstractV5Test {

	protected BeanFactory getBeanFactory(String configFile) {
		DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
				defaultBeanFactory);
		Resource resource = new ClassPathResource(configFile);
		reader.loadBeanDefinitions(resource);
		return defaultBeanFactory;
	}

	protected Method getAdviceMethod(String methodName) throws Exception {
		return TransactionManager.class.getMethod(methodName);
	}

	protected AspectInstanceFactory getAspectInstanceFactory(
			String targetBeanName) {
		AspectInstanceFactory factory = new AspectInstanceFactory();
		factory.setAspectBeanName(targetBeanName);
		return factory;
	}

}
