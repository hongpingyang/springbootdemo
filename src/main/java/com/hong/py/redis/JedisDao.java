package com.hong.py.redis;

public interface JedisDao {

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    Boolean exists(String key);


    /**
     * 删除
     * @param key
     * @return
     */
    Boolean del(String key);

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, String value);


    /**
     * 取值
     * @param key
     * @return
     */
    String get(String key);


    /**
     * 设置过期时间
     * @param key
     * @param seconds
     * @return
     */
    Boolean expire(String key, int seconds);

}
