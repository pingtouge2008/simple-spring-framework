package com.ptg.springframework.beans.factory;

import com.ptg.springframework.beans.BeansException;

public interface BeanFactoryAware extends Aware {

	void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
