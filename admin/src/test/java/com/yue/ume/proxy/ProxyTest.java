package com.yue.ume.proxy;

import com.yue.ume.model.entity.User;
import com.yue.ume.service.UserService;
import com.yue.ume.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Callback;

import java.util.List;

/**
 * 代理测试
 *
 * @author yueyue
 */
@SpringBootTest
public class ProxyTest {

    @Test
    public void jdkProxy() {
        //JDK动态代理
        JDKProxy jdkProxy = new JDKProxy();
        //获取代理类对象
        PayService payService = (PayService) jdkProxy.newProxyInstance(new PayServiceImpl());
        payService.callBack("JDK代理测试");
    }

    @Test
    public void cgLibProxy() {
        //Cglib动态代理
        CglibProxy cglibProxy = new CglibProxy();
        //获取代理类对象
        UserService payService = (UserService) cglibProxy.newProxyInstance(new UserServiceImpl());
//        payService.callBack("cgLib代理测试");
        List<User> list = payService.list();
//        System.out.println("list = " + list);
    }

    @Test
    public void staticProxy() {
//        PayService payService = new PayServiceImpl();
//        payService.callBack("hello world!");
//        payService.save(1);
        StaticProxy proxy = new StaticProxy();
        proxy.callBack("静态代理测试！！！");
        proxy.save(1);
    }
}
