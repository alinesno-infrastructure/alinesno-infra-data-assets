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

      <!-- 入参列表（使用 prop 绑定自定义 validator） -->
      <el-form-item label="入参" prop="params">
        <div class="params-list">
          <div v-for="(p, idx) in local.params" :key="p.__uid" class="param-row">
            <el-row :gutter="8" align="middle">
              <el-col :span="6">
                <el-input v-model="p.name" placeholder="参数名 (name)" clearable />
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
                <el-input v-model="p.description" placeholder="说明" clearable />
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

      <!-- 你可以在表单底部放一个提交按钮以触发校验 
      <div style="margin-top:12px;">
        <el-button type="primary" @click="handleSubmit">保存并校验</el-button>
      </div>
       -->

    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref, watch, toRaw, onMounted } from 'vue'
import { listAllDatasourceConfig } from '@/api/data/asset/datasource'

const props = defineProps({
  modelValue: {
    type: Object,
    default: () => ({
      id: '',
      name: '',
      description: '',
      scriptType: 'groovy',
      enabled: true,
      params: []
    })
  }
})
const emit = defineEmits(['update:modelValue'])

const dataSourceArr = ref([])
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

function normalizeModel(input) {
  const base = {
    id: '',
    name: '',
    description: '',
    scriptType: 'groovy',
    enabled: true,
    params: []
  }
  const merged = Object.assign({}, base, deepClone(input || {}))
  merged.params = Array.isArray(merged.params) ? merged.params.map(p => {
    const item = Object.assign({ name: '', type: 'string', required: false, description: '' }, p || {})
    if (!item.__uid) item.__uid = genUid()
    return item
  }) : []
  return merged
}

const local = reactive(normalizeModel(props.modelValue))

watch(
  () => props.modelValue,
  (nv) => {
    const n = normalizeModel(nv)
    Object.assign(local, n)
  },
  { deep: true }
)

watch(
  local,
  (nv) => {
    const out = deepClone(toRaw(nv))
    if (Array.isArray(out.params)) {
      out.params = out.params.map(p => {
        const { __uid, ...rest } = p || {}
        return rest
      })
    }
    emit('update:modelValue', out)
  },
  { deep: true }
)

// 表单 ref
const formRef = ref(null)

// 自定义 params 校验器
function validateParams(rule, value, callback) {
  const params = local.params || []
  if (!Array.isArray(params) || params.length === 0) {
    // 如果允许空参数，把下面一行改为 callback(); 这里示例要求至少一个参数可调整
    return callback() // 允许空数组，若需不允许请改为 callback(new Error('至少需要一个入参'))
  }

  const namePattern = /^[A-Za-z_][A-Za-z0-9_]*$/
  const names = params.map(p => (p && p.name ? String(p.name).trim() : ''))
  // 非空检查 & 格式检查 & type 检查
  for (let i = 0; i < params.length; i++) {
    const p = params[i]
    const n = names[i]
    if (!n) return callback(new Error(`第 ${i + 1} 个参数名称不能为空`))
    if (!namePattern.test(n)) return callback(new Error(`第 ${i + 1} 个参数名称不合法，仅允许字母、数字、下划线，且不能以数字开头`))
    if (!p.type) return callback(new Error(`第 ${i + 1} 个参数类型不能为空`))
  }
  // 唯一性检查
  const dup = names.find((v, idx) => names.indexOf(v) !== idx)
  if (dup) return callback(new Error(`参数名 "${dup}" 重复，请保证参数名唯一`))

  callback()
}

// rules 定义
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

// 提交示例：手动触发表单校验
function handleSubmit() {
  if (!formRef.value) return
  // callback 方式
  formRef.value.validate((valid, fields) => {
    if (valid) {
      // 校验通过，执行保存逻辑
      console.log('表单校验通过，准备保存：', JSON.parse(JSON.stringify(local)))
    } else {
      console.warn('校验失败：', fields)
      // Element 会自动显示错误信息
    }
  })
  // 也可以使用 promise 风格（视 Element Plus 版本）：
  // formRef.value.validate().then(() => { ... }).catch(() => { ... })
}

onMounted(() => {
  listAllDatasourceConfig().then(res => {
    dataSourceArr.value = res.data;
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