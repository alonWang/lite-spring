package com.github.alonwang.aop;

import org.aopalliance.intercept.MethodInterceptor;

public interface Advice extends MethodInterceptor {
    public Pointcut getPointcut();
}
