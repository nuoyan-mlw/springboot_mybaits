package com.crazycode.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class JedisUtils {
    private static JedisPool jedisPool = null;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        Properties properties = new Properties();
        InputStream is = JedisUtils.class.getClassLoader().getResourceAsStream("redisConfig.properties");
        try {
            properties.load(is);
            jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("jedis.maxTotal")));
            jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("jedis.maxIdle")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jedisPool = new JedisPool(properties.getProperty("jedis.host"),Integer.parseInt(properties.getProperty("jedis.port")));
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void close(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }


}
