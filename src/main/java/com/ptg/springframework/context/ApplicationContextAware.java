package com.ptg.springframework.context;

import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
