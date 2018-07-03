package com.github.alonwang.test.v2;

import com.github.alonwang.beans.propertyeditor.CustomBooleanEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author weilong.wang Created on 2018/7/2
 */
public class CustomBooleanEditorTest {
    @Test
    public void testConvertStringToBoolean() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);
        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("on");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("yes");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("1");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("0");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());

        try {
            editor.setAsText("agabgeage");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
