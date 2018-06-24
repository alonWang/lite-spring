package com.github.alonwang.beans.exception.general;

import com.github.alonwang.beans.exception.BeanException;

public class BeanDefinitionStoreException extends BeanException {
    public BeanDefinitionStoreException(String message) {
        super(message);
    }

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
