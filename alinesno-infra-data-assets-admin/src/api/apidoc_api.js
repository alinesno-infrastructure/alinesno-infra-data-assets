//apidoc_api.js
import request from '@/utils/request'

/**
 * 查询网关路由列表
 */
export const apiDocList = data => {
    return request({
        url: '/apiDoc/list',
        method: 'get',
        params: data
    })
};

/**
 * 保存API接口内容
 */
export const saveApiDoc = data => {
    return request({
        url: '/apiDoc/save',
        method: 'post',
        data: data
    })
};

/**
 * 查询API接口内容
 */
export const findByApiDoc = data => {
    return request({
        url: '/apiDoc/findById',
        method: 'get',
        params: data
    })
};