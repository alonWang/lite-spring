package com.github.alonwang.aop.config;

import com.github.alonwang.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

public interface AopConfig {
    Class<?> getTargetClass();

    Object getTargetObject();

    void setTargetObject(Object obj);

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    List<Advice> getAdvices();

    void addAdvice(Advice advice) ;

    List<Advice> getAdvices(Method method/*,Class<?> targetClass*/);
}
