package org.example.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.douyin.entity.User;

/**
 * @author bgmyangzhu
 * @date 2025/3/31 18:29
 */
public interface UserService extends IService<User> {
    User createUserWithEmail(String email);
}
