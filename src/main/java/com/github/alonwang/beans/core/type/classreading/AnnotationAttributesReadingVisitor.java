package com.github.alonwang.beans.core.type.classreading;


import com.github.alonwang.beans.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

public final class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
    private final String annotationType;
    private final Map<String, AnnotationAttributes> attributesMap;
   private AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributes) {
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributes;
    }

    @Override
    public void visitEnd() {
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName,attributeValue);
    }
}
