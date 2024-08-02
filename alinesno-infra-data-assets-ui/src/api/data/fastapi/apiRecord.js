import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * 数据库接口操作
 * 
 * @author luoxiaodong
 * @since 1.0.0
 */

// 接口配置项
var prefix = '/api/infra/data/fastapi/api_record/' ;
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
    downloadfile: prefix + "downloadfile"
}

// 查询操作日志列表
export function list(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 删除操作日志
export function delOperlog(operId) {
  return request({
    url: managerUrl.cleanUrl + '/' + operId ,
    method: 'delete'
  })
}

// 清空操作日志
export function cleanOperlog() {
  return request({
    url: managerUrl.cleanUrl ,
    method: 'delete'
  })
}

// // 查询数据库列表
// export function listApplication(query) {
//   return request({
//     url: managerUrl.datatables ,
//     method: 'post',
//     params: query
//   })
// }

// // 查询数据库详细
// export function getApplication(databaseId) {
//   return request({
//     url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
//     method: 'get'
//   })
// }

// // 新增数据库
// export function addApplication(data) {
//   return request({
//     url: managerUrl.saveUrl ,
//     method: 'post',
//     data: data
//   })
// }

// // 修改数据库
// export function updateApplication(data) {
//   return request({
//     url: managerUrl.updateUrl ,
//     method: 'put',
//     data: data
//   })
// }

// // 删除数据库
// export function delApplication(databaseId) {
//   return request({
//     url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
//     method: 'delete'
//   })
// }
