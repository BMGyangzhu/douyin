package org.example.tiktok01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author bgmyangzhu
 * @date 2024/11/29 15:12
 */
@Configuration
public class EmailConfig {
    
    @Bean
    public SimpleMailMessage simpleMailMessage() {
        return new SimpleMailMessage();
    }
}
