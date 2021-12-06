package com.ptg.springframework.context.support;

import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.ptg.springframework.beans.factory.config.BeanPostProcessor;
import com.ptg.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.ptg.springframework.context.ConfigurableApplicationContext;
import com.ptg.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory 并加载BeanDefinition
        refreshBeanFactory();

        // 2. 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 在 Bean 实例化前, 执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessor(beanFactory);

        // 4. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessor(beanFactory);

        // 5. 提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();
    }

    protected void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    protected void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();
}
