<template>
    <div class="app-container">
        <div class="label-title">
            <div class="tip">数据源配置</div>
            <div class="sub-tip">填写数据库连接信息并保存（仅支持 1 个数据源）</div>
        </div>

        <div class="form-container">
            <el-form :model="form" :rules="rules" ref="formRef" label-width="160px" size="large"
                class="datasource-form">

                <el-form-item label="数据源名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入数据源名称" />
                </el-form-item>

                <el-form-item label="数据源类型" prop="type">
                    <el-select v-model="form.type" placeholder="请选择类型">
                        <el-option label="MySQL" value="mysql" />
                        <el-option label="PostgreSQL" value="postgresql" />
                        <el-option label="ClickHouse" value="clickhouse" />
                    </el-select>
                </el-form-item>

                <el-form-item label="数据库连接地址" prop="url">
                    <div style="display: flex; align-items: center;">
                        <el-input v-model="form.url" placeholder="jdbc:mysql://localhost:3306/dbname"
                            style="flex: 1;width:80%;min-width:450px" />
                        <el-button type="primary" @click="onCheckDatasource" :loading="checking"
                            style="margin-left: 10px">
                            检测连接
                        </el-button>
                    </div>
                    <div class="form-tip">
                        <span v-if="healthStatus === 'ok'" style="color: #67C23A;">数据源连接可用</span>
                        <span v-if="healthStatus === 'fail'" style="color: #F56C6C;">连接失败</span>
                        <span v-if="healthStatus !== 'ok' && healthStatus !== 'fail'">待连接验证.</span>
                    </div>
                </el-form-item>

                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username" placeholder="数据库用户名" />
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password" placeholder="数据库密码" show-password />
                </el-form-item>

                <el-form-item label="连接超时(毫秒)" prop="connectionTimeout">
                    <el-input-number v-model="form.connectionTimeout" :min="1000" :step="1000" />
                </el-form-item>

                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" resize="none" type="textarea" placeholder="备注信息（可选）" />
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" :loading="loading" @click="onSubmit">保存配置</el-button>
                    <el-button @click="onReset">恢复默认</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { 
    getDatasourceConfig, 
    getCurrentDatasourceConfig,
    addDatasourceConfig, 
    updateDatasourceConfig, 
    listDatasourceConfig, 
    probeDatasourceConfig 
} from '@/api/data/asset/datasource'

const formRef = ref(null)
const loading = ref(false)
const checking = ref(false)
const healthStatus = ref('') // '', 'ok', 'fail'

const defaultForm = {
    id: null,
    name: '',
    type: 'mysql',
    url: '',
    username: '',
    password: '',
    driverClassName: '',
    connectionTimeout: 30000,
    remark: ''
}

const form = reactive({ ...defaultForm })

const rules = {
    name: [{ required: true, message: '请输入数据源名称', trigger: 'blur' }],
    type: [{ required: true, message: '请选择数据源类型', trigger: 'change' }],
    url: [{ required: true, message: '请输入数据库地址', trigger: 'blur' }],
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function loadConfig() {
    loading.value = true
    try {
        const res = await getCurrentDatasourceConfig()
        // const dataList = res.data || []
        // if (dataList.length > 0) {
        //     Object.assign(form, dataList[0])
        // }
        Object.assign(form, res.data)
    } catch (e) {
        console.error(e)
        ElMessage.error('加载数据源配置失败')
    } finally {
        loading.value = false
    }
}
onMounted(() => loadConfig())

// 检测数据源连接
async function onCheckDatasource() {
    if (!form.url || !form.username || !form.password) {
        ElMessage.warning('请先填写完整连接信息再检测')
        return
    }
    checking.value = true
    healthStatus.value = ''
    try {
        const res = await probeDatasourceConfig({
            url: form.url,
            username: form.username,
            password: form.password,
            type: form.type
        })
        if (res.code == 200) {
            healthStatus.value = 'ok'
            ElMessage.success(res.msg || '连接成功')
        } else {
            healthStatus.value = 'fail'
            ElMessage.error(res.msg || '连接失败')
        }
    } catch (e) {
        console.error(e)
        healthStatus.value = 'fail'
        ElMessage.error('检测连接失败（可能无法访问后端接口）')
    } finally {
        checking.value = false
    }
}

// 保存前必须检测通过
async function onSubmit() {
    formRef.value.validate(async valid => {
        if (!valid) return

        // 强制检测一次
        await onCheckDatasource()
        if (healthStatus.value !== 'ok') {
            ElMessage.warning('请先确保数据源连接可用再保存')
            return
        }

        loading.value = true
        try {
            const res = await addDatasourceConfig(form)
            if(res.data){
                form.id = res.data
            }
            ElMessage.success('数据源配置保存成功')
        } catch (e) {
            console.error(e)
            ElMessage.error('保存失败')
        } finally {
            loading.value = false
        }
    })
}

function onReset() {
    Object.assign(form, defaultForm)
    formRef.value.clearValidate()
}
</script>

<style scoped lang="scss">
.form-container {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
    background: #fff;
    border-radius: 6px;
}

.label-title {
    text-align: center;
    margin-bottom: 20px;
    line-height: 2rem;

    .tip {
        font-size: 22px;
        font-weight: 600;
    }

    .sub-tip {
        font-size: 13px;
        color: #666;
    }
}

.form-tip {
    font-size: 12px;
    color: #999;
    margin-top: 5px;
}
</style>