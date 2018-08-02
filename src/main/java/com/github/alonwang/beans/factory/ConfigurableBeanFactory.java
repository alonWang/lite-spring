package com.github.alonwang.beans.factory;

import com.github.alonwang.beans.factory.config.AutowiredCapableBeanFactory;

/**
 * @author weilong.wang Created on 2018/6/27
 */
public interface ConfigurableBeanFactory extends AutowiredCapableBeanFactory {
	ClassLoader getBeanClassLoader();

	void setBeanClassLoader(ClassLoader classLoader);
}
