package com.github.alonwang.beans.core.annotation;

import java.lang.annotation.*;

/**
 * @author weilong.wang Created on 2018/7/13
 */
@Target({ ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD,
		ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
	boolean required() default true;
}
