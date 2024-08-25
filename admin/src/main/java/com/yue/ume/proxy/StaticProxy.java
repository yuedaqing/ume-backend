package com.yue.ume.proxy;

/**
 * @author yueyue
 */
public class StaticProxy implements PayService{
    private PayService payService;
    public void proxy(PayService payService){
        this.payService = payService;
    }

    @Override
    public String callBack(String name) {
        System.out.println("\"静态代理\" = " + "静态代理");
        System.out.println("name = " + name);
        return name;
    }

    @Override
    public int save(int id) {
        System.out.println("\"静态代理\" = " + "静态代理");
        System.out.println("id = " + id);
        return id;
    }
}
