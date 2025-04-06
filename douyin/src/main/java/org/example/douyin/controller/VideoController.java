package org.example.douyin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.douyin.entity.dto.BasePageRequest;
import org.example.douyin.entity.video.Video;
import org.example.douyin.holder.UserHolder;
import org.example.douyin.limit.Limit;
import org.example.douyin.service.QiNiuFileService;
import org.example.douyin.service.video.VideoService;
import org.example.douyin.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 15:16
 */
@RestController
@CrossOrigin
@RequestMapping("/douyin/video")
public class VideoController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private QiNiuFileService fileService;

    /**
     * 获取文件上传token
     */
    @GetMapping("/token")
    public R getToken() {
        return R.ok().data(fileService.getToken());
    }
    
    @PostMapping
    @Limit(limit = 5, time = 3600L, msg = "一小时内发布视频不能超过5次")
    public R publishVideo(@RequestBody @Validated Video video) {
        videoService.publishVideo(video);
        return R.ok().message("发布成功，请等待审核");
    }
    
    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return R.ok().message("删除成功");
    }

    /**
     * 查看用户所管理的视频 --稿件管理
     * @param 
     * @return
     */
//    @GetMapping
//    public R listVideo(BasePage basePage) {
//        return  R.ok().data(videoService.listVideoByUserId(basePage, UserHolder.get()));
//    }
    
    @GetMapping
    public R listVideoByUser(BasePageRequest request) {
        IPage<Video> response = videoService.listVideoByUserId(request, UserHolder.get());
        return R.ok().data(response);
    }
    
    
}
