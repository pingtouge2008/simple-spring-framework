package com.ptg.springframework.cyclicdep.bean;

public class Husband {
    private Wife wife;

    public String queryWife() {
        return "Husband.wife";
    }
}