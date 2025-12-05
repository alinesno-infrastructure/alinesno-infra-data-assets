<template>
  <div>
    <el-row class="acp-dashboard-panel" :gutter="20">
      <el-col class="panel-col" :span="19">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-file-waveform"></i>  数据域列表</div>
          </div>

          <div class="panel-body acp-height-auto" style="padding: 0;padding-bottom: 10px;">
            <div class="acp-app-list">
              <ul>
                <li class="app-items" v-for="item in apps" :key="item" style="width:calc(25% - 10px);display: flex">
                  <div class="app-icon">
                    <i :class="item.icon" style="font-size: 20px" />
                  </div>
                  <div class="app-info">
                    <div class="app-item-title">{{ item.name }}</div>
                    <div class="app-item remark">{{ item.remark }}</div>
                  </div>
                </li>
              </ul>
            </div>
            <div v-if="apps.length == 0">
              <el-empty image-size="100" description="当前还没有进行数据域划分，请配置元数据目录并集成数据域" >
                <el-button type="primary" @click="openMetaConfig()" text bg icon="Plus">配置数据域</el-button>
              </el-empty>
            </div>
          </div>

        </div>
      </el-col>

      <el-col :span="5">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-user-nurse"></i> 资产情况概览</div>
          </div>
          <div class="panel-body acp-height-auto">
            <ul class="panel-item-text">
              <li style="width:50%;padding:4px;border-bottom: 0px;" v-for="item in operationAssets" :key="item.id">
                <div class="item-health-box">
                  <div class="item-health-title">{{ item.title }}</div>
                  <div class="item-health-count">{{ item.count }}</div>
                </div>
              </li>
            </ul>
          </div>
          <div class="panel-footer">
            <div class="footer-link">
            </div>
          </div>
        </div>
      </el-col>

    </el-row>
  </div>
</template>

<script setup>

import {
  topCatalog , 
  getAssetStatus
} from "@/api/data/asset/dashboard";

import { ref } from 'vue'
import { ElLoading } from 'element-plus'

const operationAssets = ref([
  { id: '1', title: '资产总量', count: 0, key: 'assetCount' }, // 对应 assetCount
  { id: '2', title: '资产类型', count: 0, key: 'typeCount' }, // 对应 typeCount
  { id: '3', title: '资产主题', count: 0, key: 'topicCount' }, // 对应 topicCount
  { id: '5', title: '指标统计', count: 0, key: 'metricCount' }, // 对应 metricCount
  { id: '4', title: '使用统计', count: 0, key: 'totalUseCount' }, // 对应 totalUseCount（原“调用统计”）
])

const apps = ref([]);
const router = useRouter()

// 获取数据
function handCatalog(){
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })

    topCatalog().then(res => {
        apps.value = res.data;
        loading.close();
    })
}

const handleGetAssetStatus = () => {
  getAssetStatus().then(res => {
    if (res.code === 200 && res.data) {
      const assetData = res.data;
      // 循环匹配 key 并更新 count
      operationAssets.value.forEach(item => {
        item.count = assetData[item.key] || 0; // 兼容字段不存在时显示0
      });
    }
  })
}

// 跳转数据域配置
const openMetaConfig = () => {
    router.push('/assets/data/datasource/index')
}

// 获取数据
handCatalog();
handleGetAssetStatus() ; 

</script>

<style lang="scss" scoped>
.item-health-title {
  margin-bottom: 5px !important;
}

.item-health-count {
  margin-bottom: 5px;
}

.acp-dashboard .acp-app-list ul {

  .app-status {
    float: right;
    margin-right: 10px;
    font-size: 13px;
    line-height: 13px;
    color: #545b64 ;
  }

  li.app-items .app-item.remark {
    font-size: 12px;
    color: #545b64;
    margin-top: 5px;
  }

  li.app-items {
    list-style: none;
    float: left;
    border-bottom: 1px solid #fafafa;
    width: 50%;
    padding: 10px 0px;
    width: calc(33% - 10px);
    background: #fafafa;
    border-radius: 10px;
    padding: 10px;
    margin-right: 5px;
    margin-top: 10px;
    margin-left: 5px;
  }
}
</style>