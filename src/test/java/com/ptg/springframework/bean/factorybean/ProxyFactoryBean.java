package com.ptg.springframework.bean.factorybean;

import cn.hutool.core.util.ClassUtil;
import com.ptg.springframework.bean.UserDao;
import com.ptg.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyFactoryBean implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler = new InvocationHandler() {
            private IUserDao userDao = new UserDao();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                if (method.getName().equals("queryUserName")) {
                    Map<String, String> hashMap = new HashMap<>();
                    hashMap.put("10001", "伟哥");
                    hashMap.put("10002", "八杯水");
                    hashMap.put("10003", "阿毛");
                    return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
                } else {
                    return method.invoke(userDao, args);
                }
            }
        };
        return (IUserDao) Proxy.newProxyInstance(ClassUtil.getClassLoader(),
                new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
