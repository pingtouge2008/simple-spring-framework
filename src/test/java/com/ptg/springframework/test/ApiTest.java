package com.ptg.springframework.test;

import com.ptg.springframework.bean.UserDao;
import com.ptg.springframework.bean.UserService;
import com.ptg.springframework.bean.processor.MyBeanFactoryPostProcessor;
import com.ptg.springframework.bean.processor.MyBeanPostProcessor;
import com.ptg.springframework.beans.PropertyValue;
import com.ptg.springframework.beans.PropertyValues;
import com.ptg.springframework.beans.factory.config.BeanDefinition;
import com.ptg.springframework.beans.factory.config.BeanReference;
import com.ptg.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.ptg.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.ptg.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();


        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();
    }


    @Test
    public void test_BeanFactory2() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. UserService 设置属性[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);

        // 4. UserService 注入bean
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. UserService 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }


    /**
     * 不用应用上下文
     */
    @Test
    public void xml_without_BFPP_and_BPP() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. 读取配置文件 & 注册 Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring_with_processor.xml");
        // 3. BeanDefinition 加载完成 & Bean 实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        // 4. Bean 实例化之后，修改 Bean 属性信息
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);
        // 5. 获取 Bean 对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void test_xml() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_with_processor.xml");
        // 2. 获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void test_init_destroy() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_with_processor.xml");
        applicationContext.registerShutdownHook();
        // 2. 获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void test_aware() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_with_processor.xml");
        applicationContext.registerShutdownHook();
        // 2. 获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
        System.out.println(userService.getBeanFactory());
        System.out.println(userService.getApplicationContext());
    }

    @Test
    public void test_scope() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_factory_bean.xml");
        applicationContext.registerShutdownHook();
        // 2. 获取 Bean 对象调用方法
        UserService userService01 = applicationContext.getBean("userService", UserService.class);
        UserService userService02 = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService01 == userService02);

        userService01.queryUserInfo();
    }
}
