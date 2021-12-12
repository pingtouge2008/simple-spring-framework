package com.ptg.springframework.aop;

import com.ptg.springframework.util.ClassUtils;

public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetInterfaces(){
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    public Class<?> getTargetClass() {
        return this.target.getClass();
    }

    public Object getTarget(){
        return this.target;
    }
}
