package com.github.alonwang.test.v4;

import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.annotation.AutowiredFieldElement;
import com.github.alonwang.beans.factory.annotation.InjectionElement;
import com.github.alonwang.beans.factory.annotation.InjectionMetadata;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.dao.v4.AccountDao;
import com.github.alonwang.dao.v4.ItemDao;
import com.github.alonwang.service.v4.PetStoreService;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public class InjectMetadataTest {
	@Test
	public void testInjection() throws Exception {
		DefaultBeanFactory factory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		Resource resource = new ClassPathResource("petStore-v4.xml");
		reader.loadBeanDefinitions(resource);

		Class<?> clz = PetStoreService.class;
		LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();

		{
			Field f = PetStoreService.class.getDeclaredField("accountDao");
			InjectionElement injectionElem = new AutowiredFieldElement(f, true,
					factory);
			elements.add(injectionElem);
		}
		{
			Field f = PetStoreService.class.getDeclaredField("itemDao");
			InjectionElement injectionElem = new AutowiredFieldElement(f, true,
					factory);
			elements.add(injectionElem);
		}

		InjectionMetadata metadata = new InjectionMetadata(clz, elements);

		PetStoreService petStoreService = new PetStoreService();

		metadata.inject(petStoreService);
		Assert.assertTrue(
				petStoreService.getAccountDao() instanceof AccountDao);
		Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);
	}
}
