package org.example.douyin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String email;
    


}
