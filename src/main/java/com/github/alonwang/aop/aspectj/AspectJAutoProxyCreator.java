package com.github.alonwang.aop.aspectj;

import com.github.alonwang.aop.Advice;
import com.github.alonwang.aop.MethodMatcher;
import com.github.alonwang.aop.Pointcut;
import com.github.alonwang.aop.framework.AopConfigSupport;
import com.github.alonwang.aop.framework.AopProxyFactory;
import com.github.alonwang.aop.framework.CglibProxyFactory;
import com.github.alonwang.beans.exception.BeanException;
import com.github.alonwang.beans.factory.ConfigurableBeanFactory;
import com.github.alonwang.beans.factory.config.BeanPostProcessor;
import com.github.alonwang.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AspectJAutoProxyCreator implements BeanPostProcessor {
	ConfigurableBeanFactory beanFactory;

	public static boolean canApply(Pointcut pc, Class<?> targetClass) {

		MethodMatcher methodMatcher = pc.getMethodMatcher();

		Set<Class> classes = new LinkedHashSet<Class>(
				ClassUtils.getAllInterfacesForClassAsSet(targetClass));
		classes.add(targetClass);
		for (Class<?> clazz : classes) {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (methodMatcher.matches(method/* , targetClass */)) {
					return true;
				}
			}
		}

		return false;
	}

	public Object beforeInitialization(Object bean, String beanName)
			throws BeanException {
		return bean;
	}

	public Object afterInitialization(Object bean, String beanName)
			throws BeanException {

		// 如果这个Bean本身就是Advice及其子类，那就不要再生成动态代理了。
		if (isInfrastructureClass(bean.getClass())) {
			return bean;
		}

		List<Advice> advices = getCandidateAdvices(bean);
		if (advices.isEmpty()) {
			return bean;
		}

		return createProxy(advices, bean);
	}

	private List<Advice> getCandidateAdvices(Object bean) {

		List<Object> advices = this.beanFactory.getBeansByType(Advice.class);

		List<Advice> result = new ArrayList<Advice>();
		for (Object o : advices) {
			Pointcut pc = ((Advice) o).getPointcut();
			if (canApply(pc, bean.getClass())) {
				result.add((Advice) o);
			}

		}
		return result;
	}

	protected Object createProxy(List<Advice> advices, Object bean) {

		AopConfigSupport config = new AopConfigSupport();
		for (Advice advice : advices) {
			config.addAdvice(advice);
		}

		Set<Class> targetInterfaces = ClassUtils
				.getAllInterfacesForClassAsSet(bean.getClass());
		for (Class<?> targetInterface : targetInterfaces) {
			config.addInterface(targetInterface);
		}

		config.setTargetObject(bean);

		AopProxyFactory proxyFactory = null;
		if (config.getProxiedInterfaces().length == 0) {
			proxyFactory = new CglibProxyFactory(config);
		} else {
			// TODO 需要实现JDK 代理
			// proxyFactory = new JdkAopProxyFactory(config);
		}

		return proxyFactory.getProxy();

	}

	protected boolean isInfrastructureClass(Class<?> beanClass) {
		boolean retVal = Advice.class.isAssignableFrom(beanClass);

		return retVal;
	}

	public void setBeanFactory(ConfigurableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;

	}

}
