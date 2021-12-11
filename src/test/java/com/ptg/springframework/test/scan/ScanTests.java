package com.ptg.springframework.test.scan;

import com.ptg.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ScanTests {

    @Test
    public void test_property() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_property.xml");
        IUserService02 userService = applicationContext.getBean("userService02", IUserService02.class);
        System.out.println("测试结果：" + userService);
    }

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_scan.xml");
        IUserService02 userService = applicationContext.getBean("userService", IUserService02.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }


}
