package com.ptg.springframework.beans.factory.support;

import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.core.io.Resource;
import com.ptg.springframework.core.io.ResourceLoader;

/**
 * 这个接口定义了:
 * <ul>
 * <li>获取BeanDefinition注册表
 * <li>获取ResourceLoader
 * <li>三个加载 BeanDefinition 的方法
 * </ul>
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

}
