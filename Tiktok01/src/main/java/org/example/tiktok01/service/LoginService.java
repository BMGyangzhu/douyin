package org.example.tiktok01.service;

import org.example.tiktok01.entity.Captcha;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bgmyangzhu
 * @date 2024/11/30 0:38
 */
public interface LoginService {

    /**
     * 生成图形验证码
     * @param uuid
     * @param response
     * @throws IOException
     */
    void captcha(String uuid, HttpServletResponse response) throws IOException;

    Boolean getCode(Captcha captcha) throws Exception;
}
