package com.github.alonwang.test.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.exception.general.BeanDefinitionStoreException;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.service.v1.PetStoreService;
import com.github.alonwang.xml.XmlBeanDefinitionReader;

public class BeanFactoryTest {
	private DefaultBeanFactory factory;
	private XmlBeanDefinitionReader reader;

	@BeforeEach
	public void setUP() {
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);
	}

	@Test
	public void testGetBean() {
		reader.loadBeanDefinitions("petStore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");
		assertEquals("com.github.alonwang.service.v1.PetStoreService",
				bd.getBeanClassName());

		PetStoreService petStoreService = (PetStoreService) factory
				.getBean("petStore");
		assertNotNull(petStoreService);
	}

	@Test
	public void testInvalidBean() {
		reader.loadBeanDefinitions("petStore-v1.xml");
		try {
			factory.getBean("xxx");
		} catch (BeanCreationException e) {
			return;
		}
		Assertions.fail();
	}

	@Test
	public void testInvalidXML() {
		try {
			reader.loadBeanDefinitions("xxx.xml");
		} catch (BeanDefinitionStoreException e) {
			return;
		}
		Assertions.fail();
	}

}
