package com.ptg.springframework.context.event;

import com.ptg.springframework.beans.factory.BeanFactory;
import com.ptg.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.ptg.springframework.context.ApplicationEvent;
import com.ptg.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
