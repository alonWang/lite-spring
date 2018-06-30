package com.github.alonwang.xml;


import cn.hutool.core.util.StrUtil;
import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.PropertyValue;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.exception.general.BeanDefinitionStoreException;
import com.github.alonwang.beans.factory.config.RuntimeBeanReference;
import com.github.alonwang.beans.factory.support.GeneralBeanDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE_ATTRIBUTE = "scope";
    private static final String PROPERTY_ELEMENT = "property";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String NAME_ATTRIBUTE = "name";
    private BeanDefinitionRegistry registry;
    protected final Log logger = LogFactory.getLog(getClass());
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
                Element ele = (Element) obj;
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GeneralBeanDefinition(id,
                        beanClassName);
                if (ele.attribute(SCOPE_ATTRIBUTE) != null) {
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                parsePropertyElement(ele, bd);
                this.registry.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException(
                    "IOException parse XML document from [ " + resource.getDescription()
                            + " ]",
                    e);
        }
    }

    private void parsePropertyElement(Element beanEle, BeanDefinition bd) {
        Iterator it = beanEle.elementIterator(PROPERTY_ELEMENT);
        while (it.hasNext()) {
            Element propEle = (Element) it.next();
            String propName = propEle.attributeValue(NAME_ATTRIBUTE);
            if (StrUtil.isEmpty(propName)) {
                logger.fatal("Tag 'property' must have  a 'name' attribute");
                return;
            }
            Object val = parsePropertyValue(propEle, bd, propName);
            PropertyValue pv = new PropertyValue(propName, val);
        }

    }

    private Object parsePropertyValue(Element propEle, BeanDefinition bd, String propName) {
        String eleName = (propName != null) ? "<property> element for property '" + propName + "'" : "<constructor-arg> element";
        boolean hasRefAttribute = propEle.attribute(REF_ATTRIBUTE) != null;
        boolean hasValueAttribute = propEle.attribute(VALUE_ATTRIBUTE) != null;

        if (hasRefAttribute) {
            String refName = propEle.attributeValue(REF_ATTRIBUTE);
            if (StrUtil.isEmpty(refName)) {
                logger.error(eleName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        } else if (hasValueAttribute) {

        }


    }
}
