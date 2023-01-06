package com.yue.ume.redis;

import com.yue.ume.redisUtils.RedisIdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class RedisIdTest {
    @Autowired
    private RedisIdWorker redisIdWorker;
    //创建500个线程
    private ExecutorService es = Executors.newFixedThreadPool(500);

    @Test
    void test02(){
        CountDownLatch countDownLatch = new CountDownLatch(300);
        Runnable task = ()->{
            for (int i = 0; i < 100; i++) {
                long id = redisIdWorker.nextId("order");
                System.out.println("id = " + id);
            }
            countDownLatch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }
        long end = System.currentTimeMillis();
        System.out.println("end = " + (end-begin));
    }
}
