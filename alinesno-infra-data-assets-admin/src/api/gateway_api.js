//sys_api.js
import request from '@/utils/request'

/**
 * 添加字典组
 */
export const getGateWayInfo = data => {
    return request({
        url: '',
        method: 'post',
		headers: {'Content-type': 'multipart/form-data'},
        data: data
    })
};


/**
 * 添加网关路由服务
 */
export const addRoute = data => {
    return request({
        url: '/route/add',
        method: 'post',
        data: data
    })
};

/**
 * 添加网关路由服务
 */
export const updateRoute = data => {
    return request({
        url: '/route/update',
        method: 'post',
        data: data
    })
};

/**
 * 查询网关路由分页列表
 */
export const routePageList = data => {
    return request({
        url: '/route/pageList',
        method: 'post',
        data: data
    })
};

/**
 * 启用网关路由
 */
export const startRoute = data => {
    return request({
        url: '/route/start',
        method: 'get',
        params: data
    })
};

/**
 * 停止网关路由
 */
export const stopRoute = data => {
    return request({
        url: '/route/stop',
        method: 'get',
        params: data
    })
};

/**
 * 删除网关路由
 */
export const deleteRoute = data => {
    return request({
        url: '/route/delete',
        method: 'get',
        params: data
    })
};

/**
 * 删除网关路由
 */
export const getToken = () => {
  return request({
    url: '/route/getToken',
    method: 'get'
  })
};
