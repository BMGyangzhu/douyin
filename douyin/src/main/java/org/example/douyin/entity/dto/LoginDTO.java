package org.example.douyin.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;

/**
 * @author bgmyangzhu
 * @date 2025/3/31 17:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginDTO {
    
    @Email
    private String email;

    CaptchaDTO captcha;
    
}
