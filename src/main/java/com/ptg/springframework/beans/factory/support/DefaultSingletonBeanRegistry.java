package com.ptg.springframework.beans.factory.support;

import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.factory.DisposableBean;
import com.ptg.springframework.beans.factory.ObjectFactory;
import com.ptg.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认单例注册表的实现
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存，普通对象
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    /**
     * 二级缓存，提前暴漏对象，没有完全实例化的对象
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();
    /**
     * 三级缓存，存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    /**
     * Internal marker for a null singleton object:
     * used as marker value for concurrent Maps (which don't support null values).
     */
    protected static final Object NULL_OBJECT = new Object();

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        // 从一级缓存中获取, 如果有直接返回
        Object singletonObject = singletonObjects.get(beanName);
        // 如果没有从一级缓存获取到
        if (null == singletonObject) {
            // 再从二级缓存获取, 但这是这个对象是半成品Bean(属性未填充)或者是
            // proxyBean(属性不一定要是未填充, 注意"proxy"语义), 获取到了直接返回
            singletonObject = earlySingletonObjects.get(beanName);
            // 如果二级缓存也没获取到，可以说明这个对象可能是代理对象，也有可能是没被代理的对象
            // 要看那个lambda: () -> getEarlyBeanReference(beanName, beanDefinition, finalBean)
            // 有没有返回代理对象, 如果返回的是原来的bean就是没有被代理的, 否则就是被代理过的
            if (null == singletonObject) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    // 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            String beanName = disposableBeanNames[i].toString();
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }


}
