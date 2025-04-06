package org.example.douyin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 13:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class File extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    // 文件key
    private String fileKey;
    
    // 文件格式
    private String format;
    
    // 文件类型
    private String type;
    
    // 文件大小
    private Long size;
    
    // 发布者
    private Long userId;
}
