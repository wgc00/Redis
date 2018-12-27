package com.wgc.service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        JedisService bean = context.getBean(JedisService.class);
        bean.testRedis();
        bean.testRedisTemplate();
    }
}
