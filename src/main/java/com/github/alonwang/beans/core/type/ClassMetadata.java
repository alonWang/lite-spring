package com.github.alonwang.beans.core.type;

public interface ClassMetadata {
    String getClassName();
    boolean isInterface();
    boolean isAbstract();
    boolean isFinal();
    boolean hasSuperClass();
    String getSuperClassName();
    String[] getInterfaces();
}
