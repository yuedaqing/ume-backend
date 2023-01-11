package com.yue.ume.controller;

import cn.hutool.core.date.DateUtil;
import com.yue.ume.config.DelayedQueueConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "测试接口")
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "分页查询XXX信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", dataTypeClass = Integer.class)
    })
    @GetMapping(value = "/selectPageList")
    public Map<String, Object> selectPageList(@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("name","王某某");
        map.put("age",12);
        return map;
    }

    @GetMapping("/sendMsg/{message}/{delayTime}")
    public void senMsg(@PathVariable String message,@PathVariable Integer delayTime){
        log.info("当前时间：{}"+"发送一条时长{}毫秒信息给延迟队列QC：{}",DateUtil.now(),delayTime,message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,DelayedQueueConfig.DELAYED_ROUTING_KEY,message,msg->{
            //发送消息的时候 延迟时长 单位ms
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }
}