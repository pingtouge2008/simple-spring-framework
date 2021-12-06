package com.ptg.springframework.bean;


import com.ptg.springframework.beans.factory.DisposableBean;
import com.ptg.springframework.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {
    private String userId;
    private String company;
    private String location;
    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println(toString());
        System.out.println("查询用户信息：" + userDao.queryUserName(userId));
    }

    @Override
    public String toString() {
        return "UserService{" +
                "userId='" + userId + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", userDao=" + userDao +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
    }
}
