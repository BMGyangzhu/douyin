package org.example.tiktok01.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.example.tiktok01.constant.RedisConstant;
import org.example.tiktok01.entity.Captcha;
import org.example.tiktok01.entity.FindPasswordVO;
import org.example.tiktok01.entity.RegisterVO;
import org.example.tiktok01.entity.user.User;
import org.example.tiktok01.exception.BaseException;
import org.example.tiktok01.mapper.user.UserMapper;
import org.example.tiktok01.service.CaptchaService;
import org.example.tiktok01.service.LoginService;
import org.example.tiktok01.service.user.UserService;
import org.example.tiktok01.util.RedisCacheUtil;
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
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    
    @Autowired
    private CaptchaService captchaService; 
    
    @Autowired
    private RedisCacheUtil redisCacheUtil;
    
    @Autowired
    private UserService userService;
    
    
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


    @Override
    public Boolean checkCode(String email, Integer code) {
        if(ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(code)){
            throw new BaseException("参数为空");
        }
        final Object object = redisCacheUtil.get(RedisConstant.EMAIL_CODE + email);
        
        if(!code.toString().equals(object)){
            throw new BaseException("验证码不正确");
        }
        return true;
    }

    @Override
    public Boolean register(RegisterVO registerVO) throws Exception {
        // 注册成功后删除图形验证码
        if (userService.register(registerVO)){
            captchaService.removeById(registerVO.getUuid());
            return true;
        }
        return userService.register(registerVO);
    }

    @Override
    public User login(User user) {
        final String password = user.getPassword();
        user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail,user.getEmail()));
        if(ObjectUtils.isEmpty(user)) throw new BaseException("该账号不存在");
        
        if(!password.equals(user.getPassword())) throw new BaseException("密码错误");
        
        return user;
    }

    @Override
    public Boolean findPassword(FindPasswordVO findPasswordVO) {
        // 从redis中取出
        final Object object = redisCacheUtil.get(RedisConstant.EMAIL_CODE + findPasswordVO);
        if( object == null){
            return false;
        }
        // 校验
        if (Integer.parseInt(object.toString()) != findPasswordVO.getCode()){
            return false;
        }
        // 修改
        final User user = new User();
        user.setEmail(findPasswordVO.getEmail());
        user.setPassword(findPasswordVO.getNewPassword());
        update(user, new LambdaUpdateWrapper<User>().set(User::getPassword,findPasswordVO.getNewPassword()).eq(User::getEmail,findPasswordVO.getEmail()));
        return true;
        
    }
}
