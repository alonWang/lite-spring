package com.github.alonwang.beans;


import com.github.alonwang.beans.exception.TypeMismatchException;

/**
 * @author weilong.wang Created on 2018/7/2
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
