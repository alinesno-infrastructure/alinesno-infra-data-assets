<template>
  <div class="app-container">

      <div style="margin-bottom:20px;">
         <BackUpCompoent :url="'/global/config'" :title="'元数据配置'"  />
      </div>

    <el-row :gutter="20">
      <!-- 搜索区域 -->
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
          <el-form-item label="数据源名称" prop="name">
            <el-input v-model="queryParams.name" placeholder="请输入名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="数据源类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择类型" clearable style="width: 200px">
              <el-option label="MySQL" value="mysql" />
              <el-option label="PostgreSQL" value="postgresql" />
              <el-option label="ClickHouse" value="clickhouse" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!-- 操作按钮区域 -->
      <el-col :span="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="20">
            <el-button type="primary" icon="Plus" @click="handleAdd">新增数据源</el-button>
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete">删除选中</el-button>
          </el-col>
          <el-col :span="4" style="display: flex;flex-direction: row;justify-content: flex-end;">
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
          </el-col>
        </el-row>
      </el-col>

      <!-- 数据源列表 -->
      <el-col :span="24">
        <el-table v-loading="loading" :data="datasourceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="ID" prop="id" width="180" align="center" v-if="columns[0].visible" />
          <el-table-column label="数据源名称" prop="name" align="left" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="数据库类型" prop="type" align="center" v-if="columns[2].visible" width="120">
            <template #default="scope">
              <el-tag v-if="scope.row.type === 'mysql'" type="primary">MySQL</el-tag>
              <el-tag v-if="scope.row.type === 'postgresql'" type="success">PostgreSQL</el-tag>
              <el-tag v-if="scope.row.type === 'clickhouse'" type="warning">ClickHouse</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="连接地址" prop="url" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="用户名" prop="username" align="center" v-if="columns[4].visible" width="120" />
          <el-table-column label="状态" align="center" v-if="columns[5].visible" width="100">
            <template #default="scope">
              <el-tag type="success">已配置</el-tag>
            </template>
          </el-table-column>
        <el-table-column label="最后同步时间" align="center" prop="lastSyncTime" v-if="columns[6].visible" width="220">
            <template #default="scope">
                <span>{{ parseTime(scope.row.lastSyncTime) }}</span>
            </template>
        </el-table-column>
          <el-table-column label="操作" align="center" width="280" v-if="columns[7].visible">
            <template #default="scope">
              <el-button type="text" icon="Edit" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="text" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
              <el-button type="text" icon="RefreshRight" @click="handleSync(scope.row)">立即同步</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>

    <!-- 新增/编辑数据源弹窗 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="140px" size="large">
        <el-form-item label="数据源名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入数据源名称（如：业务数据库）" max-length="50" />
        </el-form-item>
        <el-form-item label="数据库类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择数据库类型" required>
            <el-option label="MySQL" value="mysql" />
            <el-option label="PostgreSQL" value="postgresql" />
            <el-option label="ClickHouse" value="clickhouse" />
          </el-select>
        </el-form-item>
        <el-form-item label="JDBC连接地址" prop="url">
          <div style="display: flex">
            <el-input v-model="form.url" placeholder="如：jdbc:mysql://localhost:3306/dbname" style="flex: 1" max-length="255" />
            <el-button type="primary" @click="handleTestConnection" :loading="testing" style="margin-left: 10px">测试连接</el-button>
          </div>
          <div class="form-tip" style="margin-top: 5px">
            <el-tag v-if="testResult === 'success'" type="success">连接成功</el-tag>
            <el-tag v-if="testResult === 'error'" type="danger">{{ testMsg }}</el-tag>
            <span v-else style="color: #999">未测试连接</span>
          </div>
        </el-form-item>
        <el-form-item label="数据库用户名" prop="username">
          <el-input v-model="form.username" placeholder="数据库登录用户名" />
        </el-form-item>
        <el-form-item label="数据库密码" prop="password">
          <el-input v-model="form.password" placeholder="数据库登录密码" type="password" show-password />
        </el-form-item>
        <el-form-item label="连接超时（毫秒）" prop="connectionTimeout">
          <el-input-number v-model="form.connectionTimeout" :min="1000" :step="1000" :max="60000" />
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
          <el-input v-model="form.remark" type="textarea" rows="3" placeholder="可选备注信息" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { 
    datatableDatasourceConfig, 
    delDatasourceConfig, 
    getDatasourceConfig, 
    syncNowDatasource,
    addDatasourceConfig, 
    updateDatasourceConfig, 
    probeDatasourceConfig 
} from '@/api/data/asset/datasource'

const { proxy } = getCurrentInstance()
const router = useRouter()

