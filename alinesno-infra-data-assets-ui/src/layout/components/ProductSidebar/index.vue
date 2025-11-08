<template>
  <div class="siderbar">
    <el-menu :default-active="activeMenu" class="el-menu-vertical" :collapse="isCollapse" @open="handleOpen" @close="handleClose">

        <el-menu-item :index="item.id" @click="openServiceList(item.link)" v-for="item in menuItems" :key="item.id"  class="aip-menu-item">
          <i :class="item.icon"></i>
          <span>
            {{ item.desc }}
          </span>
        </el-menu-item>
    </el-menu>

    <el-menu style="" class="el-menu-vertical acp-suggest" :collapse="isCollapse" @open="handleOpen" @close="handleClose">
        <el-menu-item :index="item.id" @click="openServiceList(item.link)" class="aip-menu-item" v-for="item in footerMenuItems" :key="item.id">
          <i :class="item.icon"></i>
          <span>
            {{ item.desc }}
          </span>
      </el-menu-item>
    </el-menu>

  </div>
</template>

<script setup>

const createChildComp = ref(null);
const createScreenComp = ref(null);

const dialogVisible = ref(false)

const router = useRouter();
const route = useRoute();

// 菜单列表
const menuItems = ref([
  { id: '1', icon: 'fa-solid fa-house-user', link: '/index', desc: '仪表盘' }, // 仪表盘用gauge更准确
  { id: '2', icon: 'fa-solid fa-list-check', link: '/asset/data/asset/manifest/index', desc: '资产清单' }, // 清单用list-check
  { id: '3', icon: 'fa-solid fa-sitemap', link: '/asset/data/asset/type/index', desc: '业务域' }, // 域管理用sitemap
  { id: '4', icon: 'fa-solid fa-chart-line', link: '/metrics/data/asset/metric/index', desc: '指标管理' }, // 指标用chart-line
  { id: '5', icon: 'fa-solid fa-server', link: '/service/data/fastapi/api/index', desc: '数据服务' }, // 服务用server
  { id: '12', icon: 'fa-solid fa-route', link: '/lift/data/lift/data/index', desc: '生命周期' }, // 生命周期用route表示流程
]);

// Footer菜单列表
const footerMenuItems = ref([
  { id: '15', icon: 'fa-solid fa-code', link: '/service/data/fastapi/apiclient/index', desc: 'API客户端' }, // API用code
  { id: '16', icon: 'fa-solid fa-calculator', link: '/operate/data/operate/analyse/index', desc: '计量统计' }, // 计量用calculator
  { id: '13', icon: 'fa-solid fa-lock', link: '/asset/data/asset/security/index', desc: '安全策略' }, // 安全用lock
]);

// 打开服务市场
function openServiceList(_path) {
  router.push({ path: _path });
}

// 打开客户配置
function jumpToConstomTheme() {
  router.push({ path: "/dashboard/dashboardTheme" });
}

// 添加频道
function addChannel(){
  createChildComp.value.handleOpenChannel(true);
}

// 添加场景
function addScreen(){
  createScreenComp.value.handleOpenChannel(true);
}

// 打开首页
function jumpTo() {
  router.push({ path: "/index" });
}

// 打开智能客服
function openSmartService() {
  router.push({ path: "/dashboard/smartService" });
}

const activeMenu = computed(() => {
  const currentPath = route.path
  const item = menuItems.value.find(item => {
    return currentPath.startsWith(item.link) // 匹配路由前缀
  })
  return item ? item.id : '1'
})

</script>

<style lang="scss" scoped>
.el-menu-vertical:not(.el-menu--collapse) {
  width: 70px;
}

.acp-suggest {
  bottom: 20px;
  position: absolute;
}

.siderbar {
  float: left;
  height: 100%;
  width: 70px;
  border-right: 1px solid #e6e6e6;
  padding-top: 40px;
  overflow: hidden;
  background-color: #fff;
  position: fixed;
  margin-top: 10px;
  margin-bottom: 20px;
  z-index: 10;
}

.aip-menu-item {

  display: flex;
  flex-direction: column;
  gap: 5px;
  line-height: 1.2rem;
  padding-top: 0px;
  margin: 5px;
  border-radius: 10px;
  justify-content: center;

  span{
    font-size:12px;
    color: #888;
  }
}
</style>
