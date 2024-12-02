package org.example.tiktok01.service.user.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.tiktok01.constant.RedisConstant;
import org.example.tiktok01.entity.RegisterVO;
import org.example.tiktok01.entity.user.Favorites;
import org.example.tiktok01.entity.user.User;
import org.example.tiktok01.exception.BaseException;
import org.example.tiktok01.mapper.user.UserMapper;
import org.example.tiktok01.service.user.FavoritesService;
import org.example.tiktok01.service.user.UserService;
import org.example.tiktok01.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bgmyangzhu
 * @date 2024/12/1 16:33
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisCacheUtil redisCacheUtil;
    
    @Autowired
    private FavoritesService favoritesService;
    
    
    @Override
    public boolean register(RegisterVO registerVO) throws Exception {
        // 先检查邮箱是否存在
        final long count = count(new LambdaQueryWrapper<User>().eq(User::getEmail, registerVO.getEmail()));
        
        if(count == 1) throw new BaseException("邮箱已被注册");
        
        final String code = registerVO.getCode();
        final Object o = redisCacheUtil.get(RedisConstant.EMAIL_CODE + registerVO.getEmail());
        
        if(o == null) throw new BaseException("验证码为空");
        
        if(!code.equals(o)) return false;
        
        final User user = new User();
        user.setNickName(registerVO.getNickName());
        user.setEmail(registerVO.getEmail());
        user.setDescription("这个人很懒...");
        // TO DO 要做加密
        user.setPassword(registerVO.getPassword());
        save(user);
        
        // 创建默认收藏夹
        final Favorites favorites = new Favorites();
        favorites.setUserId(user.getId());
        favorites.setName("默认收藏夹");
        favoritesService.save(favorites);
        
        user.setDefaultFavoritesId(favorites.getId());
        updateById(user);
        return true;
        
    }
}
