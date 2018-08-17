package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.ConfigurableBeanFactory;

/**
 * @author weilong.wang Created on 2018/8/17
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
		implements ConfigurableBeanFactory {
	protected abstract Object createBean(BeanDefinition bd)
			throws BeanCreationException;
}
