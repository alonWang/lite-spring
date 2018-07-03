package com.github.alonwang.beans;

import com.github.alonwang.beans.exception.TypeMismatchException;
import com.github.alonwang.beans.propertyeditor.CustomBooleanEditor;
import com.github.alonwang.beans.propertyeditor.CustomNumberEditor;
import com.github.alonwang.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weilong.wang Created on 2018/7/2
 */
public class SimpleTypeConverter implements TypeConverter {
    private Map<Class<?>, PropertyEditor> defaultEditors;

    public SimpleTypeConverter() {
    }

    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(requiredType, value)) {
            return (T) value;
        } else {
            if (value instanceof String) {
                PropertyEditor editor = findDefaultEditor(requiredType);
                try {
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, requiredType);
                }
                return (T) editor.getValue();
            } else {
                throw new RuntimeException("Can't convert value for " + value + " class:" + requiredType);
            }
        }
    }

    private PropertyEditor findDefaultEditor(Class<?> requiredType) {
        if (defaultEditors == null) {
            createDefaultEditors();
        }
        return this.defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<Class<?>, PropertyEditor>(64);
        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
    }
}
