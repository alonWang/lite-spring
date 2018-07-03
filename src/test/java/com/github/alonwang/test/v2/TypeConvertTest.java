package com.github.alonwang.test.v2;

import com.github.alonwang.beans.SimpleTypeConverter;
import com.github.alonwang.beans.TypeConverter;
import com.github.alonwang.beans.exception.TypeMismatchException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/7/2
 */
public class TypeConvertTest {
    @Test
    public void testConvertString2Int() {
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());
        try {
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvertString2Boolean() {
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("True", Boolean.class);
        Assert.assertEquals(true, b);
        try {
            converter.convertIfNecessary("fsdfea", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }

}
