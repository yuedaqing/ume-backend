package com.yue.ume.redis;

import com.yue.ume.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class lettuceDemo {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    void lettuceTest(){
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserName("赵丽颖");
        user.setAccount("1888888888");
        users.add(user);
        //写入一条String数据
        redisTemplate.opsForValue().set("yue:user",users);
        List<User> userList = (List<User>) redisTemplate.opsForValue().get("yue:user");
        System.out.println("nickName = " + userList);
    }
}
