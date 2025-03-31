package org.example.douyin.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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
public class CaptchaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 验证码
     */
    private String code;

    /**
     * 邮箱验证码
     */
    private String emailCode;
    


}
