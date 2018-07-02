package com.github.alonwang.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author weilong.wang Created on 2018/7/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV2.class, BeanDefinitionTestV2.class, BeanDefinitionValueResolverTest.class, CustomBooleanEditorTest.class, CustomNumberEditorTest.class, TypeConvertTest.class})
public class V2AllTests {
}
