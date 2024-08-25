package com.yue.ume.common;

import com.yue.ume.constant.CommonConstant;
import lombok.Data;

/**
 * @description: 分页请求
 * @author yueyue
 * @date 2023/4/27 17:00
 * @version 1.0
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
