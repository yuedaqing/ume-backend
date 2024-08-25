package com.yue.ume.redis.jedis;

import com.google.gson.Gson;
import com.yue.ume.model.entity.User;
import com.yue.ume.redis.config.JedisConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JedisDemo {
    private Jedis jedis;

    @BeforeEach
    public void after() {
//单连接jedis
//        jedis = new Jedis("47.94.206.225",6379);
//        jedis.auth("123456");
//        jedis.select(0);
        //连接池连接jedis
        jedis = JedisConfig.getJedis();
    }

    @Test
    public void jedisTest() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserName("yueyue");
        user.setUserAccount("admin");
        users.add(user);
        Gson gson = new Gson();
        String userJson = gson.toJson(users);
        jedis.lpush("user", userJson);
        List<String> user1 = jedis.lrange("user", 0, -1);
        System.out.println("user1 = " + user1);
    }

    @AfterEach
    public void close() {
        //当有连接池时，会把连接归还连接池，不会直接关闭
        if (jedis == null) {
            jedis.close();
        }
    }
}
