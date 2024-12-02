package org.example.tiktok01;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.tiktok01.entity.Captcha;
import org.example.tiktok01.mapper.CaptchaMapper;
import org.example.tiktok01.service.CaptchaService;
import org.example.tiktok01.service.Impl.CaptchaServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletResponse;

@SpringBootTest
class Tiktok01ApplicationTests {
    
    @Autowired
    CaptchaService captchaService;
    
    @Autowired
    CaptchaMapper captchaMapper;

    @Test
    void contextLoads() {
        captchaService.removeById("58efee08-1ce5-4cb7-8ef2-71cb4484a740");
        
    }

}
