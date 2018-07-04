package com.github.alonwang.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author weilong.wang Created on 2018/7/4
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ApplicationContextTestV3.class,
		BeanDefinitionTestV3.class, ConstructorResolverTest.class })
public class V3AllTest {
}
