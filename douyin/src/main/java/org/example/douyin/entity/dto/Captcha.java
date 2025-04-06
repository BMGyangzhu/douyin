package org.example.douyin.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 系统验证码
 * </p>
 *
 * @author xhy
 * @since 2023-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)   
public class Captcha implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @NotBlank(message = "uuid为空")
    private String uuid;

    /**
     * 验证码
     */
    @NotBlank(message = "图形验证码为空")
    private String code;

    /**
     * 邮箱
     */
    @Email
    @NotBlank(message = "邮箱不可为空")
    private String email;
    


}
