package com.ptg.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.PropertyValues;
import com.ptg.springframework.beans.factory.BeanFactory;
import com.ptg.springframework.beans.factory.BeanFactoryAware;
import com.ptg.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.ptg.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.ptg.springframework.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * <ul>
 * <li>AutowiredAnnotationBeanPostProcessor 是实现接口
 * InstantiationAwareBeanPostProcessor 的一个用于在 Bean 对象<strong style="color:#ff0000;">实例化完成后</strong>，
 * 设置属性操作前的处理属性信息的类和操作方法。只有实现了 BeanPostProcessor接口才有机会在 Bean 的生命周期中处理初始化信息
 * <li>核心方法 postProcessPropertyValues用于处理类含有 @Value、@Autowired 注解的属性，进行属性信息的提取和设置。
 * <li>需要注意一点因为我们在 AbstractAutowireCapableBeanFactory 类中使用的是
 * CglibSubclassingInstantiationStrategy 进行类的创建，所以在
 * AutowiredAnnotationBeanPostProcessor#postProcessPropertyValues
 * 中需要判断是否为 CGlib 创建对象，否则是不能正确拿到类信息的。
 * </ul>
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            // 1. 处理注解 @Value
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
            // 2. 处理注解 @Autowired
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                Class<?> fieldType = field.getType();
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean;
                if (null != qualifierAnnotation) {
                    String dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return pvs;
    }

}
