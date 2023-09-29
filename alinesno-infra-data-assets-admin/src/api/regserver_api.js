//regserver_api.js
import request from '@/utils/request'

/**
 * 添加客户端关联的注册服务
 */
export const addRegServer = data => {
    return request({
        url: '/regServer/add',
        method: 'post',
        data: data
    })
};

/**
 * 修改客户端关联的注册服务
 */
export const updateRegServer = data => {
    return request({
        url: '/regServer/update',
        method: 'post',
        data: data
    })
};

/**
 * 查询客户端关联的注册服务分页列表
 */
export const regServerPageList = data => {
    return request({
        url: '/regServer/serverPageList',
        method: 'post',
        data: data
    })
};

/**
 * 查询客户端关联的注册服务分页列表
 */
export const regClientPageList = data => {
    return request({
        url: '/regServer/clientPageList',
        method: 'post',
        data: data
    })
};

/**
 * 启用客户端关联的注册服务访问状态
 */
export const startRegServer = data => {
    debugger
    return request({
        url: '/regServer/start',
        method: 'get',
        params: data
    })
};

/**
 * 停止客户端关联的注册服务访问状态
 */
export const stopRegServer = data => {
    return request({
        url: '/regServer/stop',
        method: 'get',
        params: data
    })
};

/**
 * 全部启用客户端关联的注册服务访问状态
 */
export const startAllRegServer = data => {
    return request({
        url: '/regServer/startClientAllRoute',
        method: 'get',
        params: data
    })
};

/**
 * 全部停止客户端关联的注册服务访问状态
 */
export const stopAllRegServer = data => {
    return request({
        url: '/regServer/stopClientAllRoute',
        method: 'get',
        params: data
    })
};

/**
 * 取消客户端关联的注册服务
 */
export const deleteRegServer = data => {
    return request({
        url: '/regServer/delete',
        method: 'get',
        params: data
    })
};

/**
 * 获取未注册网关路由列表
 */
export const notRegServerPageList = data => {
    return request({
        url: '/regServer/notRegServerPageList',
        method: 'post',
        data: data
    })
};

// ==========================服务端管理=============================

/**
 * 添加客户端关联的注册服务
 */
export const addRegClient = data => {
    return request({
        url: '/regServer/add',
        method: 'post',
        data: data
    })
};

/**
 * 全部启用客户端关联的注册服务访问状态
 */
export const startAllRegClient = data => {
    return request({
        url: '/regServer/startRouteAllClient',
        method: 'get',
        params: data
    })
};

/**
 * 全部停止客户端关联的注册服务访问状态
 */
export const stopAllRegClient = data => {
    return request({
        url: '/regServer/stopRouteAllClient',
        method: 'get',
        params: data
    })
};

/**
 * 启用客户端关联的注册服务访问状态
 */
export const startRegClient = data => {
    debugger
    return request({
        url: '/regServer/start',
        method: 'get',
        params: data
    })
};

/**
 * 停止客户端关联的注册服务访问状态
 */
export const stopRegClient = data => {
    return request({
        url: '/regServer/stop',
        method: 'get',
        params: data
    })
};

/**
 * 取消客户端关联的注册服务
 */
export const deleteRegClient = data => {
    return request({
        url: '/regServer/delete',
        method: 'get',
        params: data
    })
};

/**
 * 获取当前网关路由未注册客户端列表
 */
export const notRegClientPageList = data => {
    return request({
        url: '/regServer/notRegClientPageList',
        method: 'post',
        data: data
    })
};

/**
 * 获取当前网关路由未注册客户端列表
 */
export const regClientList = data => {
    return request({
        url: '/regServer/regClientList',
        method: 'post',
        data: data
    })
};

/**
 * 创建当前网关路由注册客户端的TOKEN令牌
 */
 export const createRegClientToken = data => {
    return request({
        url: '/regServer/createToken',
        method: 'post',
        data: data
    })
};

/**
 * 移除当前网关路由注册客户端的TOKEN令牌
 */
 export const removeRegClientToken = data => {
    return request({
        url: '/regServer/removeToken',
        method: 'post',
        data: data
    })
};