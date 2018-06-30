package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.core.Assert;
import com.github.alonwang.beans.factory.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author weilong.wang Created on 2018/6/29
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "'beanName' must not be null");
        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Could not register object [" + singletonObject + "] under beanName [{" + beanName + "}] : this is already object [{" + oldObject + "}] bound");
        }
        this.singletonObjects.put(beanName, singletonObject);
    }

    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
