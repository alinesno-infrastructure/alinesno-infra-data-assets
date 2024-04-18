<template>
  <div class="chat-container" v-loading="loading">

    <el-row>
      <el-col :span="7">

        <el-form :model="form" label-position="top" style="padding-right:20px;" label-width="120px">

          <el-form-item label="模型">
            <el-select v-model="form.region" style="width:100%" placeholder="请选择模型">
              <el-option label="gpt-3.5-turbo" value="shanghai" />
              <el-option label="gpt-4" value="beijing" />
            </el-select>
          </el-form-item>

          <el-form-item label="最大Token">
            <el-slider v-model="value" size="small" />
          </el-form-item>

          <el-form-item label="Temperature">
            <el-slider v-model="value" size="small" />
          </el-form-item>

          <el-form-item label="Top P">
            <el-slider v-model="value" size="small" />
          </el-form-item>

        </el-form>

      </el-col>
      <el-col :span="17">
        <div class="main-content">

          <div class="sidebar">
            <el-select v-model="selectedRole" placeholder="选择角色" class="role-select" @change="handleRoleChange">
              <el-option v-for="role in roles" :key="role.value" :label="role.label" :value="role.value"></el-option>
            </el-select>
          </div>
          <el-input type="textarea" :rows="10" v-model="inputText" placeholder="输入对话信息" class="input-text" resize="none"></el-input>

          <el-button @click="addMessage" class="add-button">添加对话</el-button>

          <div v-for="(message, index) in messages" :key="index" class="message">
            <div class="message-content">
              <el-select v-model="message.role" placeholder="选择角色" class="message-role-select">
                <el-option v-for="role in roles" :key="role.value" :label="role.label" :value="role.value"></el-option>
              </el-select>
              <el-input type="textarea" :rows="1" v-model="message.content" placeholder="输入对话信息"
                        class="message-input-text"></el-input>
            </div>
            <el-button @click="deleteMessage(index)" type="danger" class="delete-button">删除</el-button>
          </div>

          <div class="dialog-footer">
            <el-button type="primary" @click="saveJson" class="save-button">保存</el-button>
          </div>

        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script setup>

import {
  updatePromptContent,
  getPromptContent,
} from "@/api/data/asset/dataAsset";

const props = defineProps({
  currentPostId: {
    type: String,
    require: true,
  },
})

const form = ref({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

const { proxy } = getCurrentInstance();

const loading = ref(true)
const selectedRole = ref("")
const inputText = ref("")
const roles = ref([
  { value: 'system', label: '系统(system)' },
  { value: 'user', label: '用户(user)' },
  { value: 'assistants', label: '助手(assistants)' }
])
const messages = ref([])

function handleRoleChange() {
  // 处理角色切换
}

function addMessage() {
  console.log('current post id = ' + props.currentPostId);

  messages.value.push({ role: selectedRole.value, content: inputText.value });
  // messages = ref([])
  inputText.value = "";
}

function deleteMessage(index) {
  messages.value.splice(index, 1);
}

function saveJson() {
  const json = JSON.stringify(messages.value);
  console.log(json);

  updatePromptContent(json, props.currentPostId).then(response => {
    proxy.$modal.msgSuccess("修改成功");
  });
}

function handlePromptContent() {
  getPromptContent(props.currentPostId).then(response => {
    if (response.data) {
      messages.value = response.data;
    }
    loading.value = false;
  })
}

handlePromptContent();

console.log('message.value = ' + messages.value);

</script>

<style>
.chat-container {
  /* display: flex; */
  margin: 0 auto;
}

.sidebar {
  flex: 1;
}

.main-content {
  flex: 3;
}

.role-select {
  margin-bottom: 10px;
}

.input-text {
  margin-bottom: 10px;
}

.add-button {
  margin-bottom: 20px;
}

.message-role-select {
  margin-right: 20px;
}

.message {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.message-content {
  flex: 1;
  display: flex;
  align-items: center;
}

.message-input-text {
  flex: 1;
}

.delete-button {
  margin-left: 10px;
}

.save-button {
  margin-top: 20px;
}
</style>
