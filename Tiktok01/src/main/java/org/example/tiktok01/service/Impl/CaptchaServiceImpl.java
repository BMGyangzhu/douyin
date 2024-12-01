package org.example.tiktok01.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import org.example.tiktok01.constant.RedisConstant;
import org.example.tiktok01.entity.Captcha;
import org.example.tiktok01.exception.BaseException;
import org.example.tiktok01.mapper.CaptchaMapper;
import org.example.tiktok01.service.CaptchaService;
import org.example.tiktok01.service.EmailService;
import org.example.tiktok01.util.DateUtil;
import org.example.tiktok01.util.RedisCacheUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @author bgmyangzhu
 * @date 2024/11/30 13:56
 */
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha> implements CaptchaService {
    
    @Autowired
    private Producer producer;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public BufferedImage getCaptcha(String uuId) {
        String code = this.producer.createText();
        Captcha captcha = new Captcha();
        captcha.setUuid(uuId);
        captcha.setCode(code);
        captcha.setExpireTime(DateUtil.addDateMinutes(new Date(),5));
        this.save(captcha);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(Captcha captcha) throws Exception {
        String email = captcha.getEmail();
        final String code1 = captcha.getCode();
        captcha = this.getOne(new LambdaQueryWrapper<Captcha>().eq(Captcha::getUuid, captcha.getUuid()));
        if(captcha == null) throw new BaseException("uuId为空");
        
        this.remove(new LambdaQueryWrapper<Captcha>().eq(Captcha::getUuid, captcha.getUuid()));
        
        if(!captcha.getCode().equals(code1)){
            throw new BaseException("code错误");
        }
        if(captcha.getExpireTime().getTime() <= System.currentTimeMillis()){
            throw new BaseException("uuid过期");
        }
        
        String code = getSixCode();
        redisCacheUtil.set(RedisConstant.EMAIL_CODE +email,code,RedisConstant.EMAIL_CODE_TIME);
        emailService.send(email,"注册验证码:" + code + ",验证码5分钟内有效");
        return true;
    }
    
    public static String getSixCode(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 6; i++){
            int code = (int) (Math.random()*10);
            builder.append(code);
        }
        return builder.toString();
    }
}
