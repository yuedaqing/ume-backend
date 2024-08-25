package com.yue.ume.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 通用删除请求
 * @author yueyue
 * @date 2023/4/27 16:59
 * @version 1.0
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}