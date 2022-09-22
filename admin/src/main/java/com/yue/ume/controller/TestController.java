package com.yue.ume.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "测试接口")
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "分页查询XXX信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页大小", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", dataTypeClass = Integer.class)
    })
    @GetMapping(value = "/selectPageList")
    public Map<String, Object> selectPageList(@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage) {
        Map<String, Object> map = new HashMap<>();
        map.put("name","王某某");
        map.put("age",12);
        return map;
    }
}