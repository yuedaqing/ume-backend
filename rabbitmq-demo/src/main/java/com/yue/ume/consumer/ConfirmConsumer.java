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
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiverConfirmMessage(Message message) {
        log.info("消费的消息为：{}", StrUtil.utf8Str(message.getBody()));
    }
}
