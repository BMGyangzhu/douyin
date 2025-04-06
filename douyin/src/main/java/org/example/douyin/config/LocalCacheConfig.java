package org.example.douyin.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 13:45
 */
@Configuration
public class LocalCacheConfig {
    
    @Bean
    public Cache<String, Boolean> caffeineCache() {
        return Caffeine.newBuilder()
                 // 设置最后一次写入或访问后的固定过期时间      
                .expireAfterWrite(8, TimeUnit.SECONDS)
                 // 初始化缓存空间大小      
                .initialCapacity(100)
                 // 缓存最大数目      
                .maximumSize(100000)
                .build();
    }
}
