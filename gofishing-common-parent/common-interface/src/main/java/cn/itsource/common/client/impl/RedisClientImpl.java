package cn.itsource.common.client.impl;

import cn.itsource.common.client.RedisClient;

public class RedisClientImpl implements RedisClient {
    @Override
    public String get(String key) {
        return "{\"message\":\"服务异常！\"}";
    }

    @Override
    public void set(String key, String value) {

    }
}
