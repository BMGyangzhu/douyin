package org.example.douyin.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.douyin.entity.Register;
import org.example.douyin.entity.User;
import org.example.douyin.enums.CaptchaStatus;
import org.example.douyin.exception.BaseException;
import org.example.douyin.mapper.UserMapper;
import org.example.douyin.service.CaptchaService;
import org.example.douyin.service.UserService;
import org.example.douyin.util.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.example.douyin.util.SystemConstants.USER_NICK_PREFIX;

/**
 * @author bgmyangzhu
 * @date 2025/3/31 18:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    CaptchaService captchaService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public boolean register(Register register){
        // 查看邮箱是否存在
        long count = count(new LambdaQueryWrapper<User>().eq(User::getEmail, register.getEmail()));
        if (count == 1) {
            throw new BaseException("邮箱已被注册");
        }
        String email = register.getEmail();
        String emailCode = register.getEmailCode();
        CaptchaStatus captchaStatus = captchaService.validateEmailCode(email, emailCode);
        if (!captchaStatus.isValid()) {
            throw new BaseException(captchaStatus.getMessage());
        }
        
        User user = new User();
        user.setNickName(register.getNickName());
        user.setEmail(register.getEmail());
        user.setDescription("这个人很懒...");
        String encryptPassword = passwordEncoder.encode(register.getPassword());
        user.setPassword(encryptPassword);
        save(user);
        
        return true;
        
    }
    @Override
    public User createUserWithEmail(String email) {
        // 1.创建用户
        User user = new User();
        user.setEmail(email);
        user.setNickName(USER_NICK_PREFIX + RandomUtil.randomString(10));
        user.setDescription("这个人很懒...");
        // 2.添加用户
        save(user);
        return user;
    }
    
}
