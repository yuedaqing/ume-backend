//package com.yue.ume.hotkey;
//
//import com.jd.platform.hotkey.client.ClientStarter;
//import org.redisson.api.RBlockingDeque;
//import org.redisson.api.RDelayedQueue;
//import org.redisson.api.RedissonClient;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.print.attribute.standard.Destination;
//
//@Configuration
//public class InitHotkey {
//
//    @PostConstruct
//    public void initHotkey() {
//
//        ClientStarter.Builder builder = new ClientStarter.Builder();
//        ClientStarter starter = builder.setAppName("test_hot_key").setEtcdServer("http://127.0.0.1:2379").build();
//        starter.startPipeline();
//    }
//}
