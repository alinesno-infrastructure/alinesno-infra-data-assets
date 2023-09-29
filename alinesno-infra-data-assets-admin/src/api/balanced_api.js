//balanced_api.js
import request from '@/utils/request'

/**
 * 添加负载配置
 */
export const addBalanced = data => {
    return request({
        url: '/balanced/add',
        method: 'post',
        data: data
    })
};

/**
 * 删除负载配置
 */
export const deleteBalanced = data => {
    return request({
        url: '/balanced/delete',
        method: 'get',
        params: data
    })
};

/**
 * 更新负载配置
 */
export const updateBalanced = data => {
    return request({
        url: '/balanced/update',
        method: 'post',
        data: data
    })
};

/**
 * 查找负载配置
 */
export const findByBalanced = data => {
    return request({
        url: '/balanced/findById',
        method: 'get',
        params: data
    })
};


/**
 * 分页展示负载配置
 */
export const balancedPageList = data => {
    return request({
        url: '/balanced/pageList',
        method: 'post',
        data: data
    })
};

/**
 * 启用负载服务
 */
export const startBalanced = data => {
    return request({
        url: '/balanced/start',
        method: 'get',
        params: data
    })
};

/**
 * 禁用负载服务
 */
export const stopBalanced = data => {
    return request({
        url: '/balanced/stop',
        method: 'get',
        params: data
    })
};

/**
 * 已注册负载服务
 */
export const loadServerRegList = data => {
    return request({
        url: '/loadServer/regList',
        method: 'post',
        data: data
    })
};

/**
 * 未注册负载服务
 */
export const loadServerNotRegList = data => {
    return request({
        url: '/loadServer/notRegPageList',
        method: 'post',
        data: data
    })
};
