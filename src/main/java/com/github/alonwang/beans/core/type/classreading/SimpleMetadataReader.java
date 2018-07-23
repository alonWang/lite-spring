package com.github.alonwang.beans.core.type.classreading;

import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.core.type.AnnotationMetadata;
import com.github.alonwang.beans.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleMetadataReader implements MetadataReader {
    private final Resource resource;
    private final ClassMetadata classMetadata;
    private final AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(Resource resource) throws IOException {
        InputStream is = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader = null;
        try {
            classReader = new ClassReader(is);
        } finally {
            is.close();
        }
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor, org.springframework.asm.ClassReader.SKIP_DEBUG);
        this.resource = resource;
        this.classMetadata = visitor;
        this.annotationMetadata = visitor;
    }

    public Resource getResource() {
        return resource;
    }

    public ClassMetadata getClassMetadata() {
        return classMetadata;
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return annotationMetadata;
    }
}
