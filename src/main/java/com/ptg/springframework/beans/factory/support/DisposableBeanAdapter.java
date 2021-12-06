package com.ptg.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.factory.DisposableBean;
import com.ptg.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;
    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 配置信息 destroy-method { 判断是为了避免二次执行销毁 }
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean)
                && "destroy".equals(destroyMethodName)) {
            Method destroyMethod = bean.getClass().getDeclaredMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" +
                        destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }


}
