package com.github.alonwang.test.v3;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.ConstructorArgument;
import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.TypedStringValue;
import com.github.alonwang.beans.factory.config.RuntimeBeanReference;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author weilong.wang Created on 2018/7/3
 */
public class BeanDefinitionTestV3 {
	@Test
	public void testConstructorArgument() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petStore-v3.xml");
		reader.loadBeanDefinitions(resource);

		BeanDefinition bd = factory.getBeanDefinition("petStore");
		Assert.assertEquals("com.github.alonwang.service.v3.PetStoreService",
				bd.getBeanClassName());

		ConstructorArgument args = bd.getConstructArgument();
		List<ConstructorArgument.ValueHolder> valueHolders = args
				.getArgumentValues();
		Assert.assertEquals(3, valueHolders.size());

		RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0)
				.getValue();
		Assert.assertEquals("accountDao", ref1.getBeanName());
		RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1)
				.getValue();
		Assert.assertEquals("itemDao", ref2.getBeanName());

		TypedStringValue stringValue = (TypedStringValue) valueHolders.get(2)
				.getValue();
		Assert.assertEquals("1", stringValue.getValue());
	}
}
