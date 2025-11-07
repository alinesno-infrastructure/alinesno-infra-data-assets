import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 元数据配置接口操作
 *
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/data/assets/datasourceConfig/' ;
var managerUrl = {
    datatables : prefix +"datatables" ,
    createUrl: prefix + 'add' ,
    saveUrl: prefix + 'insertDatasourceConfig' ,
    updateUrl: prefix +"modify" ,
    statusUrl: prefix +"changeStatus" ,
    cleanUrl: prefix + "clean",
    detailUrl: prefix +"detail",
    removeUrl: prefix + "delete" ,
    exportUrl: prefix + "exportExcel",
    changeField: prefix + "changeField",
    list: prefix + "list",
    getCurrentDatasourceConfig: prefix + "getCurrentDatasourceConfig",
    probeDatasourceConfig: prefix + "probe",
    excludeChild: prefix + "excludeChild",
    downloadfile: prefix + "downloadfile"
}

// 获取当前数据源配置
export function getCurrentDatasourceConfig() {
    return request({
        url: managerUrl.getCurrentDatasourceConfig ,
        method: 'get'
    })
}

// 检测数据源连接
export function probeDatasourceConfig(params) {
    return request({
        url: managerUrl.probeDatasourceConfig ,
        method: 'get',
        params
    })
}

// 查询部门列表（排除节点）
export function listDatasourceConfigExcludeChild(deptId) {
    return request({
        url: managerUrl.excludeChild+ '/' + deptId ,
        method: 'get'
    })
}

// 查询元数据配置列表
export function listDatasourceConfig(query) {
    return request({
        url: managerUrl.list,
        method: 'get',
        params: query
    })
}

// 查询元数据配置列表
export function datatableDatasourceConfig(query) {
    return request({
        url: managerUrl.datatables ,
        method: 'post',
        params: query
    })
}

// 查询元数据配置详细
export function getDatasourceConfig(databaseId) {
    return request({
        url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
        method: 'get'
    })
}

// 新增元数据配置
export function addDatasourceConfig(data) {
    return request({
        url: managerUrl.saveUrl ,
        method: 'post',
        data: data
    })
}

// 修改元数据配置
export function updateDatasourceConfig(data) {
    return request({
        url: managerUrl.updateUrl ,
        method: 'put',
        data: data
    })
}

// 删除元数据配置
export function delDatasourceConfig(databaseId) {
    return request({
        url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
        method: 'delete'
    })
}
