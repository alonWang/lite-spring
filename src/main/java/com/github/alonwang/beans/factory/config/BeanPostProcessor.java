package com.github.alonwang.beans.factory.config;

import com.github.alonwang.beans.exception.BeanException;

public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeanException;


    Object afterInitialization(Object bean, String beanName) throws BeanException;


}
