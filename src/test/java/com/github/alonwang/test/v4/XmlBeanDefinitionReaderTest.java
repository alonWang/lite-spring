package com.github.alonwang.test.v4;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.core.annotation.AnnotationAttributes;
import com.github.alonwang.beans.core.type.AnnotationMetadata;
import com.github.alonwang.beans.factory.annotation.ScannedGenericBeanDefinition;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.stereotype.Component;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public class XmlBeanDefinitionReaderTest {
	@Test
	public void testParseScanedBean() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petStore-v4.xml");
		reader.loadBeanDefinitions(resource);
		String annotaion = Component.class.getName();
		{
			BeanDefinition bd = factory.getBeanDefinition("petStoreService");
			Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
			ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
			AnnotationMetadata amd = sbd.getMetadata();

			Assert.assertTrue(amd.hasAnnotation(annotaion));
			AnnotationAttributes attributes = amd
					.getAnnotationAttributes(annotaion);
			Assert.assertEquals("petStore", attributes.get("value"));
		}

	}

}
