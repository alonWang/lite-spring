package com.github.alonwang.test.v2;

import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.factory.TypedStringValue;
import com.github.alonwang.beans.factory.config.RuntimeBeanReference;
import com.github.alonwang.beans.factory.support.BeanDefinitionValueResolver;
import com.github.alonwang.beans.factory.support.DefaultBeanFactory;
import com.github.alonwang.dao.v2.AccountDao;
import com.github.alonwang.xml.XmlBeanDefinitionReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/6/30
 */
public class BeanDefinitionValueResolverTest {
    @Test
    public void testResolveRuntimeBeanReference() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petStore-v2.xml"));
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petStore-v2.xml"));
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);
    }
}
