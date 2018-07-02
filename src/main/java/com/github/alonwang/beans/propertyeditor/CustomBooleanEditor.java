package com.github.alonwang.beans.propertyeditor;

import java.beans.PropertyEditorSupport;

/**
 * @author weilong.wang Created on 2018/7/2
 */
public class CustomBooleanEditor extends PropertyEditorSupport {
    private boolean allowEmpty;

    public CustomBooleanEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }

    @Override
    public void setAsText(String s) throws IllegalArgumentException {
        super.setAsText(s);
    }
}
