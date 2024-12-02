package org.example.tiktok01.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.tiktok01.entity.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * @author bgmyangzhu
 * @date 2024/12/1 16:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Favorites extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @NotBlank(message = "得给你得收藏夹取个名字吧?")
    private String name;
    
    private String description;
    
    private Long userId;
    
    // 收藏夹下得视频总数
    @TableField(exist = false)
    private Long videoCount;
}
