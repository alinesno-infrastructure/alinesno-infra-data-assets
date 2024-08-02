<template>
  <div class="app-container acp-dashboard">

    <el-row>
      <el-col :span="24">
        <div class="aip-appinfo-header">
          <div class="header-icon-banner">
            <i class="fa-solid fa-layer-group"></i>
          </div>
          <div class="icon">
            <div class="title">
                  {{ currentEnvClusterObj.appName }}
            </div>
            <div class="title-desc">
                  {{ currentEnvClusterObj.desc }}
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 应用统计 -->
    <!-- <OperationWorkspaceCountStaitcs /> -->

    <!-- 应用套件和状态 -->
    <OperationWorkspaceService />

    <!-- 运行应用列表 -->
    <OperationWorkspaceApps />

  </div>
</template>

<script setup name="Index">


// import OperationWorkspaceCountStaitcs from './operation-workspace/countStatics.vue'

import OperationWorkspaceApps from './operation-workspace/apps.vue'
import OperationWorkspaceService from './operation-workspace/service.vue'


const currentEnvClusterObj = ref({
  appName : '数据资产管理服务'  , 
  apiServerUrl: 'http://portal.infra.linesno.com' , 
  desc: '数据经采集、集成、加工等流程处理完成后，可以在数据资产模块进行系统化管理'
}) 

</script>


<style lang="scss" scoped>
.aip-appinfo-header{
  position: relative;
  padding: 20px;
  overflow: hidden;

  .header-icon-banner {
    float: left;
    font-size: 2.4rem;
    margin-right: 10px;
    color: #3b5998;
  }

  .head-app-status{
    float: right;
    font-size: 14px;
    line-height: 1.7rem;
    margin-bottom: 10px;

    .integrated-item-box {

      margin-left: 10px;
      float: right;

      ul , li {
        margin: 0px;
        padding: 0px;
        list-style: none;
      }
      li {
        float:right ;
        margin-right: 10px;

        img {
          border-radius: 5px;
          width: 25px;
        }
      }

    }
  }

  .cluster-info {
    float: right;
    position: relative;
    font-size: 14px;
    margin-left: 10px;
    font-weight: bold;
    margin-top: 10px;
    color: #3b5998;

    span {
      margin-left: 20px;
    }
  }

  .icon {
    float: left;
  }

  .title {
    display: block;
    font-weight: 600;
    font-style: normal;
    font-size: 24px;
    color: #242e42;
    text-shadow: 0 4px 8px rgba(36, 46, 66, 0.1);
    margin-bottom: 10px;
  }

  .title-desc {
    color: #79879c !important;
    font-size: 12px;
  }
}
</style>