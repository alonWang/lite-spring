package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.ConstructorArgument;
import com.github.alonwang.beans.SimpleTypeConverter;
import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.ConfigurableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author weilong.wang Created on 2018/7/4
 */
public class ConstructorResolver {
	protected final Log logger = LogFactory.getLog(getClass());
	private final ConfigurableBeanFactory beanFactory;

	public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Object autowireConstructor(final BeanDefinition bd) {
		Constructor<?> constructorToUse = null;
		Object[] argsToUse = null;
		Class<?> beanClass = null;
		try {
			beanClass = this.beanFactory.getBeanClassLoader()
					.loadClass(bd.getBeanClassName());
		} catch (ClassNotFoundException e) {
			throw new BeanCreationException(bd.getID(),
					"Instantiation of bean failed,can't resolver class", e);
		}
		Constructor<?>[] candidates = beanClass.getConstructors();

		BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(
				this.beanFactory);
		ConstructorArgument cargs = bd.getConstructArgument();
		SimpleTypeConverter typeConverter = new SimpleTypeConverter();

		for (Constructor<?> candidate : candidates) {
			Class<?>[] parameterTypes = candidate.getParameterTypes();
			if (parameterTypes.length != cargs.getArgumentCount()) {
				continue;
			}
			argsToUse = new Object[parameterTypes.length];
			boolean result = this.valuesMatchTypes(parameterTypes,
					cargs.getArgumentValues(), argsToUse, valueResolver,
					typeConverter);
			if (result) {
				constructorToUse = candidate;
				break;
			}
		}
		if (constructorToUse == null) {
			throw new BeanCreationException(bd.getID(),
					"can't find a apporiate constructor");
		}
		try {
			return constructorToUse.newInstance(argsToUse);
		} catch (Exception e) {
			throw new BeanCreationException(bd.getID(),
					"can't find a create instance using " + constructorToUse);
		}

	}

	private boolean valuesMatchTypes(Class<?>[] parameterTypes,
			List<ConstructorArgument.ValueHolder> valueHolders,
			Object[] argsToUse, BeanDefinitionValueResolver valueResolver,
			SimpleTypeConverter typeConverter) {
		for (int i = 0; i < parameterTypes.length; i++) {
			ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
			Object originalValue = valueHolder.getValue();

			try {
				Object resolvedValue = valueResolver
						.resolveValueIfNecessary(originalValue);
				Object convertedValue = typeConverter
						.convertIfNecessary(resolvedValue, parameterTypes[i]);
				argsToUse[i] = convertedValue;
			} catch (Exception e) {
				logger.error(e);
				return false;
			}
		}
		return true;
	}
}
