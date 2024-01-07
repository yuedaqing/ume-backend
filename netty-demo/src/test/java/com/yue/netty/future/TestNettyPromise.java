package com.yue.netty.future;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        DefaultPromise<Integer> promise = new DefaultPromise<Integer>(group.next());
        new Thread(() -> {
            log.info("开始计算");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            promise.setSuccess(50);
        }).start();
        try {
            Integer integer = promise.get();
            log.info("执行结果={}", integer);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
