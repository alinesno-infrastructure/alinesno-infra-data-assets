<template>
  <div>
    <router-link tag="div" class="header-logo-bar" to="/index">
      <div class="header-logo" v-if="enableLogo" @click="enterDomain">
        <img :src="saasLogoUrl" alt="" />
      </div>
      <a :title="saasTitle" target="_self" class="header-logo-label">
        <span>{{ saasTitle }} </span>
      </a>
      <div v-if="saasUrl" class="dashboard-home" @click="dashboardHome()">
        <i class="fa-solid fa-house"></i> 工作台
      </div>
    </router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import saasLogo from '@/assets/logo/logo.png'  // 先导入图片

const saasTitle = ref('AIP智能设施')
const enableLogo = ref(true)
const saasUrl = ref('https://alinesno-infra-plat-console-ui.linesno.com')
const saasLogoUrl = ref(saasLogo)  // 使用导入的图片
const displayUrl = ref('')
const domainName = ref(null)

const dashboardHome = () => {
  window.location.href = saasUrl.value
}

// 进入企业官网
const enterDomain = () => {
  if (domainName.value) { // 跳转进入官网
    window.open(domainName.value)
  }
}
</script>