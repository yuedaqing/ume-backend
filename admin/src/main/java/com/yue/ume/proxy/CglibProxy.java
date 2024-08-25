package com.yue.ume.proxy;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author yueyue
 */
public class CglibProxy implements MethodInterceptor {

    private Object targetObject;

    /**
     * 绑定关系
     * @param targetObject
     * @return
     */
    public Object newProxyInstance(Object targetObject){
        this.targetObject = targetObject;
        Enhancer enhancer = new Enhancer();
        //设置代理类的父类
        enhancer.setSuperclass(this.targetObject.getClass());
        //设置回调函数
        enhancer.setCallback(this);
        //创建子类（代理对象）
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        try {
            System.out.println("result = " + "调用前");
            result = methodProxy.invokeSuper(o,objects);
            System.out.println("result = " + "调用后");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;    }
}
