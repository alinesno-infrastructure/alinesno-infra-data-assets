import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 *
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/data/assets/catalog/' ;
var managerUrl = {
    datatables : prefix +"datatables" ,
    createUrl: prefix + 'add' ,
    saveUrl: prefix + 'insertCatalog' ,
    updateUrl: prefix +"modify" ,
    statusUrl: prefix +"changeStatus" ,
    cleanUrl: prefix + "clean",
    detailUrl: prefix +"detail",
    removeUrl: prefix + "delete" ,
    exportUrl: prefix + "exportExcel",
    changeField: prefix + "changeField",
    list: prefix + "list",
    excludeChild: prefix + "excludeChild",
    downloadfile: prefix + "downloadfile"
}

// 查询部门列表（排除节点）
export function listCatalogExcludeChild(deptId) {
    return request({
        url: managerUrl.excludeChild+ '/' + deptId ,
        method: 'get'
    })
}

// 查询数据库列表
export function listCatalog(query) {
    return request({
        url: managerUrl.list,
        method: 'get',
        params: query
    })
}

// 查询数据库列表
export function datatableCatalog(query) {
    return request({
        url: managerUrl.datatables ,
        method: 'post',
        params: query
    })
}

// 查询数据库详细
export function getCatalog(databaseId) {
    return request({
        url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
        method: 'get'
    })
}

// 新增数据库
export function addCatalog(data) {
    return request({
        url: managerUrl.saveUrl ,
        method: 'post',
        data: data
    })
}

// 修改数据库
export function updateCatalog(data) {
    return request({
        url: managerUrl.updateUrl ,
        method: 'put',
        data: data
    })
}

// 删除数据库
export function delCatalog(databaseId) {
    return request({
        url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
        method: 'delete'
    })
}
