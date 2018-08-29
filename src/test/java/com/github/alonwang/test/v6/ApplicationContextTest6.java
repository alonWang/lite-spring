package com.github.alonwang.test.v6;

import com.github.alonwang.context.ApplicationContext;
import com.github.alonwang.context.ClassPathXmlApplicationContext;
import com.github.alonwang.service.v6.IPetStoreService;
import com.github.alonwang.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author weilong.wang Created on 2018/8/29
 */
public class ApplicationContextTest6 {
	@Before
	public void setUp() {
		MessageTracker.clearMsgs();
	}

	@Test
	public void testGetBeanProperty() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"petStore-v6.xml");
		IPetStoreService petStoreService = (IPetStoreService) ctx
				.getBean("petStoreService");
		petStoreService.placeOrder();
		List<String> msgs = MessageTracker.getMsgs();

		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));
		Assert.assertEquals("place order", msgs.get(1));
		Assert.assertEquals("commit tx", msgs.get(2));
	}

}
