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
  topCatalog
} from "@/api/data/asset/dashboard";

import { ref } from 'vue'
import { ElLoading } from 'element-plus'

const operationAssets = ref([
  { id: '1', title: '资产总量', count: 3762428145 },
  { id: '2', title: '资产类型', count: 164 },
  { id: '3', title: '资产主题', count: 635723 },
  { id: '4', title: '行业统计', count: 31 },
  { id: '5', title: '使用统计', count: 78366485 },
])

const apps = ref([]);

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

// 获取数据
handCatalog();

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
    border-radius: 3px;
    padding: 10px;
    margin-right: 5px;
    margin-top: 10px;
    margin-left: 5px;
  }
}
</style>