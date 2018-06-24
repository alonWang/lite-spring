package com.github.alonwang.util;

public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try{
            cl=Thread.currentThread().getContextClassLoader();
        }catch (Throwable ex){
            //do nothing
        }
        if(cl==null){
            cl=ClassUtils.class.getClassLoader();
            if(cl==null){
                try {
                    cl=ClassLoader.getSystemClassLoader();
                }catch (Throwable ex){
                    //do nothing
                }
            }
        }
        return cl;
    }
}
