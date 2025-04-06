package org.example.douyin.entity.dto;

import lombok.Data;

/**
 * @author bgmyangzhu
 * @date 2025/4/6 23:53
 */
@Data
public class BasePageRequest {
    
    private Long page = 1L;
    private Long limit = 15L;
}
