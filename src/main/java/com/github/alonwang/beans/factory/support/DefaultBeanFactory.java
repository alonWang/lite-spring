package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.BeanDefinitionRegistry;
import com.github.alonwang.beans.PropertyValue;
import com.github.alonwang.beans.SimpleTypeConverter;
import com.github.alonwang.beans.TypeConverter;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.BeanFactoryAware;
import com.github.alonwang.beans.factory.NoSuchBeanDefinitionException;
import com.github.alonwang.beans.factory.config.BeanPostProcessor;
import com.github.alonwang.beans.factory.config.DependencyDescriptor;
import com.github.alonwang.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.github.alonwang.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends AbstractBeanFactory
		implements BeanDefinitionRegistry {
	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
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

	public List<Object> getBeansByType(Class<?> type) {
		List<Object> result = new ArrayList<Object>();
		List<String> beanIDs = this.getBeanIDsByType(type);
		for (String beanID : beanIDs) {
			result.add(this.getBean(beanID));
		}
		return result;
	}

	private List<String> getBeanIDsByType(Class<?> type) {
		List<String> result = new ArrayList<String>();
		for (String beanName : this.beanDefinitionMap.keySet()) {
			if (type.isAssignableFrom(this.getType(beanName))) {
				result.add(beanName);
			}
		}
		return result;
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

	public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		BeanDefinition bd = this.getBeanDefinition(name);
		if (bd == null) {
			throw new NoSuchBeanDefinitionException(name);
		}
		resolveBeanClass(bd);
		return bd.getBeanClass();
	}

	protected Object createBean(BeanDefinition bd) {
		Object bean = instantiateBean(bd);
		populate(bd, bean);
		bean = initializeBean(bd, bean);
		return bean;

	}

	protected Object initializeBean(BeanDefinition bd, Object bean) {
		invokeAwareMethods(bean);
		// Todo，对Bean做初始化
		// 创建代理
		return bean;
	}

	private void invokeAwareMethods(final Object bean) {
		if (bean instanceof BeanFactoryAware) {
			((BeanFactoryAware) bean).setBeanFactory(this);
		}
	}
	protected void populate(BeanDefinition bd, Object bean) {
		for (BeanPostProcessor processor : this.getBeanPostProcessors()) {
			if (processor instanceof InstantiationAwareBeanPostProcessor) {
				((InstantiationAwareBeanPostProcessor) processor)
						.postProcessPropertyValues(bean, bd.getID());
			}
		}
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

	public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
		this.beanPostProcessors.add(postProcessor);
	}

	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}
}
