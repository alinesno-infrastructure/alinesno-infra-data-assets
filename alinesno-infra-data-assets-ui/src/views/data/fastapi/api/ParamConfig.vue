<template>
  <div class="panel-card">
    <div class="panel-header">接口配置</div>

    <el-form ref="formRef" :model="local" :rules="rules" label-width="80px" size="large" label-position="right" class="param-form">

      <!-- 脚本类型 -->
      <el-form-item label="脚本类型">
        <el-radio-group v-model="local.scriptType" style="width: 100%;">
          <el-radio label="groovy">Groovy</el-radio>
          <el-radio label="sql">SQL</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 数据源 -->
      <el-form-item label="数据源" prop="datasourceId">
        <el-select v-model="local.datasourceId" placeholder="选择数据源" style="width: 100%;">
          <el-option :label="item.name" :value="item.id" :key="item.id" v-for="item in dataSourceArr" />
        </el-select>
      </el-form-item>

      <!-- 接口名称 -->
      <el-form-item label="接口名称" prop="name">
        <el-input v-model="local.name" placeholder="接口名称（示例：user.get）" />
      </el-form-item>

      <!-- 描述 -->
      <el-form-item label="接口说明">
        <el-input type="textarea" :rows="2" resize="none" v-model="local.description" placeholder="接口的用途或说明" />
      </el-form-item>

      <!-- 启停 -->
      <el-form-item label="接口状态">
        <el-switch v-model="local.enabled" active-text="已启用" inactive-text="已关闭" />
      </el-form-item>

      <!-- 接口路径 -->
      <el-form-item label="接口路径" prop="path">
        <el-input v-model="local.path" placeholder="接口路径（示例：/api/user/get）" />
      </el-form-item>

      <!-- 入参列表（单向模式，父通过 ref 获取） -->
      <el-form-item label="入参" prop="params">
        <div class="params-list">
          <div v-for="(p, idx) in local.params" :key="p.__uid" class="param-row">
            <el-row :gutter="8" align="middle">
              <el-col :span="6">
                <el-input v-model="p.name" placeholder="参数名 (name)"  />
              </el-col>

              <el-col :span="6">
                <el-select v-model="p.type" placeholder="类型" style="width: 100%;">
                  <el-option label="string" value="string" />
                  <el-option label="number" value="number" />
                  <el-option label="boolean" value="boolean" />
                  <el-option label="object" value="object" />
                  <el-option label="array" value="array" />
                </el-select>
              </el-col>

              <el-col :span="3">
                <el-checkbox v-model="p.required">必填</el-checkbox>
              </el-col>

              <el-col :span="6">
                <el-input v-model="p.description" placeholder="说明"  />
              </el-col>

              <el-col :span="3" class="param-actions">
                <el-tooltip content="删除" placement="top">
                  <el-button type="danger" icon="Delete" circle @click="removeParam(idx)" />
                </el-tooltip>
              </el-col>
            </el-row>
          </div>

          <div class="param-actions-row">
            <el-button type="primary" plain size="large" icon="Plus" @click="addParam">
              添加参数
            </el-button>
            <el-button type="warning" plain size="large" @click="resetParams" style="margin-left:8px;">
              重置示例
            </el-button>
          </div>
        </div>
      </el-form-item>

    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref, toRaw, onMounted } from 'vue'
import { listAllDatasourceConfig } from '@/api/data/asset/datasource'

/**
 * 单向组件（不接收 props）
 * 暴露的方法（通过 ref 调用）：
 *  - setConfig(obj)    // 设置配置（会尽量保留 params 的 __uid，做原地更新）
 *  - getConfig()       // 获取当前配置（返回不带 __uid 的纯净对象）
 *  - validate()        // 返回 Promise，表单校验
 */

const dataSourceArr = ref([])

// __uid 生成
let uidSeed = 1
function genUid() {
  return Date.now().toString(36) + '-' + (uidSeed++)
}

function deepClone(obj) {
  try {
    return JSON.parse(JSON.stringify(obj))
  } catch (e) {
    return obj
  }
}

// 默认模型
function defaultModel() {
  return {
    id: '',
    name: '',
    description: '',
    scriptType: 'groovy',
    enabled: true,
    params: [],
    path: '',
    datasourceId: ''
  }
}

// 内部响应式状态（父通过 setConfig 设置）
const local = reactive(defaultModel())

// 表单 ref
const formRef = ref(null)

// helpers：在保持数组引用的前提下，用 incoming 更新 target（并尽量保留已有 __uid）
// 匹配策略：优先按 name 去找到旧项来保留 __uid，否则按索引匹配，最后生成新 __uid
function updateParamsInPlace(targetArr, incoming = []) {
  if (!Array.isArray(incoming)) incoming = []
  // 简单映射 existing by name
  const existingByName = {}
  for (let i = 0; i < targetArr.length; i++) {
    const e = targetArr[i]
    if (e && e.name) existingByName[e.name] = e.__uid
  }

  const mapped = incoming.map((p, idx) => {
    const base = Object.assign({ name: '', type: 'string', required: false, description: '' }, p || {})
    // try reuse uid by name
    let uid = null
    if (base.name && existingByName[base.name]) uid = existingByName[base.name]
    // else try reuse by index
    if (!uid && targetArr[idx] && targetArr[idx].__uid) uid = targetArr[idx].__uid
    if (!uid) uid = genUid()
    return Object.assign({ __uid: uid }, base)
  })

  // 原地修改数组内容（保持同一个 array 引用）
  targetArr.splice(0, targetArr.length, ...mapped)
}

