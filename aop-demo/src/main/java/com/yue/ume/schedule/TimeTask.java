package com.yue.ume.schedule;

import org.springframework.stereotype.Component;

/**
 * @author YueYue
 */
@Component
public class TimeTask {
//    @Scheduled(fixedRate = 2000)
    public void fun(){
        System.out.println("\"定时任务\" = " + "定时任务");
    }
}
