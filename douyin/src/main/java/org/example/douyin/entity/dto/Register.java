package org.example.douyin.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author bgmyangzhu
 * @date 2025/4/1 13:56
 */
@Data
public class Register {
    
    @Email
    @NotBlank(message = "邮箱不可为空")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱验证码不可为空")
    private String emailCode;
    @NotBlank(message = "用户名不可为空")
    private String nickName;
   
}
