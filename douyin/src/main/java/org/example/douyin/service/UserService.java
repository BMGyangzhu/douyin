package org.example.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.douyin.entity.dto.FindPasswordRequest;
import org.example.douyin.entity.dto.Register;
import org.example.douyin.entity.User;
import org.example.douyin.entity.vo.UserVO;

/**
 * @author bgmyangzhu
 * @date 2025/3/31 18:29
 */
public interface UserService extends IService<User> {
    void register(Register register);

    void findPassword(FindPasswordRequest request);

    UserVO getInfo(Long userId);
}
