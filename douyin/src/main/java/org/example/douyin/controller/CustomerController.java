package org.example.douyin.controller;

import org.example.douyin.config.QiNiuConfig;
import org.example.douyin.holder.UserHolder;
import org.example.douyin.service.UserService;
import org.example.douyin.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bgmyangzhu
 * @date 2025/4/6 1:31
 */
@RestController
@RequestMapping("/douyin/customer")
public class CustomerController {
    
    @Autowired
    QiNiuConfig qiNiuConfig;
    
    @Autowired
    private UserService userService;
    
//    @Autowired
//    private FavoriteService favoriteService;

    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    @GetMapping("/getInfo/{userId}")
    public R getInfo(@PathVariable Long userId) {
        return R.ok().data(userService.getInfo(userId));
    }
    
     @GetMapping("/getInfo")
    public R getDefaultInfo() {
        return R.ok().data(userService.getInfo(UserHolder.get()));
     }
}
