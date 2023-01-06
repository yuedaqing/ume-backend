package com.yue.ume.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author YueYue
 */
@Data
public class RedisData {

    private LocalDateTime expireTime;
    private Object data;

}
