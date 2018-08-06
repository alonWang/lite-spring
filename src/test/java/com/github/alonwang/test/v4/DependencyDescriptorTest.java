package com.github.alonwang.test.v4;

import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.config.DependencyDescriptor;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.dao.v4.AccountDao;
import com.github.alonwang.service.v4.PetStoreService;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public class DependencyDescriptorTest {

	@Test
	public void testResolveDependency() throws Exception {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petStore-v4.xml");
		reader.loadBeanDefinitions(resource);

		Field f = PetStoreService.class.getDeclaredField("accountDao");
		DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
		Object o = factory.resolveDependency(descriptor);
		Assert.assertTrue(o instanceof AccountDao);
	}
}
