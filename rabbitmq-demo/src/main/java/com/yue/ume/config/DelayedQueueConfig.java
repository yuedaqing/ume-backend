package com.yue.ume.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yueyue
 * 延迟队列配置
 */
@Configuration
public class DelayedQueueConfig {
    /**
     * 交换机名称
     */
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    /**
     * 队列名称
     */
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    /**
     * routingKey 路由key名称
     */
    public static final String DELAYED_ROUTING_KEY = "delayed.routingKey";

    /**
     * 延迟交换机
     * @return org.springframework.amqp.core.CustomExchange
     */
    @Bean
    public CustomExchange delayedExchange(){
        Map<String, Object> arguments = new HashMap<String, Object>(8);
        //延迟类型  直接类型
        arguments.put("x-delayed-type","direct");
        /**
         * 1. 交换机名称
         * 2. 交换机的类型 延迟交换机
         * 3. 是否需要持久化 true 持久化 false 不持久化
         * 4. 是否需要自动删除
         */
        return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",true,false,arguments);
    }

    /**
     * 声明队列
     *
     * @return org.springframework.amqp.core.Queue
     */
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    /**
     * 绑定队列与交换机
     */
    @Bean
    public Binding delayedQueueBinding(@Qualifier("delayedQueue") Queue delayedQueue,
                                       @Qualifier("delayedExchange")CustomExchange customExchange ){
        return BindingBuilder.bind(delayedQueue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
