package org.example.douyin.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author bgmyangzhu
 * @date 2025/3/31 16:41
 */
@Getter
public enum CaptchaStatus {
    VALID(true, "验证通过"),
    EXPIRED(false, "验证码已过期或不存在"),
    MISMATCH(false, "验证码不匹配"),
    ISNULL(false, "用户输入的邮箱或验证码为空");

    // Getter 方法
    private final boolean valid;  // 是否验证通过
    private final String message; // 状态描述
    

    // 构造方法
    CaptchaStatus(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

}
