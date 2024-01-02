package com.yue.ume.redis.config;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Yue
 * jedis连接池配置
 */
public class JedisConfig {

    public static final JedisPool JEDIS_POOL;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接
        jedisPoolConfig.setMaxTotal(8);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(8);
        //最小空闲连接
        jedisPoolConfig.setMinIdle(0);
        //最长等待时间
        jedisPoolConfig.setMaxWaitMillis(500);
        JEDIS_POOL = new JedisPool(jedisPoolConfig,"127.0.0.1",6379,500,"123456");
    }
    public static Jedis getJedis(){
        return JEDIS_POOL.getResource();
    }
}