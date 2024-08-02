//count_api.js
import request from '@/utils/request'

/**
 * 统计网关路由请求量
 */
export const countRequest = data => {
    return request({
        url: '/count/request',
        method: 'post',
		loading: false,
        data: data
    })
};

/**
 * 统计负载网关路由请求量
 */
export const countBalancedRequest = data => {
    return request({
        url: '/count/balanced/request',
        method: 'post',
		loading: false,
        data: data
    })
};

/**
 * 获取网关路由集合
 */
export const countRoutePageList = data => {
    return request({
        url: '/count/route/pageList',
        method: 'post',
		loading: false,
        data: data
    })
};

/**
 * 统计按7天和按24小时维度计算的请求总量
 */
export const countRequestTotal = data => {
    return request({
        url: '/count/request/total',
        method: 'post',
		loading: false,
        data: data
    })
};

/**
 * 统计按7天和按24小时维度计算的请求总量
 */
 export const countAppRequestTotal = data => {
    return request({
        url: '/count/request/app/total',
        method: 'get',
		loading: false,
        params: data
    })
};