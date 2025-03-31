package org.example.douyin.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.douyin.entity.User;
import org.example.douyin.mapper.UserMapper;
import org.example.douyin.service.UserService;
import org.springframework.stereotype.Service;

import static org.example.douyin.util.SystemConstants.USER_NICK_PREFIX;

/**
 * @author bgmyangzhu
 * @date 2025/3/31 18:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
