package org.example.tiktok01.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiktok01.entity.Captcha;
import org.example.tiktok01.service.LoginService;
import org.example.tiktok01.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bgmyangzhu
 * @date 2024/11/29 15:11
 */
@RestController
@Slf4j
public class LoginController {
    
    @Autowired
    LoginService loginService;
    
    @GetMapping("/captcha.jpg/{uuId}")
    public void captcha(HttpServletResponse response, @PathVariable String uuId) throws IOException{
        log.info("captcha, response: {}", response.toString());
        loginService.captcha(uuId,response);
    }
    
    @PostMapping("/getCode")
    public R getCode(@RequestBody @Validated Captcha captcha) throws Exception {
        if (!loginService.getCode(captcha)){
            return R.error().message("验证码错误");
        }
        return R.ok().message("发送成功,请耐心等待");
    }
}
