package org.example.tiktok01.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.tiktok01.entity.RegisterVO;
import org.example.tiktok01.entity.user.User;

/**
 * @author bgmyangzhu
 * @date 2024/12/1 16:27
 */
public interface UserService extends IService<User> {

    /**
     * 注册
     * @param registerVO
     * @return
     * @throws Exception
     */
    boolean register(RegisterVO registerVO) throws Exception;
}
