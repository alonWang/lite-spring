package com.github.alonwang.aop;

public interface PointCut {
    MethodMatcher getMethodMatcher();

    String getExpression();
}
