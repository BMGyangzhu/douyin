package org.example.tiktok01.service.Impl;

import org.example.tiktok01.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author bgmyangzhu
 * @date 2024/11/29 15:13
 */
@Service
public class EmailServiceImpl implements EmailService {
    
    @Autowired
    private SimpleMailMessage simpleMailMessage;
    
    @Autowired
    JavaMailSender javaMailSender;
    
    @Value("${MAIL_NAME:2378150995@qq.com}")
    String fromName;
    
    @Override
    @Async
    public void send(String email, String context) {
        simpleMailMessage.setSubject("BGM养猪");
        simpleMailMessage.setFrom(fromName);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(context);
        javaMailSender.send(simpleMailMessage);
    }
}
