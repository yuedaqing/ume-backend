package com.yue.ume.delayedQueue;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
public class RedisDelayedQueueInit implements ApplicationContextAware {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取应用上下文并获取相应的接口实现类
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisDelayedQueueListener> map = applicationContext.getBeansOfType(RedisDelayedQueueListener.class);
        for (Map.Entry<String, RedisDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
            String listenerName = taskEventListenerEntry.getValue().getClass().getName();
            startThread(listenerName, taskEventListenerEntry.getValue());
        }
    }

//    public void init(String queue, String e, long delay, TimeUnit unit) {
//        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(queue);
//        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
//        delayedQueue.offerAsync(e, delay, unit);
//    }

    public <T> void startThread(String queueName, RedisDelayedQueueListener redisDelayedQueueListener) {

        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        Thread thread = new Thread(() -> {
            log.info("启动监听队列线程" + queueName);
            while (true) {
                try {
                    T t = blockingFairQueue.take();
                    log.info("监听队列线程{},获取到值:{}", queueName, JSONUtil.toJsonStr(t));
                    new Thread(() -> {
                        redisDelayedQueueListener.invoke(t);
                    }).start();
                } catch (Exception e) {
                    log.info("监听队列线程错误,", e);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });
        thread.setName(queueName);
        thread.start();
    }
}
