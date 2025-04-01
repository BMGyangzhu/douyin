package org.example.douyin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.douyin.entity.Captcha;
import org.example.douyin.entity.User;
import org.example.douyin.entity.dto.UserDTO;
import org.example.douyin.entity.vo.UserVO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 邮箱登录/注册
     * @return UserVO
     */
    @Override
    public UserVO login(UserDTO userDTO) {
        log.info("用户登录：{}",userDTO );
        String password = userDTO.getPassword();

        User user = userService.lambdaQuery().eq(User::getEmail, userDTO.getEmail()).one();
        if (ObjectUtils.isEmpty(user)) {
            throw new BaseException("该账号不存在");
        }
        
        boolean match = passwordEncoder.matches(password, user.getPassword());
        if (!match) {
            throw new BaseException("密码不一致");
        }

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);

        // 返回成功
        return userVO;
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
    public R checkImageCodeAndGetEmailCode(Captcha captcha) {
        // 1.验证用户输入图形验证码是否正确
        String ImageCode = captcha.getCode();
        String uuId = captcha.getUuid();
        String email = captcha.getEmail();
        CaptchaStatus captchaStatus = captchaService.validateImageCaptcha(uuId, ImageCode);
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

 
    
}
