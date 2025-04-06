package org.example.douyin.controller;

import org.example.douyin.config.LocalCache;
import org.example.douyin.config.QiNiuConfig;
import org.example.douyin.entity.File;
import org.example.douyin.entity.Setting;
import org.example.douyin.holder.UserHolder;
import org.example.douyin.service.FileService;
import org.example.douyin.service.SettingService;
import org.example.douyin.util.R;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 13:58
 */
@RestController
@RequestMapping("/douyin/file")
public class FileController implements InitializingBean {
    
    @Autowired
    FileService fileService;
    
    @Autowired
    QiNiuConfig qiNiuConfig;
    
    @Autowired
    SettingService settingService;

    /**
     * 保存到文件表
     */
    @PostMapping
    public R save(String fileKey) {
        return R.ok().data(fileService.save(fileKey, UserHolder.get()));
    }
    
    @GetMapping("/getToken")
    public R token(String type) {
        return R.ok().data(qiNiuConfig.uploadToken(type));
    }
    
    @GetMapping("/{fileId}")
    public void getUUID(HttpServletRequest request, HttpServletResponse response, @PathVariable Long fileId) throws IOException {
        
        String ip = request.getHeader("referer");
        if (!LocalCache.containsKey(ip)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        // 如果不是指定ip调用该接口，不返回
        File url = fileService.getFileTrustUrl(fileId);
        response.setContentType(url.getType());
        response.sendRedirect(url.getFileKey());
    }
    
    @PostMapping("/auth")
    public void auth(@RequestParam(required = false) String uuid, HttpServletResponse response) throws IOException {
        if (uuid == null || LocalCache.containsKey(uuid) == null) {
            response.sendError(403);
        } else {
            LocalCache.remove(uuid);
            response.sendError(200);
        }
    }
    
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        final Setting setting = settingService.list(null).get(0);
//        if (setting == null) {
//            throw new IllegalStateException("settingService.list(null) 返回空，无法初始化");
//        }
//        for (String s : setting.getAllowIp().split(",")) {
//            LocalCache.put(s,true);
//        }
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
