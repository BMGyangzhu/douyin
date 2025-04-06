package org.example.douyin.entity.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author bgmyangzhu
 * @date 2025/4/6 23:54
 */
@Data
public class BasePageResponse {
    
    private Long page = 1L;
    private Long limit = 15L;
    
    public IPage page() {
        return new Page(page == null ? 1L : this.page, limit == null ? 15L : this.limit);
    }
}
