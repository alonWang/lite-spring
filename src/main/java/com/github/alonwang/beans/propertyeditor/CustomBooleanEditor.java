package com.github.alonwang.beans.propertyeditor;

import cn.hutool.core.util.StrUtil;

import java.beans.PropertyEditorSupport;

/**
 * @author weilong.wang Created on 2018/7/2
 */
public class CustomBooleanEditor extends PropertyEditorSupport {
    public static final String VALUE_TRUE = "true";
    public static final String VALUE_FALSE = "false";

    public static final String VALUE_ON = "on";
    public static final String VALUE_OFF = "off";

    public static final String VALUE_YES = "yes";
    public static final String VALUE_NO = "no";

    public static final String VALUE_1 = "1";
    public static final String VALUE_0 = "0";
    private boolean allowEmpty;

    public CustomBooleanEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String getAsText() {
        if (Boolean.TRUE.equals(getValue())) {
            return VALUE_TRUE;
        } else if (Boolean.FALSE.equals(getValue())) {
            return VALUE_FALSE;
        } else {
            return "";
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String input = (text != null ? text.trim() : null);
        if (this.allowEmpty && StrUtil.isEmpty(input)) {
            setValue(null);
        } else if (VALUE_TRUE.equalsIgnoreCase(input) || VALUE_ON.equalsIgnoreCase(input) || VALUE_YES.equalsIgnoreCase(input) || VALUE_1.equalsIgnoreCase(input)) {
            setValue(Boolean.TRUE);
        } else if (VALUE_FALSE.equalsIgnoreCase(input) || VALUE_OFF.equalsIgnoreCase(input) || VALUE_0.equalsIgnoreCase(input) || VALUE_NO.equalsIgnoreCase(input)) {
            setValue(Boolean.FALSE);
        } else {
            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
        }
    }

}
