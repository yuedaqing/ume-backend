package com.yue.netty.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * JDK的Future
 * 关联线程池使用
 */
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) {
        //1. 创建固定数量的线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        //2. 提交任务
        Future<Integer> future = fixedThreadPool.submit(() -> {
            Thread.sleep(1000);
            log.info("执行计算");
            return 50;
        });
        //3. main线程通过future来获取结果
        try {
            log.info("等待结果");
            Integer integer = future.get();
            log.info("结果={}", integer.toString());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        fixedThreadPool.shutdown();
    }
}
