package com.github.alonwang.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.core.annotation.AnnotationAttributes;
import com.github.alonwang.beans.core.type.AnnotationMetadata;
import com.github.alonwang.beans.factory.annotation.AnnotatedBeanDefinition;
import com.github.alonwang.beans.factory.support.BeanNameGenerator;
import com.github.alonwang.stereotype.Component;
import com.github.alonwang.util.ClassUtils;

import java.beans.Introspector;
import java.util.Map;
import java.util.Set;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {
	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		if (definition instanceof AnnotatedBeanDefinition) {
			String beanname = determineBeanNameFromAnnotation(
					(AnnotatedBeanDefinition) definition);
			if (!StrUtil.isEmpty(beanname)) {
				return beanname;
			}
		}
		return buildDefaultBeanName(definition, registry);
	}

	protected String buildDefaultBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		return buildDefaultBeanName(definition);
	}

	protected String buildDefaultBeanName(BeanDefinition definition) {
		String shortClassName = ClassUtils
				.getShortName(definition.getBeanClassName());
		return Introspector.decapitalize(shortClassName);
	}

	protected String determineBeanNameFromAnnotation(
			AnnotatedBeanDefinition definition) {
		AnnotationMetadata amd = definition.getMetadata();
		Set<String> types = amd.getAnnotationTypes();
		String beanName = null;
		for (String type : types) {
			AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
			Object value = null;
			if (isStereotypeWithNameValue(type, attributes)) {
				if (value instanceof String) {
					String strVal = (String) value;
					if (!StrUtil.isEmpty(strVal)) {
						beanName = strVal;
					}
				}
			}
		}
		return beanName;
	}

	protected boolean isStereotypeWithNameValue(String annotationType,
			Map<String, Object> attributes) {
		boolean isStereotype = annotationType.equals(Component.class.getName());
		return isStereotype && attributes != null
				&& attributes.containsKey("value");
	}
}
