package com.github.alonwang.beans.exception.general;

import com.github.alonwang.beans.exception.BeanException;

public class BeanCreationException extends BeanException {
    private String beanName;

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(String beanName, String message) {
        super("Error create bean with name [" + beanName + "]:" + message);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String message, Throwable cause) {
        this(beanName, message);
        initCause(cause);
    }
}
