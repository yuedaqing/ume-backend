package com.yue.ume.proxy;

/**
 * @author YueYue
 */
public class ProxyTest {
    public static void main(String[] args) {
//        PayService payService = new PayServiceImpl();
//        payService.callBack("nihao");
//        payService.save(1);
//        StaticProxy proxy = new StaticProxy();
//        proxy.callBack("hahahha");
//        proxy.save(123);

        //JDK动态代理
//        JDKProxy jdkProxy = new JDKProxy();
//        //获取代理类对象
//        PayService payService = (PayService) jdkProxy.newProxyInstance(new PayServiceImpl());
//        payService.callBack("aaaaaaaaaaaaaaaaa");
        //Cglib动态代理
        CglibProxy cglibProxy = new CglibProxy();
        //获取代理类对象
        PayService payService = (PayService) cglibProxy.newProxyInstance(new PayServiceImpl());
        payService.callBack("aaaaaaaaaaaaaaaaa");
    }
}
