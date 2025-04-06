package org.example.douyin.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.example.douyin.config.QiNiuConfig;
import org.example.douyin.entity.File;
import org.example.douyin.service.QiNiuFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 13:24
 */
@Service
public class QiNiuFileServiceImpl implements QiNiuFileService {
    
    @Autowired
    private QiNiuConfig qiNiuConfig;
    
    @Override
    public String getToken() { return qiNiuConfig.videoUploadToken(); }
    
    @Override
    public FileInfo getFileInfo(String url) {
        Configuration cfg = new Configuration(Region.region0());
        final Auth auth = qiNiuConfig.buildAuth();
        final String bucket = qiNiuConfig.getBucketName();

        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(bucket,url);
            return fileInfo;
        } catch (QiniuException exception) {
            System.err.println(exception.response.toString());
        }
        return null;
    }
    
}
