package org.example.douyin.service;

import org.example.douyin.entity.Captcha;
import org.example.douyin.entity.dto.UserDTO;
import org.example.douyin.entity.vo.UserVO;
import org.example.douyin.util.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bgmyangzhu
 * @date 2025/3/30 21:34
 */
public interface LoginService {

    UserVO login(UserDTO userDTO);

    void getImageCaptcha(String uuId, HttpServletResponse response) throws IOException;

    R checkImageCodeAndGetEmailCode(Captcha captcha);

}
