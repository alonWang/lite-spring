package com.github.alonwang.test.v4;

import com.github.alonwang.context.ApplicationContext;
import com.github.alonwang.context.ClassPathXmlApplicationContext;
import com.github.alonwang.service.v4.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/7/13
 */
public class ApplicationContextTest4 {
	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"petStore-v4.xml");
		PetStoreService petStoreService = (PetStoreService) ctx
				.getBean("petStore");

		Assert.assertNotNull(petStoreService.getAccountDao());
		Assert.assertNotNull(petStoreService.getItemDao());
	}
}
