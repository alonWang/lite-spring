package com.github.alonwang.aop;

public interface Pointcut {
    MethodMatcher getMethodMatcher();

    String getExpression();
}
