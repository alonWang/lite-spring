package com.github.alonwang.test.v1;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.exception.general.BeanDefinitionStoreException;
import com.github.alonwang.beans.factory.BeanFactory;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.service.v1.PetStoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class BeanFactoryTest {
    @Test
    public void testGetBean() {
        BeanFactory beanFactory = new DefaultBeanFactory("petStore-v1.xml");
        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
        assertEquals("com.github.alonwang.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService) beanFactory.getBean("petStore");
        assertNotNull(petStoreService);
    }
    @Test
    public void testInvalidBean() {
        BeanFactory beanFactory = new DefaultBeanFactory("petStore-v1.xml");
        try {
            beanFactory.getBean("xxx");
        } catch (BeanCreationException e) {
            return;
        }
        Assertions.fail();
    }
    @Test
    public void testInvalidXML() {
        try {
            new DefaultBeanFactory("xxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assertions.fail();
    }

}
