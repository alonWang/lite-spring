package com.github.alonwang.beans;

import java.util.List;

public interface BeanDefinition {
    String getBeanClassName();

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    void addProperty(PropertyValue pv);

    List<PropertyValue> getPropertyValues();
}
