package org.example.douyin.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author bgmyangzhu
 * @date 2025/4/1 22:52
 */
@Data
public class UserDTO {
    @Email
    private String email;
    
    @NotBlank(message = "密码不能为空")
    private String password;

}
