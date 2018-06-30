package com.github.alonwang.beans.factory.config;

/**
 * @author weilong.wang Created on 2018/6/30
 */
public class RuntimeBeanReference {
    private final String beanName;

    public RuntimeBeanReference(String beanName) {

        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
