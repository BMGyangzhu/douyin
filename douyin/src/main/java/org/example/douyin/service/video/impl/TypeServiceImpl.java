package org.example.douyin.service.video.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.douyin.entity.video.Type;
import org.example.douyin.mapper.video.TypeMapper;
import org.example.douyin.service.video.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 15:08
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    
}
