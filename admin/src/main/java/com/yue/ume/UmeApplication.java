package com.yue.ume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author YueYue
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class UmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmeApplication.class, args);
        System.out.println(" ume项目启动成功 ");
    }

}