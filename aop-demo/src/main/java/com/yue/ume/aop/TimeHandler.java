package com.yue.ume.aop;

import cn.hutool.core.date.DateUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author yueyue
 * 横切关注点
 */
@Aspect
@Component
public class TimeHandler {
    //注解+AOP
//    @Pointcut("@annotation(com.yue.ume.annotation.VideoAnnotation)")
    //切入点表达式
    @Pointcut("execution(* com.yue.ume.service.imp.VideoServiceImpl.*(..))")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void printBefore(){
        System.out.println("执行前日志 = " + DateUtil.now());
    }
    @After("pointCut()")
    public void printAfter(){
        System.out.println("执行后日志 = " + DateUtil.now());
    }

    @Around("pointCut()")
    public void printAround(ProceedingJoinPoint pjp){
        //目标方法签名
        Signature signature = pjp.getSignature();
        System.out.println("signature = " + signature);
        //目标方法参数
        Object[] args = pjp.getArgs();
        System.out.println("args = " + args[0]);
        //目标方法名称
        Object target = pjp.getTarget();
        System.out.println("target = " + target);
        if (signature instanceof MethodSignature){
            System.out.println("调用者是一个方法");
        }
        long begin = System.currentTimeMillis();
        try {
            pjp.proceed();
        } catch (Throwable e) {

        }
        long end = System.currentTimeMillis();
        System.out.println("环绕通知 = " + DateUtil.now());
        System.out.println("方法执行时间 = " + (end - begin));
    }
    @AfterReturning("pointCut()")
    public void printReturning(){
        System.out.println("返回结果通知 = " + DateUtil.now());
    }
    @AfterThrowing("pointCut()")
    public void printThrowing(){
        System.out.println("抛出异常通知 = " + DateUtil.now());
    }
}
