package com.github.alonwang.beans.factory.annotation;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}
