package com.github.alonwang.beans.core.type.classreading;

import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.core.type.AnnotationMetadata;
import com.github.alonwang.beans.core.type.ClassMetadata;

public interface MetadataReader {
    Resource getResource();
    ClassMetadata getClassMetadata();
    AnnotationMetadata getAnnotationMetadata();
}
