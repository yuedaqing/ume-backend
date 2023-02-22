package com.yue.ume.consumer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yue.ume.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author YueYue
 * 基于插件的延迟消息
 */
@Slf4j
@Component
public class DelayedQueueConsumer {
    /**
     * 监听消息
     */
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiverDelayedQueue(Message message) {
        String msg = StrUtil.utf8Str(message.getBody());
        log.info("当前时间：{},收到延迟队列的消息为：{}", DateUtil.now(), msg);
    }
}