// setConfig: 父通过 ref 调用设置数据
function setConfig(cfg = {}) {
  const normalized = Object.assign({}, defaultModel(), deepClone(cfg || {}))
  // 直接赋值基础字段（保持 local 对象引用）
  local.id = normalized.id || ''
  local.name = normalized.name || ''
  local.description = normalized.description || ''
  local.scriptType = normalized.scriptType || 'groovy'
  local.enabled = normalized.enabled == null ? true : normalized.enabled
  local.path = normalized.path || ''
  local.datasourceId = normalized.datasourceId || ''

  // 更新 params 原地替换并保留 __uid
  updateParamsInPlace(local.params, normalized.params)
}

// getConfig: 返回纯净对象（不带 __uid）
function getConfig() {
  const out = deepClone(toRaw(local))
  if (Array.isArray(out.params)) {
    out.params = out.params.map(p => {
      const { __uid, ...rest } = p || {}
      return rest
    })
  }
  console.log('getConfig', out)
  return out
}

// validate: 返回 Promise，方便父组件调用
function validate() {
  if (!formRef.value) return Promise.resolve(true)
  return new Promise((resolve, reject) => {
    formRef.value.validate((valid, fields) => {
      if (valid) resolve(true)
      else reject(fields)
    })
  })
}

// 参数操作
function addParam() {
  local.params.push({
    __uid: genUid(),
    name: '',
    type: 'string',
    required: false,
    description: ''
  })
}

function removeParam(index) {
  if (index >= 0 && index < local.params.length) {
    local.params.splice(index, 1)
  }
}

function resetParams() {
  local.params.splice(0, local.params.length,
    {
      __uid: genUid(),
      name: 'id',
      type: 'number',
      required: true,
      description: '主键 id'
    },
    {
      __uid: genUid(),
      name: 'name',
      type: 'string',
      required: false,
      description: '用户名'
    }
  )
}

// 暴露给父组件的方法
defineExpose({
  setConfig,
  getConfig,
  validate,
  addParam,
  removeParam,
  resetParams
})

// 校验规则（保持原有校验器）
function validateParams(rule, value, callback) {
  const params = local.params || []
  if (!Array.isArray(params) || params.length === 0) {
    return callback()
  }

  const namePattern = /^[A-Za-z_][A-Za-z0-9_]*$/
  const names = params.map(p => (p && p.name ? String(p.name).trim() : ''))
  for (let i = 0; i < params.length; i++) {
    const p = params[i]
    const n = names[i]
    if (!n) return callback(new Error(`第 ${i + 1} 个参数名称不能为空`))
    if (!namePattern.test(n)) return callback(new Error(`第 ${i + 1} 个参数名称不合法，仅允许字母、数字、下划线，且不能以数字开头`))
    if (!p.type) return callback(new Error(`第 ${i + 1} 个参数类型不能为空`))
  }
  const dup = names.find((v, idx) => names.indexOf(v) !== idx)
  if (dup) return callback(new Error(`参数名 "${dup}" 重复，请保证参数名唯一`))

  callback()
}

const rules = reactive({
  name: [
    { required: true, message: '请输入接口名称', trigger: 'blur' },
    { min: 2, max: 80, message: '接口名称长度 2-80 个字符', trigger: 'blur' }
  ],
  path: [
    { required: true, message: '请输入接口路径', trigger: 'blur' },
    { pattern: /^\/[A-Za-z0-9\/\-_]*$/, message: '路径需以 / 开头，允许字母数字、/、-、_', trigger: 'blur' }
  ],
  datasourceId: [
    { required: true, message: '请选择数据源', trigger: 'change' }
  ],
  params: [
    { validator: validateParams, trigger: 'change' }
  ]
})

// 初始加载数据源
onMounted(() => {
  listAllDatasourceConfig().then(res => {
    dataSourceArr.value = res.data || []
  }).catch(() => {
    dataSourceArr.value = []
  })
})
</script>

<style lang="scss" scoped>
.panel-card {
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 6px;
  padding: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.04);
  margin: 10px;
  margin-right: 20px;
  margin-top: 20px;
  margin-left: 0px;
}

.panel-header {
  font-weight: 600;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f2f6;
  margin-bottom: 12px;
}

.param-form {
  width: 100%;
}

.params-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.param-row {
  padding: 8px;
  border-radius: 6px;
  background: #fafafa;
  border: 1px solid #f5f7fa;
}

.param-actions {
  display: flex;
  align-items: center;
  justify-content: center;
}

.param-actions-row {
  margin-top: 8px;
}
</style>