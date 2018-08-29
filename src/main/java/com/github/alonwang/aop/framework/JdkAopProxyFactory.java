package com.github.alonwang.aop.framework;

import com.github.alonwang.aop.Advice;
import com.github.alonwang.aop.config.AopConfig;
import com.github.alonwang.util.Assert;
import com.github.alonwang.util.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weilong.wang Created on 2018/8/29
 */
public class JdkAopProxyFactory implements AopProxyFactory, InvocationHandler {
	private static final Log logger = LogFactory
			.getLog(JdkAopProxyFactory.class);

	private final AopConfig config;

	public JdkAopProxyFactory(AopConfig config) {
		Assert.notNull(config, "AdvisedSupport must not be null");
		if (config.getAdvices().size() == 0) {
			throw new AopConfigException("No advices specified");
		}
		this.config = config;
	}

	public Object getProxy() {
		return getProxy(ClassUtils.getDefaultClassLoader());
	}

	public Object getProxy(ClassLoader classLoader) {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating JDK dynamic proxy: target source is "
					+ this.config.getTargetObject());
		}
		Class<?>[] proxiedInterfaces = config.getProxiedInterfaces();

		return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
	}

	public Object invoke(Object o, Method method, Object[] args)
			throws Throwable {
		Object target = this.config.getTargetObject();

		Object retVal;

		List<Advice> chain = this.config.getAdvices(method);
		if (chain.isEmpty()) {
			retVal = method.invoke(target, args);
		} else {
			List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
			interceptors.addAll(chain);
			retVal = new ReflectiveMethodInvocation(target, method, args,
					interceptors).proceed();
		}
		return retVal;
	}
}
