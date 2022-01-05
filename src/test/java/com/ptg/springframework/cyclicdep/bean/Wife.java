package com.ptg.springframework.cyclicdep.bean;

public class Wife {
    private Husband husband;
    private IMother mother; // 婆婆

    public String queryHusband() {
        return "Wife.husband、Mother.callMother：" + mother.callMother();
    }
}
