package org.example.douyin.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.douyin.entity.Captcha;
import org.example.douyin.entity.Register;
import org.example.douyin.entity.dto.UserDTO;
import org.example.douyin.entity.vo.UserVO;
import org.example.douyin.service.LoginService;
import org.example.douyin.service.UserService;
import org.example.douyin.util.JwtUtils;
import org.example.douyin.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

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
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public R register(@RequestBody @Validated Register register) {
        if (userService.register(register)) {
            return R.ok().message("注册成功");
        }
        return  R.error().message("注册失败，验证码错误");
    }
    
    @GetMapping("/captcha.jpg/{uuId}")
    public void getImageCaptcha(HttpServletResponse response, @PathVariable String uuId) throws IOException {
        loginService.getImageCaptcha(uuId, response);
    }
    
    @PostMapping
    public R login(@RequestBody @Validated UserDTO userDTO) {
        UserVO userVO = loginService.login(userDTO);
        // 登录成功，生成token
        String token = JwtUtils.getJwtToken(userVO.getId(),userVO.getNickName());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("name", userVO.getNickName());
        map.put("user",userVO);
        return R.ok().data(map);

    }
    
    @PostMapping("/getEmailCode")
    public R getEmailCode(@RequestBody @Validated Captcha captcha) {
        log.info("获取邮箱验证码, captcha:{}", captcha);    
        return loginService.checkImageCodeAndGetEmailCode(captcha);
    }


    
    
}
