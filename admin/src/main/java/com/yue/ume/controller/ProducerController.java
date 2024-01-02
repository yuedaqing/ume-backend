package com.yue.ume.controller;

import com.yue.ume.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.util.ByteUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static javax.xml.transform.OutputKeys.ENCODING;

/**
 * MQ Controller测试
 * @author YueYue
 */
@RestController
@RequestMapping("/mq")
@Slf4j
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{sendMsg}")
    public void sendMsg(@PathVariable String sendMsg){
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY,sendMsg,correlationData);
        log.info("成功发送消息内容为：{}",sendMsg);

//        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME+"123",ConfirmConfig.CONFIRM_ROUTING_KEY,sendMsg,correlationData);
//        log.info("失败交换机——发送消息内容为：{}",sendMsg);

        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY+"123",sendMsg,correlationData);
        log.info("失败队列——发送消息内容为：{}",sendMsg);
    }

    /**
     *  @Description:加密
     */
    public static String encryptEcb(String hexKey, String paramStr, String charset) throws Exception {
        String cipherText = "";
        if (null != paramStr && !"".equals(paramStr)) {
            byte[] keyData = ByteUtils.fromHexString(hexKey);
            charset = charset.trim();
            if (charset.length() <= 0) {
                charset = ENCODING;
            }
            byte[] srcData = paramStr.getBytes(charset);
            byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
            cipherText = ByteUtils.toHexString(cipherArray);
        }
        return cipherText;
    }


}
