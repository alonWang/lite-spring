package com.github.alonwang.xml;

import cn.hutool.core.util.StrUtil;
import com.github.alonwang.aop.config.ConfigBeanDefinitionParser;
import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.ConstructorArgument;
import com.github.alonwang.beans.PropertyValue;
import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.exception.general.BeanDefinitionStoreException;
import com.github.alonwang.beans.factory.TypedStringValue;
import com.github.alonwang.beans.factory.config.RuntimeBeanReference;
import com.github.alonwang.beans.factory.support.GenericBeanDefinition;
import com.github.alonwang.context.annotation.ClassPathBeanDefinitionScanner;
import com.github.alonwang.util.StringUtils;
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
	private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
	private static final String TYPE_ATTRIBUTE = "type";
	public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
	public static final String AOP_NAMESPACE_URI = "http://www.springframework.org/schema/aop";
	public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

	private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
	private final Log logger = LogFactory.getLog(getClass());
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
				Element ele = (Element) obj;
				String nameSpaceUri = ele.getNamespaceURI();
				if (this.isDefaultNamespace(nameSpaceUri)) {
					parseDefaultElement(ele);
				} else if (this.isContextNamespace(nameSpaceUri)) {
					parseComponentElement(ele);
				} else if (this.isAOPNamespace(nameSpaceUri)) {
					parseAOPElement(ele); // 例如 <aop:config>
				}
			}
		} catch (Exception e) {
			throw new BeanDefinitionStoreException(
					"IOException parse XML document from [ "
							+ resource.getDescription() + " ]",
					e);
		}
	}

	private void parseAOPElement(Element ele) {
		ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
		parser.parse(ele, this.registry);
	}

	public boolean isAOPNamespace(String namespaceUri) {
		return (!StringUtils.hasLength(namespaceUri)
				|| AOP_NAMESPACE_URI.equals(namespaceUri));
	}

	private void parseComponentElement(Element ele) {
		String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(
				registry);
		scanner.doScan(basePackages);
	}

	private void parseDefaultElement(Element ele) {
		String id = ele.attributeValue(ID_ATTRIBUTE);
		String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
		BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
		if (ele.attribute(SCOPE_ATTRIBUTE) != null) {
			bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
		}
		parseConstructorArgElements(ele, bd);
		parsePropertyElement(ele, bd);
		this.registry.registerBeanDefinition(id, bd);
	}

	private boolean isContextNamespace(String nameSpaceUri) {
		return StrUtil.isEmpty(nameSpaceUri)
				|| CONTEXT_NAMESPACE_URI.equals(nameSpaceUri);
	}

	private boolean isDefaultNamespace(String nameSpaceUri) {
		return StrUtil.isEmpty(nameSpaceUri)
				|| BEANS_NAMESPACE_URI.equals(nameSpaceUri);
	}

	private void parseConstructorArgElements(Element constructorEle,
			BeanDefinition bd) {
		Iterator it = constructorEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
		while (it.hasNext()) {
			Element ele = (Element) it.next();
			parseConstructorArgElement(ele, bd);
		}
	}

	private void parseConstructorArgElement(Element ele, BeanDefinition bd) {
		String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
		String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
		Object value = parsePropertyValue(ele, bd, null);
		ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(
				value);
		if (!StrUtil.isEmpty(typeAttr)) {
			valueHolder.setType(typeAttr);
		}
		if (!StrUtil.isEmpty(nameAttr)) {
			valueHolder.setName(nameAttr);
		}
		bd.getConstructArgument().addArgumentValue(valueHolder);
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
			bd.addProperty(pv);
		}

	}

	private Object parsePropertyValue(Element propEle, BeanDefinition bd,
			String propName) {
		String eleName = (propName != null)
				? "<property> element for property '" + propName + "'"
				: "<constructor-arg> element";
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
			TypedStringValue valueHolder = new TypedStringValue(
					propEle.attributeValue(VALUE_ATTRIBUTE));
			return valueHolder;
		} else {
			throw new RuntimeException(
					eleName + " must specify a ref or value");
		}

	}
}
