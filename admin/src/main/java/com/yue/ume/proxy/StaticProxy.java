package com.yue.ume.proxy;

/**
 * 静态代理
 *
 * @author yueyue
 */
public class StaticProxy implements PayService {
    private PayService payService;

    public void proxy(PayService payService) {
        this.payService = payService;
    }

    @Override
    public String callBack(String name) {
        System.out.println("静态代理执行前");
        System.out.println("name = " + name);
        System.out.println("静态代理执行后");
        return name;
    }

    @Override
    public int save(int id) {
        System.out.println("静态代理执行前");
        System.out.println("id = " + id);
        System.out.println("静态代理执行后");
        return id;
    }
}
