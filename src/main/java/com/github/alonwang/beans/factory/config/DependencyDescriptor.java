package com.github.alonwang.beans.factory.config;

import java.lang.reflect.Field;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public class DependencyDescriptor {
	private Field field;
	private boolean required;

	public DependencyDescriptor(Field field, boolean required) {
		this.field = field;
		this.required = required;
	}

	public Class<?> getDependencyType() {
		if (this.field != null) {
			return field.getType();
		}
		throw new RuntimeException("only support field dependency");
	}

	public boolean isRequired() {
		return this.required;
	}

}
