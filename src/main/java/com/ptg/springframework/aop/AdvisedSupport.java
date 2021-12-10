package com.ptg.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * AdvisedSupport，主要是用于把目标对象、拦截、匹配的各项属性包装到一个类中，方
 * 便在 Proxy 实现类进行使用。这和业务开发中包装入参是一个道理
 */
public class AdvisedSupport {
    /**
     * 目标对象，在目标对象类中提供 Object 入参属性，以及获
     * 取目标类 TargetClass 信息。
     * @see TargetSource#TargetSource(Object)
     */
    private TargetSource targetSource;
    /**
     * 一个具体拦截方法实现类，由用户自己实现
     * {@link MethodInterceptor#invoke}方法，做具体的处理。
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 方法匹配器(检查目标方法是否符合通知条件) <br/>
     * 由 {@link com.ptg.springframework.aop.aspectj.AspectJExpressionPointcut} 提供服务
     */
    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
