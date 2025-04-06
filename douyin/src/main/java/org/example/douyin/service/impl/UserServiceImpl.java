package org.example.douyin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.douyin.entity.dto.FindPasswordRequest;
import org.example.douyin.entity.dto.Register;
import org.example.douyin.entity.User;
import org.example.douyin.entity.vo.UserVO;
import org.example.douyin.enums.CaptchaStatus;
import org.example.douyin.exception.BaseException;
import org.example.douyin.mapper.UserMapper;
import org.example.douyin.service.CaptchaService;
import org.example.douyin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void register(Register register){
        // 查看邮箱是否存在
        long count = count(new LambdaQueryWrapper<User>().eq(User::getEmail, register.getEmail()));
        if (count == 1) {
            throw new BaseException("邮箱已被注册");
        }
        String email = register.getEmail();
        String emailCode = register.getEmailCode();
        CaptchaStatus captchaStatus = captchaService.validateEmailCode(email, emailCode, true);
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
        
        
    }

    @Override
    public void findPassword(FindPasswordRequest request) {
        final String email = request.getEmail();
        final String password = request.getPassword();
        final String emailCode = request.getEmailCode();

        User user = lambdaQuery().eq(User::getEmail, email).one();
        if (user == null) {
            throw new BaseException("该邮箱不存在");
        }
        
        CaptchaStatus captchaStatus = captchaService.validateEmailCode(email, emailCode, true);
        if (!captchaStatus.isValid()) {
            throw new BaseException(captchaStatus.getMessage());
        }

        String encryptPassword = passwordEncoder.encode(password);
        user.setPassword(encryptPassword);
        
        updateById(user);

    }
    
    @Override
    public UserVO getInfo(Long userId) {
        
        final User user = getById(userId);
        if (ObjectUtils.isEmpty(user)) {
            return new UserVO();
        }
        final UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        
        // 查询关注数量
//        final long followCount = followService.getFollowCount(userId);
        
        // 查询粉丝数量
//        final long fansCount = followService.getFansCount(userId);
//        userVO.setFollow(followCount);
//        userVO.setFans(fansCount);
        return userVO;
    }
}
