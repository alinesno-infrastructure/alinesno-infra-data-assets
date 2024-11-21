<template>
  <nav class=" header-text">
    <div class="acp-header-item ">
      <router-link class="header-label-text" to="/index">
        <i class="fa-solid fa-screwdriver-wrench"></i> 控制台
      </router-link>
    </div>
    <div class="acp-header-item ">
      <router-link class="header-label-text" to="/dashboard/learnPanel">
        <i class="fa-regular fa-file-word"></i> 手册
      </router-link>
    </div>
    <div class="acp-header-item ">
      <router-link class="header-label-text" to="/dashboard/suportTechnique">
        <i class="fa-solid fa-user-tag"></i> 服务
      </router-link>
    </div>

    <el-dropdown size="mini">
      <div class="acp-header-item " style="display: flex">
        <a class="header-label-text">
          <i class="fa-solid fa-shield-halved"></i>
          {{ userStore.nickName }}
          {{ account }}
          <el-icon>
            <ArrowDownBold />
          </el-icon>
        </a>
        <a class="header-label-text" target="_blank">
          <img src="http://data.linesno.com/switch_header.png" class="su70ez-0 CB-gLgKdv" alt="" />
        </a>
      </div>

      <template #dropdown>
        <el-dropdown-menu style="width: 350px">

          <el-container style="margin-bottom: 15px">
            <el-header class="bg-color-base info-h" style="">
              <p class="color-text-secondary f-e-s">登陆名: {{ userStore.name }}
                <span class="copy-user-id">
                  <i class="fa-solid fa-clone"></i>
                </span>
              </p>
              <p class="color-text-primary f-e-s" v-if="userStore.org">组织: {{ userStore.org.orgName }} </p>
              <p class="color-text-secondary f-e-s" v-if="userStore.org">角色: {{ userStore.org.roleName }}</p>
            </el-header>
          </el-container>

          <router-link :to="{ path: '/user/profile' }" replace>
            <el-dropdown-item>
              <i class="fa-solid fa-user-pen"></i> 用户中心
            </el-dropdown-item>
          </router-link>

          <router-link :to="{ path: '/dashboard/dashboardTheme' }" replace>
          <el-dropdown-item>
            <i class="fa-solid fa-desktop"></i> 机构配置
          </el-dropdown-item>
          </router-link>

          <router-link :to="{ path: '/dashboard/dashboardTheme' }" replace>
            <el-dropdown-item>
              <i class="fa-solid fa-user-group"></i> 锁屏
            </el-dropdown-item>
          </router-link>

          <!--
          <el-dropdown-item @click="setting = true">
            <i class="fa-solid fa-pen-ruler"></i> 布局设置
          </el-dropdown-item>

          <el-dropdown-item>
            <i class="fa-solid fa-rocket"></i> 账单面板
          </el-dropdown-item>
          -->

          <el-container>
            <el-main>
              <el-button type="primary" style="width: 100%" @click="logout">退出登陆</el-button>
            </el-main>
          </el-container>

        </el-dropdown-menu>
      </template>
    </el-dropdown>

  </nav>
</template>

<script setup>

import { ElMessageBox } from 'element-plus'
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
const router = useRouter();

// const avatar = ref('http://data.linesno.com/switch_header.png') ;
// const nickname = ref('超级管理员') ;
// const name = ref('超级管理员') ;

function logout() {
  ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {

    userStore.ssoLogOut().then(() => {
      location.href = '/index';
    })

  }).catch(() => { });
}

/** 判断用户是否已经存在组织 */
function handleInOrg(){
  let org = userStore.org ;
  if(!org){ // 如果不存在则跳转到组织配置界面
    router.push('/createOrg')
  }
}

onMounted(() => {
  handleInOrg()
})

console.log('avatar = ' + userStore.avatar)
console.log('name = ' + userStore.name)
console.log('dept = ' + JSON.stringify(userStore.dept))
console.log('role = ' + JSON.stringify(userStore.roles))

</script>

<style lang="scss" scoped>

.color-text-secondary , .color-text-primary{
  font-size: var(--el-font-size-base);

  .copy-user-id {
    margin-left: 10px;
    cursor: pointer;
  }
}

</style>


