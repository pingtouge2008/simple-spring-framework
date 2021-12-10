package com.ptg.springframework.aop.framework;

public interface AopProxy {

    /**
     * 获取被代理过的对象
     * @return 被代理后的对象
     */
    Object getProxy();
}
