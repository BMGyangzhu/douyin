package org.example.douyin.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.example.douyin.entity.BaseEntity;

import javax.validation.constraints.Email;
import java.util.Set;

/**
 * @author bgmyangzhu
 * @date 2025/4/1 23:09
 */
@Data
public class UserVO {
    
    private Long id;
    
    private String nickName;
    
    private String description;
    
    // true 为男，false为女
    private Boolean sex;

    @Email
    private String email;
    
//     用户默认收藏夹id
//    private Long defaultFavoritesId;

//     头像
    private Long avatar;
    
//    @TableField(exist = false)
//    private Boolean each;
    
//    @TableField(exist = false)
//    private Set<String> roleName;
}
