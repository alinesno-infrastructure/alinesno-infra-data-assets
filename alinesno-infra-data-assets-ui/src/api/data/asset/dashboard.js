import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 *
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/data/assets/dashboard/' ;

var managerUrl = {
    topCatalog : prefix +"topCatalog" ,
    getApiKey: prefix +"getApiKey" ,
    getAssetStatus: prefix +"getAssetStatus" ,
}

// 查询资产状态
export function getAssetStatus() {
    return request({
        url: managerUrl.getAssetStatus,
        method: 'get'
    })
}

// 获取apikey
export function getApiKey() {
    return request({
        url: managerUrl.getApiKey,
        method: 'get'
    })
}

// 查询部门下拉树结构
export function topCatalog() {
    return request({
        url: managerUrl.topCatalog,
        method: 'get'
    })
}

