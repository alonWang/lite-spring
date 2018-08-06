package com.github.alonwang.beans.factory.config;

import com.github.alonwang.beans.exception.BeanException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;

    boolean afterInstantiation(Object bean, String beanName) throws BeanException;

    void postProcessPropertyValues(Object bean, String beanName)
            throws BeanException;

}
