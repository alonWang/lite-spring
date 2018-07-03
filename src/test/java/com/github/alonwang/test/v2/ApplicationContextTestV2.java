package com.github.alonwang.test.v2;

import com.github.alonwang.context.ApplicationContext;
import com.github.alonwang.context.ClassPathXmlApplicationContext;
import com.github.alonwang.dao.v2.AccountDao;
import com.github.alonwang.dao.v2.ItemDao;
import com.github.alonwang.service.v2.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/6/30
 */
public class ApplicationContextTestV2 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petStore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
        Assert.assertEquals("王xx", petStore.getOwner());
        Assert.assertEquals(2, petStore.getVersion());
    }
}
