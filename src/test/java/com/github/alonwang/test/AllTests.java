package com.github.alonwang.test;

import com.github.alonwang.test.v1.V1AllTest;
import com.github.alonwang.test.v2.V2AllTests;
import com.github.alonwang.test.v3.V3AllTest;
import com.github.alonwang.test.v4.V4AllTest;
import com.github.alonwang.test.v5.V5AllTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author weilong.wang Created on 2018/7/3
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ V1AllTest.class, V2AllTests.class, V3AllTest.class,
		V4AllTest.class, V5AllTest.class })
public class AllTests {
}
