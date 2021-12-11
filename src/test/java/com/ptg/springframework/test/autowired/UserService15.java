package com.ptg.springframework.test.autowired;

import com.ptg.springframework.beans.factory.annotation.Autowired;
import com.ptg.springframework.beans.factory.annotation.Value;
import com.ptg.springframework.stereotype.Component;

import java.util.Random;

@Component("userService15")
public class UserService15 implements IUserService15 {

    @Value("${token}")
    private String token;

    @Autowired
    private UserDao15 userDao15;

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao15.queryUserName("10001") + "，" + token;
    }
}