package com.github.alonwang.beans;

import java.util.List;

public interface BeanDefinition {
	String SCOPE_SINGLETON = "singleton";
	String SCOPE_PROTOTYPE = "prototype";
	String SCOPE_DEFAULT = "";

	boolean hasConstructorArgumentValues();

	String getBeanClassName();

	boolean isSingleton();

	boolean isPrototype();

	String getScope();

	void setScope(String scope);

	void addProperty(PropertyValue pv);

	List<PropertyValue> getPropertyValues();

	ConstructorArgument getConstructArgument();

	String getID();
}
