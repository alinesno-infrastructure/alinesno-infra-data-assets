//monitor_api.js
import request from '@/utils/request'

/**
 * 查询监控列表
 */
 export const monitorList = data => {
    return request({
        url: '/monitor/list',
        method: 'post',
        data: data
    })
};

/**
 * 关闭本次告警
 */
 export const closeMonitor = data => {
    return request({
        url: '/monitor/close',
        method: 'get',
        params: data
    })
};