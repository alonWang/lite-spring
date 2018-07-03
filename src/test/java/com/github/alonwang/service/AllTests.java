package com.github.alonwang.service;

import com.github.alonwang.test.v1.V1AllTest;
import com.github.alonwang.test.v2.V2AllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author weilong.wang Created on 2018/7/3
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTest.class, V2AllTests.class})
public class AllTests {
}
