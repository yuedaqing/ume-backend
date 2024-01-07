package com.yue.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * netty Future 对JDK Future的增强
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(() -> {
            log.info("执行计算");
            Thread.sleep(1000);
            return 70;
        });
        while (true) {
            //netty的方法，不阻塞，未获取到结果，返回null
            Integer now = future.getNow();
            if (now != null) {
                log.info("结果={}", now);
                break;
            } else {
                log.info("未获取到结果");
            }
        }
        future.addListener(future1 -> {
           log.info("执行结果：{}",future1.getNow());
        });
        group.shutdownGracefully();

    }
}
