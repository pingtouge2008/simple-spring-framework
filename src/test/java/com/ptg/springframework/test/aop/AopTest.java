package com.ptg.springframework.test.aop;

import com.ptg.springframework.aop.AdvisedSupport;
import com.ptg.springframework.aop.TargetSource;
import com.ptg.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.ptg.springframework.aop.framework.CglibAopProxy;
import com.ptg.springframework.aop.framework.JdkDynamicAopProxy;
import com.ptg.springframework.bean.IUserService;
import com.ptg.springframework.bean.UserService;
import com.ptg.springframework.bean.interceptor.UserServiceInterceptor;
import org.junit.Test;

public class AopTest {

    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new UserService();
        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.ptg.springframework.bean.IUserService.query*(..))"));
        // 代理对象(JdkDynamicAopProxy)
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        proxy_jdk.queryUserInfo();
        // 不符合切点表达式 不会被代理
        proxy_jdk.getById();


        // 代理对象(CglibAopProxy)
        IUserService proxy_cglib = (IUserService) new CglibAopProxy(advisedSupport).getProxy();
        // 测试调用
        proxy_cglib.queryUserInfo();
        // 不符合切点表达式 不会被代理
        proxy_cglib.getById();
    }

}
