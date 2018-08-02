package com.github.alonwang.beans.factory.config;

import com.github.alonwang.beans.factory.BeanFactory;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public interface AutowiredCapableBeanFactory extends BeanFactory {
	Object resolveDependency(DependencyDescriptor descriptor);

}
