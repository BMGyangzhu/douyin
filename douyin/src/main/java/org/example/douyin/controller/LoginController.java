package org.example.douyin.controller;

import cn.hutool.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.douyin.entity.dto.CaptchaDTO;
import org.example.douyin.entity.dto.LoginDTO;
import org.example.douyin.service.LoginService;
import org.example.douyin.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bgmyangzhu
 * @date 2025/3/30 21:34
 */
@Slf4j
@RestController
@RequestMapping("/douyin/login")
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    
    @GetMapping("/captcha.jpg/{uuId}")
    public void getImageCaptcha(HttpServletResponse response, @PathVariable String uuId) throws IOException {
        loginService.getImageCaptcha(uuId, response);
    }
    
    @PostMapping
    public R login(@RequestBody @Validated LoginDTO loginDTO) {
        return loginService.emailLogin(loginDTO);
    }
    
    @PostMapping("/getEmailCode")
    public R getEmailCode(@RequestBody LoginDTO loginDTO) {
        log.info("获取邮箱验证码: {}", loginDTO);    
        return loginService.checkCodeAndSendToEmail(loginDTO);
    }

    /**
     * 校验用户输入的邮箱验证码 
     * @param loginDTO
     * @return
     */
    @PostMapping("/checkEmailCode")
    public R checkEmailCode(@RequestBody LoginDTO loginDTO) {
        return loginService.checkEmailCode(loginDTO);
    }
    
    
}
