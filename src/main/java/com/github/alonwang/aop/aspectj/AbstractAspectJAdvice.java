package com.github.alonwang.aop.aspectj;

import com.github.alonwang.aop.Advice;
import com.github.alonwang.aop.Pointcut;
import com.github.alonwang.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {
	protected Method adviceMethod;
	protected AspectJExpressionPointcut pointcut;
	protected AspectInstanceFactory adviceObjectFactory;

	public AbstractAspectJAdvice(Method adviceMethod,
			AspectJExpressionPointcut pointcut,
			AspectInstanceFactory adviceObjectFactory) {

		this.adviceMethod = adviceMethod;
		this.pointcut = pointcut;
		this.adviceObjectFactory = adviceObjectFactory;
	}

	public void invokeAdviceMethod() throws Throwable {

		adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
	}

	public Pointcut getPointcut() {
		return this.pointcut;
	}

	public Method getAdviceMethod() {
		return adviceMethod;
	}
}
