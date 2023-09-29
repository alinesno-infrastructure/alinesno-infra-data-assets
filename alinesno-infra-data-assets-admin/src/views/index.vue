<template>
  <div class="app-container acp-dashboard">

    <!-- 应用统计 -->
    <OperationWorkspaceCountStaitcs />

    <!-- 应用套件和状态 -->
    <OperationWorkspaceService />

    <!-- 运行应用列表 -->
    <OperationWorkspaceApps />

  </div>
</template>

<script setup name="Index">


import OperationWorkspaceCountStaitcs from './operation-workspace/countStatics.vue'
import OperationWorkspaceApps from './operation-workspace/apps.vue'
import OperationWorkspaceService from './operation-workspace/service.vue'

</script>