package org.example.douyin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.douyin.entity.User;
import org.example.douyin.entity.dto.CaptchaDTO;
import org.example.douyin.entity.dto.LoginDTO;
import org.example.douyin.enums.CaptchaStatus;
import org.example.douyin.exception.BaseException;
import org.example.douyin.service.CaptchaService;
import org.example.douyin.service.EmailService;
import org.example.douyin.service.LoginService;
import org.example.douyin.service.UserService;
import org.example.douyin.util.R;
import org.example.douyin.util.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.example.douyin.util.RedisConstants.LOGIN_USER_KEY;
import static org.example.douyin.util.RedisConstants.LOGIN_USER_TTL;

/**
 * @author bgmyangzhu
 * @date 2025/3/30 21:34
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    
    @Autowired
    private CaptchaService captchaService;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;

    /**
     * 邮箱登录/注册
     * @param loginDTO
     * @return
     */
    @Override
    @Transactional  
    public R emailLogin(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        User user = userService.lambdaQuery().eq(User::getEmail, email).one();
        // 如果已经注册，直接登录
        if (user == null) {
            user = userService.createUserWithEmail(email);
        }
        // 验证邮箱验证码
        checkEmailCode(loginDTO);
        // 保存用户信息到 redis 中 
        String token = UUID.randomUUID().toString(true); 
        // 将 User 对象转为 HashMap存储
        Map<String, Object> userMap = BeanUtil.beanToMap(user, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true).setFieldValueEditor((filedName, fieldValue) -> fieldValue != null ? fieldValue.toString() : null));
        // 存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey,userMap);
        // 设置 token 有效期
        stringRedisTemplate.expire(tokenKey,LOGIN_USER_TTL, TimeUnit.MINUTES); 
        
        // 返回成功
        return R.ok();
    }
    

    /**
     * 将图形验证码返回给前端
     * @param uuId
     * @param response
     * @throws IOException
     */
    @Override
    public void getImageCaptcha(String uuId, HttpServletResponse response) throws IOException {
        if (ObjectUtils.isEmpty(uuId)) throw new BaseException("UUID不能为空");
        response.setHeader("Cache-Control","no-store, no-cache");
        response.setContentType("image/jpeg");
        BufferedImage image = captchaService.getImageCaptcha(uuId);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        IOUtils.closeQuietly(outputStream);
    }
    
    @Override
    public R checkCodeAndSendToEmail(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        CaptchaDTO captchaDTO = loginDTO.getCaptcha();
        // 1.验证用户输入图形验证码是否正确
        CaptchaStatus captchaStatus = captchaService.validateImageCaptcha(captchaDTO);
        // 2.不正确，抛出异常
        if(!captchaStatus.isValid()) {
            throw new BaseException(captchaStatus.getMessage());
        }
        // 3.正确，将邮箱验证码存入redis
        String redisKey = RedisConstants.EMAIL_CODE + email;
        long ttl = RedisConstants.EMAIL_CODE_TTL;
        String emailCode = RandomStringUtils.randomNumeric(6);
        try {
            stringRedisTemplate.opsForValue().set(redisKey, emailCode, ttl, TimeUnit.MINUTES);
            // 3.1.主机邮箱发送邮箱验证码到用户邮箱
            emailService.send(email, "验证码：" + emailCode + "，验证码" + ttl + "分钟内有效");
        } catch (Exception e) {
            // 发送失败时清理Redis，避免脏数据
            stringRedisTemplate.delete(redisKey);
            throw new BaseException("邮件发送失败，请重试");
        }
        log.info("发送邮箱验证码：{}",emailCode);
        return R.ok();
    }

    /**
     * 验证用户输入的邮箱验证码
     */
    @Override
    public R checkEmailCode(LoginDTO loginDTO) {
        String emailCode = loginDTO.getCaptcha().getEmailCode();
        String email = loginDTO.getEmail();
        CaptchaStatus captchaStatus = captchaService.validateEmailCode(email, emailCode);
        // 不正确，抛出异常
        if (!captchaStatus.isValid()) {
           throw new BaseException(captchaStatus.getMessage()); 
        }
        return R.ok();
    }
    
    
}
