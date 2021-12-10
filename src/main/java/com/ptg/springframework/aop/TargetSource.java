package com.ptg.springframework.aop;

public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetInterfaces(){
        return this.target.getClass().getInterfaces();
    }

    public Class<?> getTargetClass() {
        return this.target.getClass();
    }

    public Object getTarget(){
        return this.target;
    }
}
