package com.yue.ume.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.ume.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RedisStringTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Redis_String 的使用
     * @throws JsonProcessingException
     */
    @Test
    void test() throws JsonProcessingException {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserName("杨幂");
        user.setUserAccount("1878888888");
        users.add(user);
        //手动序列化
        String json = mapper.writeValueAsString(users);
        //写入数据
        stringRedisTemplate.opsForValue().set("yue:user:2",json);
        //获取数据
        String jsonString = stringRedisTemplate.opsForValue().get("yue:user:2");
        //手动反序列化
        ArrayList<User> arrayList = mapper.readValue(jsonString, ArrayList.class);
        System.out.println("arrayList = " + arrayList);
    }
    /**
     * Redis_Hash 使用
     */
    @Test
    void testHash(){
        stringRedisTemplate.opsForHash().put("yue:user:hash","name","张一山");
        stringRedisTemplate.opsForHash().put("yue:user:hash","age","25");
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("yue:user:hash");
        System.out.println("entries = " + entries);
    }
}
