package com.github.alonwang.test.v1;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.exception.general.BeanDefinitionStoreException;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.service.v1.PetStoreService;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {
    private DefaultBeanFactory factory;
    private XmlBeanDefinitionReader reader;

    @Before
    public void setUP() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petStore-v1.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        assertEquals("com.github.alonwang.service.v1.PetStoreService",
                bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService) factory
                .getBean("petStore");
        assertNotNull(petStoreService);
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petStore-v1.xml"));
        try {
            factory.getBean("xxx");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinitions(new ClassPathResource("xxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail();
    }

}
