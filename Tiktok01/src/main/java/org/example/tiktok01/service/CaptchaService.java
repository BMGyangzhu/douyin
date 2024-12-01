package org.example.tiktok01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.example.tiktok01.entity.Captcha;

import java.awt.image.BufferedImage;

/**
 * @author bgmyangzhu
 * @date 2024/11/30 13:56
 */
public interface CaptchaService extends IService<Captcha> {
    
    BufferedImage getCaptcha(String uuId);
    
    boolean validate(Captcha captcha) throws Exception;
    

    
}
