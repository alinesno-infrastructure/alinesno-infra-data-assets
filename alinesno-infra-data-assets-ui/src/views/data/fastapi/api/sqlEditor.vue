<template>
  <div class="app-container">
    <el-page-header @back="goBack" :content="'['+ currentApi.name +']工具脚本配置'"></el-page-header>

    <div class="form-container">
      <el-row>
        <el-col :span="7">
          <ParamConfigPanel v-model="currentApi" />
        </el-col>

        <el-col :span="10"
                v-loading="loading"
                element-loading-text="任务正在生成中，请勿刷新 ..."
                :element-loading-spinner="svg">

          <div style="margin-top: 20px;margin-bottom: 20px;display: inline-flex;width: 100%;flex-direction: column;gap: 8px;">
            <div class="flow-setting-footer" style="margin: 0px;display: flex;gap: 10px;">
              <el-input v-model="chatMessage" size="large" style="width: 100%;" placeholder="输入请求参数，比如{'name':'lisi'}." />
              <el-button type="primary" text bg @click="handleValidateTask()" size="large">
                <i class="fa-solid fa-truck-fast"></i>&nbsp;验证任务
              </el-button>
              <!-- <el-button type="danger" bg text @click="submitForm()" size="large">
                <i class="fa-solid fa-code"></i>&nbsp;保存脚本
              </el-button> -->
              <el-button type="primary" bg text @click="saveConfig()" size="large">
                <i class="fa-solid fa-code"></i>&nbsp;保存配置
              </el-button>
            </div>

            <div style="margin-top:15px;">
              <ScriptEditorPanel ref="executeEditorRef" :lang="'java'" />
            </div>
          </div>

        </el-col>

        <el-col :span="7">
          <div class="output-result-box">
            <el-card shadow="never" style="height:calc(100vh - 160px);padding:0px !important">
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
import ScriptEditorPanel from './ScriptEditor.vue';
import ParamConfigPanel from './ParamConfig.vue';
import {
  getApi,
  validateApiScript,
  updateApiScript,
  saveApiConfig,
  updateApiConfig
} from "@/api/data/fastapi/apiConfig";

const router = useRouter();
const { proxy } = getCurrentInstance();

const loading = ref(false);
const executeEditorRef = ref(null);

import MarkdownIt from 'markdown-it';
const markdown = new MarkdownIt();

// const markdown = new (await import('markdown-it')).default();

const scriptType = ref("groovy"); // 与 currentApi.scriptType 同步
const genContent = ref(null);

const currentApi = ref({
  id: '',
  name: '',
  description: '',
  scriptType: 'groovy',
  enabled: true,
  params: []
});

const currentApiId = ref(null);
const chatMessage = ref("");

// 获取代码（防空）
const getCode = () => {
  return executeEditorRef.value ? (executeEditorRef.value.getRawScript() || '') : '';
}

// 设置当前API ID（对外暴露）
function setCurrentApiId(id) {
  currentApiId.value = id;
}

// 验证脚本
const handleValidateTask = () => {
  const type = scriptType.value || currentApi.value.scriptType || 'groovy';
  const code = getCode();
  const apiId = currentApiId.value;

  if (!code || !code.trim()) {
    proxy.$modal.msgError("脚本不能为空！");
    return;
  }

  loading.value = true;

  validateApiScript({
    script: code,
    apiId,
    type,
    params: chatMessage.value || ''
  }).then(res => {
    loading.value = false;
    genContent.value = res.data;
    proxy.$modal.msgSuccess("验证成功");
  }).catch(err => {
    loading.value = false;
    proxy.$modal.msgError(err?.message || "验证失败");
  });
}

