package com.yue.ume.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yueyue
 * 配置类 发布确认（高级——尚硅谷）
 */
@Configuration
public class ConfirmConfig {
    /**
     * 交换机名称
     */
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    /**
     * 队列名称
     */
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    /**
     * 路由KEY
     */
    public static final String CONFIRM_ROUTING_KEY = "confirm_key";

    /**
     * 备份交换机
     */
    public static final String BACKUPS_EXCHANGE_NAME = "backups_exchange";

    /**
     * 备份队列
     */
    public static final String BACKUPS_QUEUE_NAME = "backups_queue";

    /**
     * 告警队列
     */
    public static final String WARNING_QUEUE_NAME = "warning_queue";


    /**
     * 声明交换机 直接类型
     * @return org.springframework.amqp.core.DirectExchange
     */
    @Bean
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME)
                .withArgument("alternate-exchange",BACKUPS_EXCHANGE_NAME).build();
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    /**
     * 绑定交换机与队列
     */
    @Bean
    public Binding confirmBinding(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                                  @Qualifier("confirmQueue") Queue confirmQueue){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    /**
     * 备份交换机 扇出类型
     * @return
     */
    @Bean
    public FanoutExchange backupsExchange(){
        return new FanoutExchange(BACKUPS_EXCHANGE_NAME);
    }

    /**
     * 备份队列
     * @return
     */
    @Bean
    public Queue backupsQueue(){
        return QueueBuilder.durable(BACKUPS_QUEUE_NAME).build();
    }

    /**
     * 报警队列
     * @return
     */
    @Bean Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    /**
     * 绑定
     * @param backupsExchange
     * @param backupsQueue
     * @return
     */
    @Bean
    public Binding backupsBinding(@Qualifier("backupsExchange") FanoutExchange backupsExchange,
                                @Qualifier("backupsQueue") Queue backupsQueue){
        return BindingBuilder.bind(backupsQueue).to(backupsExchange);
    }

    /**
     * 绑定
     * @param backupsExchange
     * @param warningQueue
     * @return
     */
    @Bean
    public Binding warningBinding(@Qualifier("backupsExchange") FanoutExchange backupsExchange,
                                @Qualifier("warningQueue") Queue warningQueue){
        return BindingBuilder.bind(warningQueue).to(backupsExchange);
    }

}
