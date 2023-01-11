package com.yue.ume.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * @author YueYue
 */
@Component
@Slf4j
public class MyCallBack  implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     *
     * 交换机回调
     * @param correlationData 保存回调消息的ID及相关信息
     * @param ack true 收到消息 false未收到
     * @param cause 原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack){
            log.info("交换机已经收到ID为：{}",id);
        }else{
            log.info("交换机未收到的ID为：{}.原因是：{}",id,cause);
        }
    }

    /**
     * 消息传递失败返回给生产者
     * @param returnedMessage 失败消息体
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息：{}，被交换机:{}退回，退回原因：{}，路由Key：{}",
                new String(returnedMessage.getMessage().getBody(), StandardCharsets.UTF_8),returnedMessage.getExchange(),
                returnedMessage.getReplyText(),returnedMessage.getRoutingKey());
    }
}
