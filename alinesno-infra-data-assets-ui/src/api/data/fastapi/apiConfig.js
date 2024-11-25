import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 * 
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/data/fastapi/apiConfig/' ;
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
    catalogTreeSelect: prefix + "catalogTreeSelect",
    downloadfile: prefix + "downloadfile",
    updateExecuteSql: prefix + "updateExecuteSql",
    getApi: prefix + "getApi",
    validateApiScript: prefix + "validateApiScript",
    updateApiScript: prefix + "updateApiScript",
}

// 获取api接口
export function getApi(id) {
  return request({
    url: managerUrl.getApi + "?id=" + parseStrEmpty(id) ,
    method: 'get'
  })
}

// 校验api脚本
export function validateApiScript(data) {
  return request({
    url: managerUrl.validateApiScript ,
    method: 'post',
    data: data
  })
}

// 更新api脚本
export function updateApiScript(data , id){
  return request({
    url: managerUrl.updateApiScript + "?id=" + parseStrEmpty(id) ,
    method: 'post',
    data: data
  })
}


// 更新api执行sql
export function updateExecuteSql(data , id){
  return request({
    url: managerUrl.updateExecuteSql + "?id=" + parseStrEmpty(id) ,
    method: 'post',
    data: data
  })
}

// 修改字段
export function changStatusField(data){
  return request({
    url: managerUrl.changeField ,
    method: 'post',
    data: data
  })
}

// 查询部门下拉树结构
export function catalogTreeSelect() {
  return request({
    url: managerUrl.catalogTreeSelect , 
    method: 'get'
  })
}

// 查询数据库列表
export function listApiConfig(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询数据库详细
export function getApiConfig(databaseId) {
  return request({
    url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
    method: 'get'
  })
}

// 新增数据库
export function addApiConfig(data) {
  return request({
    url: managerUrl.saveUrl ,
    method: 'post',
    data: data
  })
}

// 修改数据库
export function updateApiConfig(data) {
  return request({
    url: managerUrl.updateUrl ,
    method: 'put',
    data: data
  })
}

// 删除数据库
export function delApiConfig(databaseId) {
  return request({
    url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
    method: 'delete'
  })
}
