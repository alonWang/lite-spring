package com.github.alonwang.beans.factory;

import java.util.List;

public interface BeanFactory {
	/**
	 * get bean by beanID
	 *
	 * @param beanID
	 * @return
	 */
	Object getBean(String beanID);

	Class<?> getType(String name) throws NoSuchBeanDefinitionException;

	List<Object> getBeansByType(Class<?> type);
}
