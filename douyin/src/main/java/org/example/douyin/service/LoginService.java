package org.example.douyin.service;

import org.example.douyin.entity.dto.CaptchaDTO;
import org.example.douyin.entity.dto.LoginDTO;
import org.example.douyin.util.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bgmyangzhu
 * @date 2025/3/30 21:34
 */
public interface LoginService {
    R emailLogin(LoginDTO loginDTO);

    void getImageCaptcha(String uuId, HttpServletResponse response) throws IOException;

    R checkCodeAndSendToEmail(LoginDTO loginDTO);

    R checkEmailCode(LoginDTO loginDTO);
}
