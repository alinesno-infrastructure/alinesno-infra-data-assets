import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

/**
 * API 配置相关接口
 */

// 基础前缀
const prefix = '/api/infra/data/fastapi/apiConfig/';
const managerUrl = {
  datatables: prefix + "datatables",
  createUrl: prefix + 'add',
  // 保持旧字段兼容（如项目其它位置使用），但新增新的保存/更新接口
  saveUrl: prefix + 'saveConfigInfo',
  saveApiConfig: prefix + 'saveApiConfig',
  updateApiConfig: prefix + 'updateApiConfig',
  updateUrl: prefix + "modify",
  statusUrl: prefix + "changeStatus",
  cleanUrl: prefix + "clean",
  detailUrl: prefix + "detail",
  removeUrl: prefix + "delete",
  exportUrl: prefix + "exportExcel",
  changeField: prefix + "changeEnableField",
  catalogTreeSelect: prefix + "catalogTreeSelect",
  downloadfile: prefix + "downloadfile",
  updateExecuteSql: prefix + "updateExecuteSql",
  getApi: prefix + "getApi",
  validateApiScript: prefix + "validateApiScript",
  updateApiScript: prefix + "updateApiScript",
}

// 获取 api 接口信息
export function getApi(id) {
  return request({
    url: managerUrl.getApi + "?id=" + parseStrEmpty(id),
    method: 'get'
  })
}

// 校验 api 脚本
export function validateApiScript(data) {
  return request({
    url: managerUrl.validateApiScript,
    method: 'post',
    data: data
  })
}

// 更新 api 脚本（POST body: {script, apiId, type, params}）
export function updateApiScript(data) {
  return request({
    url: managerUrl.updateApiScript,
    method: 'post',
    data: data
  })
}

// 更新 api 执行 sql（保持现有）
export function updateExecuteSql(data, id) {
  return request({
    url: managerUrl.updateExecuteSql + "?id=" + parseStrEmpty(id),
    method: 'post',
    data: data
  })
}

// 修改字段
export function changStatusField(data) {
  return request({
    url: managerUrl.changeField,
    method: 'post',
    data: data
  })
}

// 查询部门下拉树结构
export function catalogTreeSelect() {
  return request({
    url: managerUrl.catalogTreeSelect,
    method: 'get'
  })
}

// 查询列表
export function listApiConfig(query) {
  return request({
    url: managerUrl.datatables,
    method: 'post',
    params: query
  })
}

// 查询详细（兼容）
export function getApiConfig(databaseId) {
  return request({
    url: managerUrl.detailUrl + '/' + parseStrEmpty(databaseId),
    method: 'get'
  })
}

// 新增/保存接口配置（后端 saveApiConfig，会根据 id 判断新增/更新）
export function saveApiConfig(data) {
  return request({
    url: managerUrl.saveApiConfig,
    method: 'post',
    data: data
  })
}

// 更新接口配置（可直接调用 updateApiConfig 或 saveApiConfig）
export function updateApiConfig(data) {
  return request({
    url: managerUrl.updateApiConfig,
    method: 'post',
    data: data
  })
}

// 兼容旧 saveUrl（如果后端另有实现）
export function addApiConfig(data) {
  return request({
    url: managerUrl.saveUrl,
    method: 'post',
    data: data
  })
}

// 删除
export function delApiConfig(databaseId) {
  return request({
    url: managerUrl.removeUrl + '/' + parseStrEmpty(databaseId),
    method: 'delete'
  })
}