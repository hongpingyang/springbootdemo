package com.hong.py.redis.impl;

import com.hong.py.redis.JedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JedisDaoImpl implements JedisDao {

     @Autowired
     private RedisTemplate<String,Object> jedisClients;
    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    @Override
    public Boolean exists(String key) {
        return jedisClients.hasKey(key);
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    @Override
    public Boolean del(String key) {
        return jedisClients.delete(key);
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(String key, String value) {
         jedisClients.opsForValue().set(key, value);
         return true;
    }

    /**
     * 取值
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return (String) jedisClients.opsForValue().get(key);
    }

    /**
     * 设置过期时间
     * @param key
     * @param seconds
     * @return
     */
    @Override
    public Boolean expire(String key, int seconds) {
        return jedisClients.expire(key, seconds, TimeUnit.SECONDS);
    }
}
