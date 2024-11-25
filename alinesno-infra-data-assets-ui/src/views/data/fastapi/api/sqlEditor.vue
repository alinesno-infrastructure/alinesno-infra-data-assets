<template>
    <div class="app-container">
        <el-page-header @back="goBack" :content="'['+ currentApi.name +']工具脚本配置'">
        </el-page-header>

        <div class="form-container">
            <el-row>
                <el-col :span="14" 
                    v-loading="loading"
                    element-loading-text="任务正在生成中，请勿刷新 ..."
                    :element-loading-spinner="svg"
                >

                    <div style="margin-top: 20px;margin-bottom: 20px;display: inline-flex;width: 100%;flex-direction: column;gap: 8px;">

                        <div class="flow-setting-footer" style="margin: 0px;display: flex;gap: 10px;">
                            <el-input v-model="chatMessage" size="large" style="width: 100%;" placeholder="输入请求参数，比如{'name':'lisi'}." />
                            <el-button type="primary" text bg @click="handleValidateTask()" size="large">
                                <i class="fa-solid fa-truck-fast"></i>&nbsp;验证任务
                            </el-button>
                            <el-button type="danger" bg text @click="submitForm()" size="large">
                                <i class="fa-solid fa-paper-plane"></i>&nbsp;提交保存
                            </el-button>
                        </div>

                        <div>
                            <ScriptEditorPanel ref="executeEditorRef" :lang="'java'" />
                        </div>


                    </div>

                </el-col>
                <el-col :span="10">
                    <div class="output-result-box">
                        <el-card shadow="never" style="height:calc(100vh - 220px);padding:0px !important">
                            <template #header>
                                <div class="card-header">
                                    <span>执行结果</span>
                                </div>
                            </template>
                            
                            <el-skeleton v-if="!genContent" :rows="10" />
                            <el-scrollbar v-else style="height: calc(-320px + 100vh);">
                                <div v-html="markdown.render(genContent)"></div>
                            </el-scrollbar>

                        </el-card>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script setup>

// import { validateApiScript, updateApiScript } from '@/api/smart/assistant/role'
import ScriptEditorPanel from './ScriptEditor.vue';

import {
  getApi , 
  validateApiScript,
  updateApiScript
} from "@/api/data/fastapi/apiConfig";

// import { getParam } from '@/utils/ruoyi'

// import MarkdownIt from 'markdown-it';
// const markdown = new MarkdownIt()

const router = useRouter();
const { proxy } = getCurrentInstance();

const loading = ref(false)
const executeEditorRef = ref(null)

// const tabPosition = ref('right')
// const auditEditorRef = ref(null)
// const functionCallEditorRef = ref(null)

const scriptType = ref("execute")
const genContent = ref(null)

const currentApi = ref({
    name:''
});
const currentApiId = ref(null)
const chatMessage = ref("")

/** 获取代码 */
const getCode = () => {
    return executeEditorRef.value.getRawScript()
}

/** 设置当前角色 */
function setCurrentApiId(id) {
    currentApiId.value = id;
}

/** 验证脚本任务 */
const handleValidateTask = () => {
    let type = scriptType.value;
    const code = getCode();
    const toolId = currentApiId.value;

    loading.value = true
    
    validateApiScript({
        'script': code,
        'toolId': toolId,
        'type': type,
        'params': chatMessage.value
    }).then(res => {
        console.log('res = ' + res);
        loading.value = false 
        genContent.value = res.data
        proxy.$modal.msgSuccess("验证成功");
    }).catch(err => {
        console.log('err = ' + err);
        loading.value = false 
    })
}

/** 提交脚本任务 */
const submitForm = () => {
    
    let type = scriptType.value;
    const code = getCode();
    const toolId = currentApiId.value;

    loading.value = true
    
    updateApiScript({
        'script': code,
        'toolId': toolId,
        'type': type,
        'params': 'save'
    }).then(res => {
        console.log('res = ' + res);
        loading.value = false 
        proxy.$modal.msgSuccess("更新成功");
    }).catch(err => {
        console.log('err = ' + err);
        loading.value = false 
    })
}

/** 返回 */
function goBack() {
    router.push({ path: '/template/smart/assistant/plugin/index' });
}

/** 获取角色信息 */
function getApiInfo() {
    currentApiId.value = router.currentRoute.value.query.toolId; 
    getApi(currentApiId.value).then(response => {
        currentApi.value = response.data;
        let groovyScript = "" ;
        if(response.data.groovyScript){
            groovyScript = response.data.groovyScript
        }
        executeEditorRef.value.setRawScript(groovyScript) ;
    });
}

/** 切换tab */
function handleTabClick(tab, event) {
    console.log('tab = ' + JSON.stringify(tab))
    scriptType.value = tab.props.name
    console.log('type = ' + scriptType.value)
}

nextTick(() => {
    // getApiInfo();
})

defineExpose({
    setCurrentApiId
})

</script>

<style scoped lang="scss">
.flow-setting-footer {
    float: right;
    margin-bottom: 20px;
}

.output-result-box {
    padding: 20px;
    margin-left: 20px;

    .card-header {
        padding: 10px;
    }
}
</style>