// 提交保存脚本（仅脚本）
const submitForm = () => {
  const type = scriptType.value || currentApi.value.scriptType || 'groovy';
  const code = getCode();
  const apiId = currentApiId.value;

  if (!currentApi.value.name) {
    proxy.$modal.msgError("接口名称不能为空！");
    return;
  }
  if (!apiId) {
    proxy.$modal.msgError("缺少 API ID，无法保存脚本");
    return;
  }
  if (!code || !code.trim()) {
    proxy.$modal.msgError("脚本不能为空！");
    return;
  }

  loading.value = true;

  updateApiScript({
    script: code,
    apiId,
    type,
    params: chatMessage.value || ''
  }).then(() => {
    loading.value = false;
    proxy.$modal.msgSuccess("脚本保存成功");
  }).catch(err => {
    loading.value = false;
    proxy.$modal.msgError(err?.message || "脚本保存失败");
  });
}

// 保存所有配置（新增或更新由后端判断）
const saveConfig = () => {
  if (!currentApi.value.name || !currentApi.value.name.trim()) {
    proxy.$modal.msgError("接口名称不能为空！");
    return;
  }
  const code = getCode();
  if (!code || !code.trim()) {
    proxy.$modal.msgError("脚本不能为空！");
    return;
  }

  loading.value = true;

  const payload = {
    ...currentApi.value,
    id: currentApiId.value,
    // 设置脚本字段按照 scriptType 写入，后端会做校验
    scriptType: currentApi.value.scriptType || scriptType.value || 'groovy',
    groovyScript: getCode(),
    params: currentApi.value.params // 数组，后端会序列化
  };

  // 使用 saveApiConfig（后端会根据 id 判断新增/更新）
  saveApiConfig(payload).then(() => {
    loading.value = false;
    proxy.$modal.msgSuccess("配置保存成功！");
    // 更新本地数据（重新拉取）
    getApiInfo();
  }).catch(err => {
    loading.value = false;
    proxy.$modal.msgError(err?.message || "保存失败！");
  });
}

// 还原配置（从后端重新拉取）
const resetConfig = () => {
  proxy.$modal.confirm("确定还原到已保存的配置吗？")
    .then(() => {
      getApiInfo();
      proxy.$modal.msgSuccess("已还原配置");
    })
    .catch(() => {});
}

// 返回
function goBack() {
  router.push({ path: '/service/data/fastapi/api/index' });
}

// 获取API信息并同步 editor / scriptType
function getApiInfo() {
  if (!currentApiId.value) return;
  getApi(currentApiId.value).then(response => {
    const data = response.data || {};
    // 后端实体字段可能是 note -> description 等，前端 currentApi 以 DTO 字段为准
    currentApi.value.id = data.id || currentApiId.value;
    currentApi.value.name = data.name || '';
    currentApi.value.description = data.note || data.description || '';
    currentApi.value.scriptType = data.executeType || data.scriptType || 'groovy';
    currentApi.value.enabled = (data.enabled == null ? true : data.enabled);
    currentApi.value.path = data.path || '';
    currentApi.value.datasourceId = data.datasourceId|| '';
    // 尝试解析 jsonParam 或 params，如果是 JSON 字符串
    try {
      const params = data.jsonParam || data.params;
      currentApi.value.params = typeof params === 'string' ? JSON.parse(params || '[]') : (params || []);
    } catch (e) {
      currentApi.value.params = data.params || [];
    }

    // 更新编辑器脚本：优先 groovyScript，再 runSql
    const script = (data.groovyScript && data.groovyScript.trim()) ? data.groovyScript : (data.runSql || '');
    if (executeEditorRef.value && typeof executeEditorRef.value.setRawScript === 'function') {
      executeEditorRef.value.setRawScript(script || '');
    }
    // 同步 scriptType ref
    scriptType.value = currentApi.value.scriptType || 'groovy';
  }).catch(err => {
    proxy.$modal.msgError(err?.message || "获取 API 信息失败");
  });
}

onMounted(() => {
  currentApiId.value = router.currentRoute.value.query.apiId;
  getApiInfo();
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
  padding-right: 0px;
  margin-left: 0px;
  .card-header {
    padding: 10px;
  }
}
</style>