package com.yue.ume.delayedQueue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 监听器
 */
@Component
@Slf4j
public class TestListener implements RedisDelayedQueueListener<TaskBodyDTO> {


    @Override
    public void invoke(TaskBodyDTO taskBodyDTO) {
        //这里调用你延迟之后的代码
        log.info("执行...." + taskBodyDTO.getBody() + "===" + taskBodyDTO.getName());
    }
}