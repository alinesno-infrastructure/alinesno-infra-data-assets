import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 *
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/data/assets/manifestField/' ;
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
    updateManifestFieldContent: prefix + "updateManifestFieldContent",
    catalogTreeSelect: prefix + "catalogTreeSelect",
    getManifestFieldContent: prefix + "getManifestFieldContent",
    getManifestFieldByMId: prefix + "getManifestFieldByMId",
    updateManifestFieldByMId: prefix + "updateManifestFieldByMId",
}

// 更新表结构
export function updateManifestFieldByMId(mId , data) {
    return request({
        url: managerUrl.updateManifestFieldByMId+ "?mId=" + parseStrEmpty(mId) ,
        method: 'post',
        data: data
    })
} 

// 获取ManifestField 
export function getManifestFieldByMId(mId) {
    return request({
        url: managerUrl.getManifestFieldByMId+ "?mId=" + parseStrEmpty(mId),
        method: 'get',
    })
}

// 查询部门下拉树结构
export function catalogTreeSelect() {
    return request({
        url: managerUrl.catalogTreeSelect ,
        method: 'get'
    })
}

// 列新ManifestFieldContent
export function updateManifestFieldContent(data , postId) {
    return request({
        url: managerUrl.updateManifestFieldContent + '?postId=' + postId,
        method: 'post',
        data: data
    })
}

// 查询数据库列表
export function listManifestField(query) {
    return request({
        url: managerUrl.datatables ,
        method: 'post',
        params: query
    })
}

// 获取ManifestFieldContent内容
export function getManifestFieldContent(postId) {
    return request({
        url: managerUrl.getManifestFieldContent+ '?postId=' + parseStrEmpty(postId),
        method: 'get'
    })
}

// 查询数据库详细
export function getManifestField(databaseId) {
    return request({
        url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
        method: 'get'
    })
}

// 新增数据库
export function addManifestField(data) {
    return request({
        url: managerUrl.saveUrl ,
        method: 'post',
        data: data
    })
}

// 修改数据库
export function updateManifestField(data) {
    return request({
        url: managerUrl.updateUrl ,
        method: 'put',
        data: data
    })
}

// 删除数据库
export function delManifestField(databaseId) {
    return request({
        url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
        method: 'delete'
    })
}
