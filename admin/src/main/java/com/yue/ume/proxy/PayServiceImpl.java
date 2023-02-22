package com.yue.ume.proxy;

public class PayServiceImpl implements PayService {
    @Override
    public String callBack(String name) {
        System.out.println("name = " + name);
        return name;
    }

    @Override
    public int save(int id) {
        System.out.println("id = " + id);
        return id;
    }
}
