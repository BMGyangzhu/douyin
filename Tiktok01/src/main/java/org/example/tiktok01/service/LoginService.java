package org.example.tiktok01.service;

import org.example.tiktok01.entity.Captcha;
import org.example.tiktok01.entity.FindPasswordVO;
import org.example.tiktok01.entity.RegisterVO;
import org.example.tiktok01.entity.user.User;

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
    
    Boolean register(RegisterVO registerVO) throws Exception;
    User login(User user);

    Boolean checkCode(String email, Integer code);
    Boolean findPassword(FindPasswordVO findPasswordVO);
    
}
