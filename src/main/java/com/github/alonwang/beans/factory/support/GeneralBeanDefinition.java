package com.github.alonwang.beans.factory.support;

import com.github.alonwang.beans.BeanDefinition;
import com.github.alonwang.beans.ConstructorArgument;
import com.github.alonwang.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GeneralBeanDefinition implements BeanDefinition {
	private String id;
	private String beanClassName;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;
	private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
	private ConstructorArgument constructorArgument = new ConstructorArgument();

	public GeneralBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	public boolean hasConstructorArgumentValues() {
		return !this.constructorArgument.isEmpty();
	}

	public String getBeanClassName() {
		return this.beanClassName;
	}

	public boolean isSingleton() {
		return this.singleton;
	}

	public boolean isPrototype() {
		return this.prototype;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope)
				|| SCOPE_DEFAULT.endsWith(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
	}

	public void addProperty(PropertyValue pv) {
		propertyValues.add(pv);
	}

	public List<PropertyValue> getPropertyValues() {
		return this.propertyValues;
	}

	public ConstructorArgument getConstructArgument() {
		return this.constructorArgument;
	}

	public String getID() {
		return this.id;
	}
}
