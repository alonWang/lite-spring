package com.github.alonwang.beans.factory.annotation;

import com.github.alonwang.beans.core.type.AnnotationMetadata;
import com.github.alonwang.beans.factory.support.GenericBeanDefinition;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
