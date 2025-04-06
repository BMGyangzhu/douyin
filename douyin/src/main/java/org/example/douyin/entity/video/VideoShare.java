package org.example.douyin.entity.video;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.douyin.entity.BaseEntity;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 15:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VideoShare extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    private Long videoId;
    
    private Long userId;
    
    private String ip;
}
