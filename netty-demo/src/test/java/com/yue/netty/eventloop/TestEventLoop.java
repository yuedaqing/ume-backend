package com.yue.netty.eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        //1. 创建事件循环组
        //NioEventLoopGroup 可以处理：IO事件，普通人物、定时任务
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        //DefaultEventLoopGroup 可以处理：普通任务、定时任务
//        DefaultEventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup();
        //2. 获取下一个事件的循环对象
        System.out.println("group = " + group.next());
        System.out.println("group = " + group.next());
        System.out.println("group = " + group.next());
        System.out.println("group = " + group.next());
        //3. 执行普通任务
        group.next().submit(() -> {
            log.info("ok");
        });
        //4. 执行定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.info("定时任务");
        }, 1, 5, TimeUnit.SECONDS);
//        group.next().
//        System.out.println("NettyRuntime.availableProcessors() * 2 = " + NettyRuntime.availableProcessors() * 2);

    }


}
