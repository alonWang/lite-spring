package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;

public class GeneralBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;

    public GeneralBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }
}
