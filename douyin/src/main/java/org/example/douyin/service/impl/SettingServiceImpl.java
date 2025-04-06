package org.example.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.douyin.entity.Setting;
import org.example.douyin.mapper.SettingMapper;
import org.example.douyin.service.SettingService;
import org.springframework.stereotype.Service;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 14:31
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {
}
