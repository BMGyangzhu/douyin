package org.example.douyin.service.video;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.douyin.entity.dto.BasePageRequest;
import org.example.douyin.entity.video.Video;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 15:07
 */
public interface VideoService extends IService<Video> {
    void deleteVideo(Long id);

    void publishVideo(Video video);
    
    IPage<Video> listVideoByUserId(BasePageRequest request, Long userId);
}
