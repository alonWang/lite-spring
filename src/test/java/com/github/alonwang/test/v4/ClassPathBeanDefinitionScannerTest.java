package com.github.alonwang.test.v4;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.core.annotation.AnnotationAttributes;
import com.github.alonwang.beans.core.type.AnnotationMetadata;
import com.github.alonwang.beans.factory.annotation.ScannedGenericBeanDefinition;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.context.annotation.ClassPathBeanDefinitionScanner;
import com.github.alonwang.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

public class ClassPathBeanDefinitionScannerTest {
    @Test
    public void testParseScanedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "com.github.alonwang.service.v4,com.github.alonwang.dao.v4";
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);
        String annotation= Component.class.getName();
        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();


            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
