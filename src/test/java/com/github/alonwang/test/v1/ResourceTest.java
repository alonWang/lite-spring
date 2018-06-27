package com.github.alonwang.test.v1;

import cn.hutool.core.io.FileUtil;
import com.github.alonwang.beans.core.ClassPathResource;
import com.github.alonwang.beans.core.FileSystemResource;
import com.github.alonwang.beans.core.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {
    @Test
    public void testClassPathResource() {
        Resource resource = new ClassPathResource("petStore-v1.xml");
        InputStream is = null;
        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } catch (IOException e) {
            Assert.fail();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }

            }
        }
    }

    @Test
    public void testFileSystemResource() {
        Resource resource = new FileSystemResource(FileUtil.getAbsolutePath("petStore-v1.xml"));
        InputStream is = null;
        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } catch (Exception e) {
            Assert.fail();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {

                }
            }
        }
    }
}
