package com.yue.ume.test;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class JsonTest {
    public static void main(String[] args) {
//        fun("{}");
        funB();
    }

    public static void fun(String json) {
        JSONObject jsonObject = JSONUtil.parseObj(json);
        jsonObject.set("arr", "[{'name':'小三'}]");
        Object arr = jsonObject.get("arr");
        System.out.println(String.valueOf(arr));

    }

    public static void funB() {
        Map<String, String> map = new HashMap<String, String>();
        System.out.println(map.get("1"));
    }
}
