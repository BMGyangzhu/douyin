package org.example.douyin.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 13:48
 */
public class LocalCache {
    
    private static Map<String, Object> cache = new ConcurrentHashMap<>();
    
    public static void put(String key, Object val) { cache.put(key,val);}
    
    public static Boolean containsKey(String key) {
        if (key == null) return false;
        return cache.containsKey(key);
    }
    
    public static void remove(String key) {
        cache.remove(key);
    }
}
