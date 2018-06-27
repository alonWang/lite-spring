package com.github.alonwang.beans.factory;

/**
 * @author weilong.wang Created on 2018/6/27
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader classLoader);
}
