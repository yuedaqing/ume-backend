package com.yue.ume.job.once;

import com.yue.ume.delayedQueue.RedisDelayedQueue;
import com.yue.ume.delayedQueue.TaskBodyDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class TestDelayQueue implements CommandLineRunner {

    @Resource
    private RedisDelayedQueue redisDelayedQueue;
    @Override
    public void run(String... args) throws Exception {
        TaskBodyDTO taskBody = new TaskBodyDTO();
        taskBody.setBody("测试DTO实体类的BODY,3秒之后执行");
        taskBody.setName("测试DTO实体类的姓名,3秒之后执行");
        redisDelayedQueue.addQueue(taskBody,3, TimeUnit.SECONDS, "test");
    }
}
