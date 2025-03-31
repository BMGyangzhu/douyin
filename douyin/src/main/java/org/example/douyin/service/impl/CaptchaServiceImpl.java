package org.example.douyin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.example.douyin.entity.dto.CaptchaDTO;
import org.example.douyin.enums.CaptchaStatus;
import org.example.douyin.exception.BaseException;
import org.example.douyin.service.CaptchaService;
import org.example.douyin.service.EmailService;
import org.example.douyin.util.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * @author bgmyangzhu
 * @date 2025/3/30 21:43
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService  {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private Producer producer;
    
    @Autowired
    private EmailService emailService;

    /**
     * 生成验证码和与之对应的验证码图片
     * @param uuId
     * @return BufferedImage
     */
    @Override
    public BufferedImage getImageCaptcha(String uuId) {
        if (uuId == null || uuId.isEmpty()) {
            throw new BaseException("UUID不能为空");
        }
        String code = this.producer.createText();
        // 存放在redis中
        String key = RedisConstants.CAPTCHA_KEY_PREFIX + uuId;
        long ttl = RedisConstants.CAPTCHA_KEY_TTL;
        stringRedisTemplate.opsForValue().set(key, code, ttl, TimeUnit.MINUTES);
        log.info("生成图形验证码：{}", code);
        return producer.createImage(code);
    }

    /**
     * 校验用户根据图形验证码输入的验证码 
     * @param captchaDTO
     * @return boolean 
     */
    @Override
    public CaptchaStatus validateImageCaptcha(CaptchaDTO captchaDTO) {
        // 1.参数校验（空值检查）
        String code = captchaDTO.getCode();
        String uuId = captchaDTO.getUuid();
        if (StrUtil.isEmpty(code) || StrUtil.isEmpty(uuId)) {
            return CaptchaStatus.ISNULL;
        }
        // 2.从redis中获取验证码
        String redisKey = RedisConstants.CAPTCHA_KEY_PREFIX + uuId;
        String codeFromRedis = stringRedisTemplate.opsForValue().get(redisKey);
        
        // 3.验证逻辑
        if (codeFromRedis == null) {
            return CaptchaStatus.EXPIRED; // key不存在或已过期
        }
        if (!codeFromRedis.equalsIgnoreCase(code)) {
            return CaptchaStatus.MISMATCH; // 验证码不匹配
        }
       
        return CaptchaStatus.VALID; // 验证通过
    }

    /**
     * 验证用户输入的邮箱和验证码 （接口防刷）
     * @param email
     * @param emailCode
     */
    @Override
    public CaptchaStatus validateEmailCode(String email, String emailCode) {
        // 1.获取redis中的邮箱验证码
        if (StrUtil.isEmpty(email) || StrUtil.isEmpty(emailCode)) {
            return CaptchaStatus.ISNULL;
        }
        // 如果用户输入的email是错误的，返回错误信息
        String redisKey = RedisConstants.EMAIL_CODE + email;
        String emailCodeFormRedis = stringRedisTemplate.opsForValue().get(redisKey);
        if (StrUtil.isEmpty(emailCodeFormRedis)) {
            return CaptchaStatus.EXPIRED;
        }
        // 2. 验证用户输入的邮箱验证码
        if (!emailCode.equalsIgnoreCase(emailCodeFormRedis)) {
           return CaptchaStatus.MISMATCH;
        }
        // 验证通过后立即删除Redis中的验证码（防止重复使用）
        stringRedisTemplate.delete(redisKey);
        return CaptchaStatus.VALID; // 验证通过
    }
    
}
