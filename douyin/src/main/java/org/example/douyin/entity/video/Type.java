package org.example.douyin.entity.video;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.douyin.entity.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 14:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Type extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "分类名称不可为空")
    private String name;
    
    private String description;
    
    private String icon;
    
    private Boolean open;
    
    private String labelNames;
    
    private Integer sort;
    
    @TableField(exist = false)
    private Boolean used;
    
    public List<String> buildLabel() {
        return Arrays.asList(labelNames.split(","));
    }
    
}
