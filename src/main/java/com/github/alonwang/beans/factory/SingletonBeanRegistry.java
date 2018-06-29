package com.github.alonwang.beans.factory;

/**
 * @author weilong.wang Created on 2018/6/29
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
