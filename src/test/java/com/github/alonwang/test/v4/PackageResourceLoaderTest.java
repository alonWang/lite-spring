package com.github.alonwang.test.v4;

import com.github.alonwang.beans.core.Resource;
import com.github.alonwang.beans.factory.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author weilong.wang Created on 2018/7/14
 */
public class PackageResourceLoaderTest {

	@Test
	public void testGetResources() throws IOException {
		PackageResourceLoader loader = new PackageResourceLoader();
		Resource[] resources = loader
				.getResources("com.github.alonwang.dao.v4");
		Assert.assertEquals(2, resources.length);
	}
}
