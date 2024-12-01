package org.example.tiktok01.service.Impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.tiktok01.entity.Captcha;
import org.example.tiktok01.service.CaptchaService;
import org.example.tiktok01.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author bgmyangzhu
 * @date 2024/11/30 13:53
 */
@Service
public class LoginServiceImpl implements LoginService {
    
    @Autowired
    private CaptchaService captchaService; 
    
    
    @Override
    public void captcha(String uuid, HttpServletResponse response) throws IOException {
        if(ObjectUtils.isEmpty(uuid)) throw new IllegalArgumentException("uuid不能为空");
        response.setHeader("Cache-Control","no-store, no-cache");
        response.setContentType("image/jpeg");
        BufferedImage image = captchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"jpg",out);
        IOUtils.closeQuietly(out);
    }
    
    @Override
    public Boolean getCode(Captcha captcha) throws Exception{
        return captchaService.validate(captcha);
    }
    
    
    
   
}
