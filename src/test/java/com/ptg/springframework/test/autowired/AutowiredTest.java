package com.ptg.springframework.test.autowired;

import com.ptg.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class AutowiredTest {
    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_autowired.xml");
        IUserService15 userService = applicationContext.getBean("userService15", IUserService15.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}
