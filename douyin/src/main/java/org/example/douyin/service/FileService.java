package org.example.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.douyin.entity.File;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 13:02
 */
public interface FileService extends IService<File> {
    
    Long save(String fileKey, Long userId);

    /**
     * 根据视频id生成图片
     * @param fileId
     * @param userId
     * @return
     */
    Long generatePhoto(Long fileId, Long userId);

    /**
     * 获取文件真实URL
     * @param fileId
     * @return
     */
    File getFileTrustUrl(Long fileId);



}

