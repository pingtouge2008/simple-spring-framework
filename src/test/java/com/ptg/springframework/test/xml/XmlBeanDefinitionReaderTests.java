package com.ptg.springframework.test.xml;

import com.ptg.springframework.bean.UserService;
import com.ptg.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.ptg.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.junit.Test;

public class XmlBeanDefinitionReaderTests {

    @Test
    public void test_load_spring_xml() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. 读取配置文件 & 注册 Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");
        // 3. 获取 Bean 对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

}
