package com.github.alonwang.test.v1;

import cn.hutool.core.io.FileUtil;
import com.github.alonwang.context.ApplicationContext;
import com.github.alonwang.context.ClassPathXmlApplicationContext;
import com.github.alonwang.context.FileSystemApplicationContext;
import com.github.alonwang.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {

    public static final String PET_STORE = "petStore";
    public static final String PET_STORE_XML = "petStore-v1.xml";

    @Test
    public void testClassPathXmlApplicationContext() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(PET_STORE_XML);
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void testFileSystemApplicationContext() {
        ApplicationContext ctx = new FileSystemApplicationContext(FileUtil.getAbsolutePath(PET_STORE_XML));
        PetStoreService petStoreService = (PetStoreService) ctx.getBean(PET_STORE);
        Assert.assertNotNull(petStoreService);
    }
}
