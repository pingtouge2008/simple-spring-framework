package com.ptg.springframework.beans.factory.support;

import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) throws BeansException;
}
