package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.PropertyValue;
import com.github.alonwang.beans.SimpleTypeConverter;
import com.github.alonwang.beans.TypeConverter;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.ConfigurableBeanFactory;
import com.github.alonwang.beans.factory.config.DependencyDescriptor;
import com.github.alonwang.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
		implements ConfigurableBeanFactory, BeanDefinitionRegistry {
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(
			64);
	private ClassLoader beanClassLoader;

	public DefaultBeanFactory() {
	}

	public ClassLoader getBeanClassLoader() {
		return this.beanClassLoader != null ? this.beanClassLoader
				: ClassUtils.getDefaultClassLoader();
	}

	public void setBeanClassLoader(ClassLoader classLoader) {
		beanClassLoader = classLoader;
	}

	public BeanDefinition getBeanDefinition(String beanID) {
		return this.beanDefinitionMap.get(beanID);
	}

	public void registerBeanDefinition(String beanID,
			BeanDefinition beanDefinition) {
		beanDefinitionMap.put(beanID, beanDefinition);

	}

	public Object getBean(String beanID) {
		BeanDefinition bd = this.beanDefinitionMap.get(beanID);
		if (bd == null) {
			throw new BeanCreationException("Bean Definition does not exist");
		}
		if (bd.isSingleton()) {
			Object bean = this.getSingleton(beanID);
			if (bean == null) {
				bean = createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		}
		return createBean(bd);
	}

	private Object createBean(BeanDefinition bd) {
		Object bean = instantiateBean(bd);
		populate(bd, bean);
		return bean;

	}

	protected void populate(BeanDefinition bd, Object bean) {
		List<PropertyValue> pvs = bd.getPropertyValues();

		if (pvs == null || pvs.isEmpty()) {
			return;
		}
		BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(
				this);
		TypeConverter converter = new SimpleTypeConverter();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for (PropertyValue pv : pvs) {
				String propertyName = pv.getName();
				Object originalValue = pv.getValue();
				Object resolvedValue = resolver
						.resolveValueIfNecessary(originalValue);

				for (PropertyDescriptor pd : pds) {
					if (pd.getName().equals(propertyName)) {
						Object convertedValue = converter.convertIfNecessary(
								resolvedValue, pd.getPropertyType());
						pd.getWriteMethod().invoke(bean, convertedValue);
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new BeanCreationException(
					"Failed to obtain BeanInfo for class ["
							+ bd.getBeanClassName() + "]",
					e);
		}

	}

	private Object instantiateBean(BeanDefinition bd) {
		if (bd.hasConstructorArgumentValues()) {
			ConstructorResolver resolver = new ConstructorResolver(this);
			return resolver.autowireConstructor(bd);
		} else {
			ClassLoader cl = getBeanClassLoader();
			String beanName = bd.getBeanClassName();
			try {
				Class<?> clazz = cl.loadClass(beanName);
				return clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new BeanCreationException(
						"Create bean for [" + beanName + "] failed", e);
			}
		}
	}

	public Object resolveDependency(DependencyDescriptor descriptor) {
		Class<?> typeToMatch = descriptor.getDependencyType();
		for (BeanDefinition bd : this.beanDefinitionMap.values()) {
			resolveBeanClass(bd);
			Class<?> beanClass = bd.getBeanClass();
			if (typeToMatch.isAssignableFrom(beanClass)) {
				return this.getBean(bd.getID());
			}
		}
		return null;
	}

	private void resolveBeanClass(BeanDefinition bd) {
		if (bd.hasBeanClass()) {
			return;
		}
		try {
			bd.resolveBeanClass(this.getBeanClassLoader());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(
					"can't load class:" + bd.getBeanClassName());
		}

	}
}