// 列表数据
const datasourceList = ref([])
const loading = ref(false)
const total = ref(0)
const showSearch = ref(true)
const columns = ref([
  { key: 0, label: 'ID', visible: true },
  { key: 1, label: '数据源名称', visible: true },
  { key: 2, label: '数据库类型', visible: true },
  { key: 3, label: '连接地址', visible: true },
  { key: 4, label: '用户名', visible: true },
  { key: 5, label: '状态', visible: true },
  { key: 6, label: '最后同步时间', visible: true },
  { key: 7, label: '操作', visible: true }
])

// 选择项
const ids = ref([])
const single = ref(true)
const multiple = ref(true)

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  name: '',
  type: ''
})

// 弹窗相关
const open = ref(false)
const title = ref('')
const formRef = ref(null)
const form = reactive({
  id: null,
  name: '',
  type: 'mysql',
  url: '',
  username: '',
  password: '',
  connectionTimeout: 30000,
  remark: ''
})
const rules = reactive({
  name: [{ required: true, message: '请输入数据源名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择数据库类型', trigger: 'change' }],
  url: [{ required: true, message: '请输入JDBC连接地址', trigger: 'blur' }],
  username: [{ required: true, message: '请输入数据库用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入数据库密码', trigger: 'blur' }],
  connectionTimeout: [{ required: true, message: '请输入连接超时时间', trigger: 'blur' }]
})

// 连接测试相关
const testing = ref(false)
const testResult = ref('') // 'success' | 'error' | ''
const testMsg = ref('')
const submitting = ref(false)

// 初始化列表
onMounted(() => {
  getList()
})

// 获取数据源列表
const getList = () => {
  loading.value = true
  datatableDatasourceConfig(queryParams.value).then(res => {
    datasourceList.value = res.rows
    total.value = res.total
    loading.value = false
  }).catch(() => {
    loading.value = false
  })
}

// 新增
const handleAdd = () => {
  open.value = true
  title.value = '新增数据源'
  resetForm()
}

// 编辑
const handleEdit = (row) => {
  open.value = true
  title.value = '编辑数据源'
  resetForm()
  getDatasourceConfig(row.id).then(res => {
    Object.assign(form, res.data)
  })
}

// 删除
const handleDelete = (row) => {
  const idsToDelete = row ? [row.id] : ids.value
  proxy.$modal.confirm(`确定要删除选中的 ${idsToDelete.length} 个数据源吗？`).then(() => {
    return delDatasourceConfig(idsToDelete)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess('删除成功')
  }).catch(() => {})
}

// 测试连接
const handleTestConnection = () => {
  if (!form.url || !form.username || !form.password || !form.type) {
    proxy.$modal.msgWarning('请先填写完整连接信息')
    return
  }
  testing.value = true
  testResult.value = ''
  probeDatasourceConfig({
    url: form.url,
    username: form.username,
    password: form.password,
    type: form.type
  }).then(res => {
    if (res.code === 200) {
      testResult.value = 'success'
      proxy.$modal.msgSuccess('连接测试成功')
    } else {
      testResult.value = 'error'
      testMsg.value = res.msg || '连接失败'
    }
  }).catch(err => {
    testResult.value = 'error'
    testMsg.value = '连接测试失败：' + (err.msg || err.message || '网络异常')
  }).finally(() => {
    testing.value = false
  })
}

// 保存提交
const handleSubmit = () => {
  formRef.value.validate(valid => {
    if (!valid) return
    if (testResult.value !== 'success') {
      proxy.$modal.msgWarning('请先测试并确保连接成功')
      return
    }
    submitting.value = true
    const saveFunc = form.id ? updateDatasourceConfig : addDatasourceConfig
    saveFunc(form).then(res => {
      proxy.$modal.msgSuccess(form.id ? '更新成功' : '新增成功')
      open.value = false
      getList()
    }).catch(() => {}).finally(() => {
      submitting.value = false
    })
  })
}

// 立即同步（跳转到同步任务或直接触发同步）
const handleSync = (row) => {
  proxy.$modal.confirm(`确定要立即同步数据源【${row.name}】吗？`).then(() => {
    proxy.$modal.loading(`正在同步【${row.name}】，请稍候...`)
    return syncNowDatasource(row.id)
  }).then(res => {
    proxy.$modal.closeLoading()
    proxy.$modal.msgSuccess(res.msg || '同步任务已触发')
    getList()
  }).catch(() => {
    proxy.$modal.closeLoading()
  })
}

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields()
  Object.assign(form, {
    id: null,
    name: '',
    type: 'mysql',
    url: '',
    username: '',
    password: '',
    connectionTimeout: 30000,
    remark: ''
  })
  testResult.value = ''
  testMsg.value = ''
}

// 取消
const cancel = () => {
  open.value = false
  resetForm()
}

// 选择项变化
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

// 查询和重置
const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}
const resetQuery = () => {
  queryParams.value = { pageNum: 1, pageSize: 10, name: '', type: '' }
  getList()
}
</script>

<style scoped>
.form-tip {
  font-size: 12px;
}
</style>