package com.github.alonwang.aop.framework;

public interface AopProxyFactory {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
