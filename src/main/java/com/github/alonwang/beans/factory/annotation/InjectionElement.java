package com.github.alonwang.beans.factory.annotation;

import com.github.alonwang.beans.factory.config.AutowiredCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author weilong.wang Created on 2018/8/2
 */
public abstract class InjectionElement {
	protected Member member;
	protected AutowiredCapableBeanFactory factory;

	public InjectionElement(Member member,
			AutowiredCapableBeanFactory factory) {
		this.member = member;
		this.factory = factory;
	}

	public abstract void inject(Object target);
}
