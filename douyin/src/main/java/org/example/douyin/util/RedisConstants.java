package org.example.douyin.util;

/**
 * @author bgmyangzhu
 * @date 2025/3/31 12:49
 */
public class RedisConstants {
    
    public static final String CAPTCHA_KEY_PREFIX = "captcha:";
    public static final Long CAPTCHA_KEY_TTL = 5L;
    public static final String EMAIL_CODE = "email:";
    public static final Long EMAIL_CODE_TTL = 5L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 36000L;

    public static final String VIDEO_LIMIT = "video:limit";
}