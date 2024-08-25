package com.yue.ume.hotkey;

import com.jd.platform.hotkey.client.ClientStarter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class InitHotkey {

    @PostConstruct

    public void initHotkey() {

        ClientStarter.Builder builder = new ClientStarter.Builder();
        ClientStarter starter = builder.setAppName("test_hot_key").setEtcdServer("http://127.0.0.1:2379").build();
        starter.startPipeline();
    }
}
