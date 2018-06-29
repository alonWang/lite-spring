package com.github.alonwang.xml;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.exception.general.BeanDefinitionStoreException;
import com.github.alonwang.beans.factory.support.GeneralBeanDefinition;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class XmlBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";
    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();
            for (Object obj : root.elements()) {
                Element element = (Element) obj;
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GeneralBeanDefinition(id,
                        beanClassName);
                if (element.attribute(SCOPE_ATTRIBUTE) != null) {
                    bd.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.registry.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException(
                    "IOException parse XML document from [ " + resource.getDescription()
                            + " ]",
                    e);
        }
    }
}
