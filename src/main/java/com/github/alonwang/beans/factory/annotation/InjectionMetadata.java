package com.github.alonwang.beans.factory.annotation;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public class InjectionMetadata {
	private final Class<?> targetClass;
	private List<InjectionElement> injectionElements;

	public InjectionMetadata(Class<?> targetClass,
			List<InjectionElement> injectionElements) {
		this.targetClass = targetClass;
		this.injectionElements = injectionElements;
	}

	public List<InjectionElement> getInjectionElements() {
		return injectionElements;
	}

	public void inject(Object target) {
		if (CollectionUtil.isEmpty(injectionElements)) {
			return;
		}
		for (InjectionElement ele : injectionElements) {
			ele.inject(target);
		}
	}
}
