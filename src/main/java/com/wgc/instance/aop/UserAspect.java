package com.wgc.instance.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Aspect
@Component
public class UserAspect {

    /*使用环绕的方式*/
    @Around("@annotation(com.wgc.instance.aop.UserAOP)")
    public  Object setRedis(ProceedingJoinPoint point) throws Throwable {
        Jedis jedis = new Jedis();
        String name = point.getSignature().getName();
        ObjectMapper mapper = new ObjectMapper();
        /*listClass相当与List.Class
        *通过反射的获取到List.Class
        * */
        //point.getTarget().getClass().getMethod(name).
        Class<?> listClass = Class.forName(point.getTarget().getClass().getMethod(name).getReturnType().getName());

        if (jedis.exists(name)) {
            return mapper.readValue(jedis.get(name), listClass);
        }

        Object proceed =  point.proceed();
        jedis.set(name, mapper.writeValueAsString(proceed));
        return proceed;
    }
}
