package com.yue.ume.delayedQueue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisDelayedQueueTest {
    @Resource
    private RedisDelayedQueue redisDelayedQueue;

    @Test
    public void fun(){
        TaskBodyDTO taskBody = new TaskBodyDTO();
        taskBody.setBody("测试DTO实体类的BODY,3秒之后执行");
        taskBody.setName("测试DTO实体类的姓名,3秒之后执行");
        redisDelayedQueue.addQueue(taskBody,3, TimeUnit.SECONDS, "test");
    }

}