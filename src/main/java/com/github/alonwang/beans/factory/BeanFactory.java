package com.github.alonwang.beans.factory;

import com.github.alonwang.beans.BeanDefinition;

public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);
}
