package org.example.tiktok01.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiktok01.entity.Captcha;
import org.example.tiktok01.entity.FindPasswordVO;
import org.example.tiktok01.entity.RegisterVO;
import org.example.tiktok01.entity.user.User;
import org.example.tiktok01.service.LoginService;
import org.example.tiktok01.util.JwtUtils;
import org.example.tiktok01.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author bgmyangzhu
 * @date 2024/11/29 15:11
 */
@RestController
@Slf4j
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    @PostMapping("/login")
    public R login(@RequestBody @Validated User user){
        loginService.login(user);
        // 登录成功, 生成token
        String token = JwtUtils.getJwtToken(user.getId(),user.getNickName());
        final HashMap<Object, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("name",user.getNickName());
        map.put("user",user);
        return R.ok().data(map);
    }
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
    
    @PostMapping("/register")
    public R register(@RequestBody @Validated RegisterVO registerVO) throws Exception{
        if (!loginService.register(registerVO)) {
            return R.error().message("注册失败, 验证码错误");
        }
        return R.ok().message("注册成功");
    }
    
    @PostMapping("/findPassword")
    public R findPassword(@RequestBody @Validated FindPasswordVO findPasswordVO, HttpServletResponse response){
        final Boolean b = loginService.findPassword(findPasswordVO);
        return R.ok().message(b ? "修改成功" : "修改失败, 验证码不正确");
    }
}
