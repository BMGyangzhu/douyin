import * as qiniu from 'qiniu-js'
import request from './request'

const Config = {
    qiniuOSS: {}
}

export const apiVideoGetToken = async() => await request.get("/video/token")

/**
 * 这个函数的主要作用是 通过七牛云上传一个视频文件，并通过回调函数处理上传过程的不同阶段（进行中、出错、完成）。
 * @param {*} file 
 * @param {*} callBack 
 * @returns 
 */
export const apiVideoUpload = async( file, callBack = { next:()=>{}, error:()=>{}, complete: ()=>{}}) => {
    const videoToken = (await apiVideoGetToken()).data.data;
    // 创建上传任务
    const observable = qiniu.upload(file, null, videoToken, {}, Config.qiniuOSS);
    // 订阅上传状态
    const subscription = observable.subscription(callBack.next, callBack.error, callBack.complete);
    // 返回 subscription 对象
    return subscription;
}

/**
 * 获取用户自己的视频
 * GET /video?page=1&limit=5
 *  请求参数（Query 参数）：
 * @param {int} page 当前页
 * @param {int} limit 条数
 * @returns 
 */
export const apiGetVideoByUser = (page = 1, limit = 5) => {
    return request.get(`/video`, {
        params:{
            page,
            limit
        }
    })
}