package org.example.douyin.service.video.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.douyin.entity.dto.BasePageRequest;
import org.example.douyin.entity.video.Video;
import org.example.douyin.entity.vo.BasePageResponse;
import org.example.douyin.mapper.video.VideoMapper;
import org.example.douyin.service.video.VideoService;
import org.example.douyin.service.video.VideoShareService;
import org.springframework.stereotype.Service;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 15:08
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public void deleteVideo(Long id) {
        
    }

    @Override
    public void publishVideo(Video video) {

    }

    @Override
    public IPage<Video> listVideoByUserId(BasePageRequest request, Long userId) {
        BasePageResponse response = new BasePageResponse();
        BeanUtil.copyProperties(request,response);
        LambdaQueryWrapper<Video> queryWrapper = Wrappers.lambdaQuery(Video.class)
                .eq(Video::getUserId,userId)
                .orderByDesc(Video::getGmtCreated);
        final IPage<Video> page = page(response.page(), queryWrapper);
//        setUserVOAndUrl(page.getRecords());
        return page;
        
    }
}
