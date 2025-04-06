package org.example.douyin.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author bgmyangzhu
 * @date 2025/4/4 20:19
 */
@Data
public class EmailCodeRequest {
    
    @Email
    @NotBlank(message = "邮箱不可为空")
    private String email;
    
    @NotBlank(message = "邮箱验证码不能为空")
    private String emailCode;
}
