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
      <router-link class="header-label-text" to="/dashboard/smartService">
        <i class="fa-solid fa-swatchbook"></i> 专家 
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
          {{ nickname }}
          {{ account }}
          <el-icon>
            <ArrowDownBold />
          </el-icon>
        </a>
        <a class="header-label-text" target="_blank">
          <img :src="avator" class="su70ez-0 CB-gLgKdv" alt="" />
        </a>
      </div>

      <template #dropdown>
        <el-dropdown-menu style="width: 350px">

          <el-container style="margin-bottom: 15px">
            <el-header class="bg-color-base info-h" style="">
              <p class="color-text-secondary f-e-s">名称: {{ nickname }}</p>
              <p class="color-text-primary f-e-s">账号ID: 417-14778-7888  
                <span class="copy-user-id">
                  <i class="fa-solid fa-clone"></i> 
                </span>
              </p>
            </el-header>
          </el-container>

          <router-link :to="{ path: '/user/profile' }" replace>
            <el-dropdown-item>
              <i class="fa-solid fa-user-pen"></i> 用户中心
            </el-dropdown-item>
          </router-link>

          <router-link :to="{ path: '/dashboard/dashboardTheme' }" replace>
          <el-dropdown-item>
            <i class="fa-solid fa-desktop"></i> 组织配置
          </el-dropdown-item>
          </router-link>

          <router-link :to="{ path: '/dashboard/dashboardTheme' }" replace>
            <el-dropdown-item>
              <i class="fa-solid fa-user-group"></i> 锁屏
            </el-dropdown-item>
          </router-link>

          <el-dropdown-item @click="setting = true">
            <i class="fa-solid fa-pen-ruler"></i> 布局设置
          </el-dropdown-item>

          <el-dropdown-item>
            <i class="fa-solid fa-rocket"></i> 账单面板
          </el-dropdown-item>

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
<script>
export default {
  name: "TopHeader",
  components: {},
  data() {
    return {
      drawer: false,
      direction: 'rtl',
    };
  },
  computed: {
    role: {
      get() {
        return this.$store.state.user.roles;
      },
    },
    account: {
      get() {
        // const { account } =  null ; // this.$store.state.user;
        // return account ? account : "";
        return "";
      },
    },
    nickname: {
      get() {
        const nickname = '超级管理员' //  this.$store.state.user.nickname;
        return nickname;
      },
    },
    name: {
      get() {
        const name = '超级管理员'; //  this.$store.state.user.name;
        return name;
      },
    },
    avator: {
      get() {
        const avatar = "http://data.linesno.com/switch_header.png"; //  this.$store.state.user.avatar;
        return avatar;
      },
    },
    setting: {
      get() {
        return this.$store.state.settings.showSettings;
      },
      set(val) {
        this.$store.dispatch("settings/changeSetting", {
          key: "showSettings",
          value: val,
        });
      },
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav;
      },
    },
  },
  methods: {
    lockScreen() {
      this.$message({
        message: '功能在内测试中',
        type: 'success'
      });
    },
    handleCommand(command) {
      this.$router.push({ name: command });
    },
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    submitForm() { },
    cancel() { },
    dashboardHome() {
      window.location.href = this.saasUrl;
    },
    // 表单重置
    reset() {
      this.form = {
        noticeId: undefined,
        noticeTitle: undefined,
        noticeType: undefined,
        noticeContent: undefined,
        status: "0",
      };
      this.resetForm("form");
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工单";
    },
    async logout() {
      this.$confirm("确定注销并退出系统吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // this.$store.dispatch('LogOut').then(res()=>{
        //     window.location.href="/"
        // })
        this.$store.dispatch("LogOut").then(() => {
          window.location.href = "/";
        });
      });
    },
  },
};
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


