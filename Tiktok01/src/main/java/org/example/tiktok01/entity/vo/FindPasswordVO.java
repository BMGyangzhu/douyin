package org.example.tiktok01.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author bgmyangzhu
 * @date 2024/12/2 15:04
 */
@Data
public class FindPasswordVO {
    
    @Email(message = "邮箱格式不正确")
    String email;
    
    @NotBlank(message = "code不能为空")
    Integer code;
    
    @NotBlank(message = "新密码不能为空")
    String newPassword;
    
}
