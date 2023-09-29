//ip_api.js
import request from '@/utils/request'

/**
 * 添加网关路由服务
 */
export const addIp = data => {
    return request({
        url: '/ip/add',
        method: 'post',
        data: data
    })
};

/**
 * 添加网关路由服务
 */
export const updateIp = data => {
    return request({
        url: '/ip/update',
        method: 'post',
        data: data
    })
};

/**
 * 查询网关路由列表
 */
export const ipPageList = data => {
    return request({
        url: '/ip/pageList',
        method: 'post',
        data: data
    })
};

/**
 * 删除网关路由
 */
export const deleteIp = data => {
    return request({
        url: '/ip/delete',
        method: 'get',
        params: data
    })
};
