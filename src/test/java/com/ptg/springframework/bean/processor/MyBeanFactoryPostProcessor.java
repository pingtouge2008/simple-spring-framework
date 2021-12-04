package com.ptg.springframework.bean.processor;

import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.PropertyValue;
import com.ptg.springframework.beans.PropertyValues;
import com.ptg.springframework.beans.factory.config.BeanDefinition;
import com.ptg.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.ptg.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}
