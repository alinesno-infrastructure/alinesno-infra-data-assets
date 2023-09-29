import request from '@/utils/request'

/**
 * 添加网关路由服务
 */
export const getStatisticalData = () => {
  return request({
    url: '/statistical/data',
    method: 'get'
  })
};

/**
 * 添加网关路由服务
 */
export const getCoordinateData = () => {
  return request({
    url: '/statistical/coordinate',
    method: 'get'
  })
};
