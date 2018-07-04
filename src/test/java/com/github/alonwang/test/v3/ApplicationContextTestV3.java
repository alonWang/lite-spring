package com.github.alonwang.test.v3;

import com.github.alonwang.context.ApplicationContext;
import com.github.alonwang.context.ClassPathXmlApplicationContext;
import com.github.alonwang.service.v3.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/7/3
 */
public class ApplicationContextTestV3 {
	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"petStore-v3.xml");
		PetStoreService petStoreService = (PetStoreService) ctx
				.getBean("petStore");
		Assert.assertNotNull(petStoreService.getAccountDao());
		Assert.assertNotNull(petStoreService.getItemDao());
		Assert.assertEquals(1, petStoreService.getVersion());
	}
}
