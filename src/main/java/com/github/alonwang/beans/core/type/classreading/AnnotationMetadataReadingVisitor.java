package com.github.alonwang.beans.core.type.classreading;

import com.github.alonwang.beans.core.annotation.AnnotationAttributes;
import jdk.internal.org.objectweb.asm.Type;
import org.springframework.asm.AnnotationVisitor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor {
    private final Set<String> annotationSet = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<String, AnnotationAttributes>(4);

    public AnnotationMetadataReadingVisitor() {
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, this.attributesMap);
    }

    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributesMap.get(annotationType);
    }
}
