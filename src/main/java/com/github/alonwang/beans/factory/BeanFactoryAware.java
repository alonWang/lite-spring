package com.github.alonwang.beans.factory;

import com.github.alonwang.beans.exception.BeanException;

/**
 * @author weilong.wang Created on 2018/8/16
 */
public interface BeanFactoryAware {
	void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
