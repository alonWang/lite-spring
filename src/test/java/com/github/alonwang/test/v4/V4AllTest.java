package com.github.alonwang.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author weilong.wang Created on 2018/8/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ApplicationContextTestV4.class,
		ClassPathBeanDefinitionScannerTest.class, ClassReaderTest.class,
		MetadataReaderTest.class, PackageResourceLoaderTest.class,
		XmlBeanDefinitionReaderTest.class })
public class V4AllTest {
}
