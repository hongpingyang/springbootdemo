package com.hong.py.redis.impl;

import com.hong.py.redis.JedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JedisDaoImpl1 implements JedisDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public boolean set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return true;
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean expire(String key, int seconds) {
        return stringRedisTemplate.expire(key,seconds, TimeUnit.SECONDS);
    }
}
