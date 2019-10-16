package cn.itsource.gofishing.common.client.impl;

import cn.itsource.gofishing.common.client.RedisClient;

public class RedisClientImpl implements RedisClient {

    @Override
    public String get(String key) {
        return "{\"message\": \"服务器繁忙，请稍后再试！\"}";

    }

    @Override
    public void set(String key, String value) {

    }
}
