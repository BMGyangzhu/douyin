package org.example.tiktok01.service.user.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.tiktok01.entity.user.Favorites;
import org.example.tiktok01.mapper.user.FavoritesMapper;
import org.example.tiktok01.service.user.FavoritesService;
import org.springframework.stereotype.Service;

/**
 * @author bgmyangzhu
 * @date 2024/12/1 16:53
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {
    
}
