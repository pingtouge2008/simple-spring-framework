package com.ptg.springframework.beans.factory.config;

import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    default PropertyValues postProcessPropertyValues(
            PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
