package com.yue.ume.controller;

import com.yue.ume.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yueyue
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/aop")
    public String fun(){
         videoService.beforeAdvice("前置");
         videoService.afterAdvice("后置");
         videoService.aroundAdvice("环绕");
         videoService.returnAdvice("返回结果后");
         videoService.exceptionAdvice("抛出异常后");
         return "执行成功";
    }
}
