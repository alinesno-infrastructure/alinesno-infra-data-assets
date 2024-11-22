import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 *
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/data/assets/assetData/' ;
var managerUrl = {
    datatables : prefix +"datatables" ,
    createUrl: prefix + 'add' ,
    saveUrl: prefix + 'save' ,
    updateUrl: prefix +"modify" ,
    statusUrl: prefix +"changeStatus" ,
    cleanUrl: prefix + "clean",
    detailUrl: prefix +"detail",
    removeUrl: prefix + "delete" ,
    exportUrl: prefix + "exportExcel",
    changeField: prefix + "changeField",
    downloadfile: prefix + "downloadfile",
    updateAssetDataContent: prefix + "updateAssetDataContent",
    catalogTreeSelect: prefix + "catalogTreeSelect",
    catalogManifestTreeSelect: prefix + "catalogManifestTreeSelect",
    getAssetDataContent: prefix + "getAssetDataContent",
}

// 查询清单下拉树结构
export function catalogManifestTreeSelect() {
    return request({
        url: managerUrl.catalogManifestTreeSelect ,
        method: 'get'
    })
}

// 查询部门下拉树结构
export function catalogTreeSelect() {
    return request({
        url: managerUrl.catalogTreeSelect ,
        method: 'get'
    })
}

// 列新AssetDataContent
export function updateAssetDataContent(data , postId) {
    return request({
        url: managerUrl.updateAssetDataContent + '?postId=' + postId,
        method: 'post',
        data: data
    })
}

// 查询数据库列表
export function listAssetData(query) {
    return request({
        url: managerUrl.datatables ,
        method: 'post',
        params: query
    })
}

// 获取AssetDataContent内容
export function getAssetDataContent(postId) {
    return request({
        url: managerUrl.getAssetDataContent+ '?postId=' + parseStrEmpty(postId),
        method: 'get'
    })
}

// 查询数据库详细
export function getAssetData(databaseId) {
    return request({
        url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
        method: 'get'
    })
}

// 新增数据库
export function addAssetData(data) {
    return request({
        url: managerUrl.saveUrl ,
        method: 'post',
        data: data
    })
}

// 修改数据库
export function updateAssetData(data) {
    return request({
        url: managerUrl.updateUrl ,
        method: 'put',
        data: data
    })
}

// 删除数据库
export function delAssetData(databaseId) {
    return request({
        url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
        method: 'delete'
    })
}
