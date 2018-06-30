package com.github.alonwang.test.v2;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.PropertyValue;
import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.factory.config.RuntimeBeanReference;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author weilong.wang Created on 2018/6/30
 */
public class BeanDefinitionTestV2 {
    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petStore-v2.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        List<PropertyValue> pvs = bd.getPropertyValues();
        Assert.assertTrue(pvs.size() == 2);
        {
            PropertyValue pv = this.getPropertyValue("accountDao", pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
        {
            PropertyValue pv = this.getPropertyValue("itemDao", pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> pvs) {
        for (PropertyValue pv : pvs) {
            if (pv.getName().equals(name)) {
                return pv;
            }
        }
        return null;
    }
}
