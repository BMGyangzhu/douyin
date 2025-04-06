package org.example.douyin.service;

import com.qiniu.storage.model.FileInfo;
import org.example.douyin.entity.File;

/**
 * @author bgmyangzhu
 * @date 2025/4/3 13:20
 */
public interface QiNiuFileService extends FileCloudService{

    /**
     * 获取签名
     * @return
     */
    String getToken();


    /**
     * 获取文件信息
     * @param url
     * @return
     */
    FileInfo getFileInfo(String url);
}
