package com.wgc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class JedisService {

    @Autowired
    private RedisConnectionFactory factory;

    @Autowired
    private RedisOperations operations;

    public void testRedis() {
        RedisConnection connection = factory.getConnection();
        byte[] bytes = connection.get("getting".getBytes());
        try {
            System.out.println(new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("error");
        }
    }

    public void testRedisTemplate() {
        Object getting = operations.opsForValue().get("getting");
        System.out.println(getting);
    }
}
