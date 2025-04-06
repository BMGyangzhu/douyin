package org.example.douyin.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author bgmyangzhu
 * @date 2025/4/4 21:24
 */
@Data
public class FindPasswordRequest {
    @Email
    @NotBlank(message = "邮箱不可为空")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱验证码不可为空")
    private String emailCode;
    
}
