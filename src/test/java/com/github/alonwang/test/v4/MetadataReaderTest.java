package com.github.alonwang.test.v4;

import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.annotation.AnnotationAttributes;
import com.github.alonwang.beans.core.type.AnnotationMetadata;
import com.github.alonwang.beans.core.type.classreading.MetadataReader;
import com.github.alonwang.beans.core.type.classreading.SimpleMetadataReader;
import com.github.alonwang.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/github/alonwang/service/v4/PetStoreService.class");
        MetadataReader reader = new SimpleMetadataReader(resource);
        AnnotationMetadata amd = reader.getAnnotationMetadata();
        String annotation = Component.class.getName();
        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.getString("value"));

        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("com.github.alonwang.service.v4.PetStoreService", amd.getClassName());
    }
}
