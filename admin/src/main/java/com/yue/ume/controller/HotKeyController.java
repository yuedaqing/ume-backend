package com.yue.ume.controller;

import com.jd.platform.hotkey.client.callback.JdHotKeyStore;
import com.yue.ume.model.entity.User;
import com.yue.ume.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yueyue
 * 用户接口
 */
@RequestMapping("/user")
@RestController
public class HotKeyController {
    @Resource
    private UserService userService;

    public static volatile AtomicInteger count = new AtomicInteger(0);

    @GetMapping("/userById/{userId}")
    public User getList(@PathVariable("userId") Long userId) {
        System.out.println("累计请求多少次 count = " + count.incrementAndGet());
        Object userInfo = JdHotKeyStore.getValue("userId__" + userId);
        if (userInfo == null) {
            System.out.println("通过数据库查询：" + count.get());
            User userById = userService.getById(userId);
            JdHotKeyStore.smartSet("userId__" + userId, userById);
            return userById;
        } else {
            System.out.println("热key探测查询：" + count.get());
            return (User) userInfo;
        }
    }
}
