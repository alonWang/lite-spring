package com.github.alonwang.beans.factory.annotation;

import com.github.alonwang.beans.exception.general.BeanCreationException;
import com.github.alonwang.beans.factory.config.AutowiredCapableBeanFactory;
import com.github.alonwang.beans.factory.config.DependencyDescriptor;
import com.github.alonwang.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public class AutowiredFieldElement extends InjectionElement {
	boolean required;

	public AutowiredFieldElement(Field f, boolean requried,
			AutowiredCapableBeanFactory factory) {
		super(f, factory);
		this.required = requried;
	}

	public Field getField() {
		return (Field) this.member;
	}

	@Override
	public void inject(Object target) {
		Field field = this.getField();
		try {
			DependencyDescriptor descriptor = new DependencyDescriptor(field,
					this.required);
			Object value = factory.resolveDependency(descriptor);

			if (value != null) {
				ReflectionUtils.makeAccessible(field);
				field.set(target, value);
			}
		} catch (Throwable ex) {
			throw new BeanCreationException(
					"Could not autowire field: " + field, ex);
		}
	}
}
