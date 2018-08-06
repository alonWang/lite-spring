package com.github.alonwang.beans.factory;

import com.github.alonwang.beans.factory.config.AutowiredCapableBeanFactory;
import com.github.alonwang.beans.factory.config.BeanPostProcessor;

import java.util.List;

/**
 * @author weilong.wang Created on 2018/6/27
 */
public interface ConfigurableBeanFactory extends AutowiredCapableBeanFactory {
    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader classLoader);

    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessors();
}
