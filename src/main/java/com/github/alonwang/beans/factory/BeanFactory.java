package com.github.alonwang.beans.factory;

public interface BeanFactory {
	/**
	 * get bean by beanID
	 * 
	 * @param beanID
	 * @return
	 */
	Object getBean(String beanID);
}
