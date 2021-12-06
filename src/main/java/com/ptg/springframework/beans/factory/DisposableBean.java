package com.ptg.springframework.beans.factory;

public interface DisposableBean {

    void destroy() throws Exception;

}
