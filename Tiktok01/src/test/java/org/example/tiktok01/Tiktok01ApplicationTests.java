package org.example.tiktok01;

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

    @Test
    void contextLoads() {
        HttpServletResponse httpServletResponse;
        
        
        String code = CaptchaServiceImpl.getSixCode();
        System.out.println(code);
    }

}
