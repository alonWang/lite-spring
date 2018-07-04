package com.github.alonwang.test.v3;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.support.ConstructorResolver;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.service.v3.PetStoreService;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/7/4
 */
public class ConstructorResolverTest {
	@Test
	public void testAutowireConstructor() {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petStore-v3.xml");
		reader.loadBeanDefinitions(resource);
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		ConstructorResolver resolver = new ConstructorResolver(factory);
		PetStoreService petStoreService = (PetStoreService) resolver
				.autowireConstructor(bd);

		Assert.assertEquals(1, petStoreService.getVersion());
		Assert.assertNotNull(petStoreService.getAccountDao());
		Assert.assertNotNull(petStoreService.getItemDao());
	}
}
