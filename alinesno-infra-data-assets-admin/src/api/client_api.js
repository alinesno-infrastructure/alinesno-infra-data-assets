//client_api.js
import request from '@/utils/request'

/**
 * 添加客户端
 */
export const addClient = data => {
    return request({
        url: '/client/add',
        method: 'post',
        data: data
    })
};

/**
 * 更新客户端
 */
export const updateClient = data => {
    return request({
        url: '/client/update',
        method: 'post',
        data: data
    })
};

/**
 * 分页查询客户端列表
 */
export const clientPageList = data => {
    return request({
        url: '/client/pageList',
        method: 'post',
        data: data
    })
};

/**
 * 启用客户端
 */
export const startClient = data => {
    return request({
        url: '/client/start',
        method: 'get',
        params: data
    })
};

/**
 * 停止客户端
 */
export const stopClient = data => {
    return request({
        url: '/client/stop',
        method: 'get',
        params: data
    })
};

/**
 * 删除客户端
 */
export const deleteClient = data => {
    return request({
        url: '/client/delete',
        method: 'get',
        params: data
    })
};
