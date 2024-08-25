package com.yue.ume.consumer;

import cn.hutool.core.util.StrUtil;
import com.yue.ume.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author yueyue
 */
@Component
@Slf4j
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiverConsumer(Message message) {
        log.info("*报警*：发现不可路由消息：{}", StrUtil.utf8Str(message.getBody()));
    }
}
