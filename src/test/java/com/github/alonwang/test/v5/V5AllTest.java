package com.github.alonwang.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author weilong.wang Created on 2018/8/17
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ApplicationContextTest5.class, BeanDefinitionTestV5.class,
		BeanFactoryTestV5.class, CGLibAopProxyTest.class, CGLibTest.class,
		MethodLocatingFactoryTest.class, ReflectiveMethodInvocationTest.class,
		PointcutTest.class })
public class V5AllTest {
}
