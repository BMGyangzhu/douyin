package org.example.douyin.service;

import org.example.douyin.enums.CaptchaStatus;

import java.awt.image.BufferedImage;

/**
 * <p>
 * 系统验证码 服务类
 * </p>
 *
 * @author xhy
 * @since 2023-10-25
 */
public interface CaptchaService{


    BufferedImage getImageCaptcha(String uuId);

    CaptchaStatus validateImageCaptcha(String code, String uuId);

    CaptchaStatus validateEmailCode(String email, String code);
}
