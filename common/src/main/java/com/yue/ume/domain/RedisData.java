package com.yue.ume.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yueyue
 */
@Data
public class RedisData {

    private LocalDateTime expireTime;
    private Object data;

}
