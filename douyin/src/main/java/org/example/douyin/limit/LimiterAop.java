package org.example.douyin.limit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.douyin.exception.LimiterException;
import org.example.douyin.holder.UserHolder;
import org.example.douyin.util.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 15:21
 */
@Aspect
public class LimiterAop {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Before("@annotation(limiter)")
    public Object restriction(ProceedingJoinPoint joinPoint, Limit limiter) throws Throwable {
        final Long userId = UserHolder.get();
        final int limitCount = limiter.limit();
        final String msg = limiter.msg();
        final long time = limiter.time();
        // 缓存是否存在
        String key = RedisConstants.VIDEO_LIMIT + userId;
        final Object o = stringRedisTemplate.opsForValue().get(key);
        if (ObjectUtils.isEmpty(o)) {
            stringRedisTemplate.opsForValue().set(key,"1",time, TimeUnit.SECONDS);
        } else {
            if (Integer.parseInt(o.toString()) > limitCount) {
                throw new LimiterException(msg);
            }
            stringRedisTemplate.opsForValue().increment(key, 1);
        }
        Object o1 = joinPoint.proceed();
        return o1;
    }
}

