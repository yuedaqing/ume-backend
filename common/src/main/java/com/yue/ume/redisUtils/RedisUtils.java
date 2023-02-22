package com.yue.ume.redisUtils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.yue.ume.domain.RedisData;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * redis工具类
 * @author YueYue
 */
public class RedisUtils {
    private StringRedisTemplate stringRedisTemplate;

    public RedisUtils(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void set(String key, Object value,Long time, TimeUnit unit){
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value),time,unit);
    }

    /**
     *
     * @param key redis_key
     * @param value 缓存的值
     * @param time 缓存时间
     * @param unit 缓存时间单位
     */
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        //设置逻辑过期
        RedisData redisData = new RedisData();
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        redisData.setData(value);
        //写入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData), time, unit);
    }

    /**
     *
     * @param keyPrefix redis_key
     * @param id 查询的主键
     * @param type 返回类型
     * @param dbFallBack sql回调
     * @param time 缓存时间
     * @param unit 缓存时间单位
     * @param <R> 返回类型
     * @param <ID> ID类型
     * @param <T>  无
     * @return
     */
    public <R,ID,T> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID,R> dbFallBack,Long time ,TimeUnit unit){
        String key = keyPrefix+id;
        //从redis中查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        //判断是否存在
        if (StrUtil.isNotBlank(json)){
            //存在，直接返回缓存数据
            return JSONUtil.toBean(json,type);
        }
        //不存在
//        判断命中的值是否为空值
        if (json != null){
            //返回 错误信息
            return null;
        }
        //不存在，根据Id查询数据库
        R r = dbFallBack.apply(id);
        //如果 r = null 返回错误
        if (r == null){
            //将空值写入redis
            stringRedisTemplate.opsForValue().set(key,"",30,TimeUnit.MINUTES);
            return null;
        }
        //写入redis
        this.setWithLogicalExpire(key,r,time,unit);
        return r;
    }
}
