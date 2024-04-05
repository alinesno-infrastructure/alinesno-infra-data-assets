<template>
	<div>
	<el-container>
		<el-header>
			<el-row>
				<el-col :span="20" align="left">
					<el-menu router :default-active="$route.path" mode="horizontal" @select="handleSelect"
					background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
						<el-menu-item index="/loadBalanced" class="iconfont icon-jichuguanli">负载管理</el-menu-item>
						<el-menu-item index="/gatewayList" class="el-icon-connection">服务管理</el-menu-item>
						<el-menu-item index="/clientList" class="el-icon-s-custom">客户端管理</el-menu-item>
						<el-menu-item index="/ipList" class="iconfont icon-IP">IP名单管理</el-menu-item>
						<el-menu-item index="/apiCount" class="el-icon-s-data">接口统计</el-menu-item>
						<el-menu-item index="/apiMonitor" class="iconfont icon-gaojing">接口监控</el-menu-item>
						<el-menu-item index="/apiDoc" class="el-icon-collection">接口文档</el-menu-item>
					</el-menu>
				</el-col>
				<el-col :span="4" style="line-height: 60px; height: 60px;">
					  <span style="float: right; color: rgba(255, 255, 255, 0.5); margin-right: 20px; font-size: 14px;">
						<a :href="gatewayRoutesURL" target="_blank" style="text-decoration: none; color: #AAAEB2;" class="iconfont icon-liebiao" title="网关路由生效配置列表"></a>&nbsp;&nbsp;
						<i class="el-icon-collection-tag el-icon--left" title="系统版本"> 版本 {{GLOBAL_VAR.systemVersion}}</i>
					</span>
				</el-col>
			</el-row>
		</el-header>
		<el-main>
			<!-- 回到顶部 -->
			<template><el-backtop>UP</el-backtop></template>
			<!-- 主页面 -->
			<router-view name="mainView" ref="viewPage" v-if="isRouterAlive" />			
		</el-main>
	</el-container>
	</div>
</template>

<script>
	
export default {
	data() {
		return {
			isRouterAlive: true,
			isDisabled: true,
			gatewayRoutesURL: this.GLOBAL_VAR.gatewayRoutesURL + '/actuator/gateway/routes'
		};
	},
	created: function() {		
		this.init();
	},
	mounted: function() {
  
	},
	beforeDestroy: function() {

	},
	methods:{
		init(){
			this.$router.replace('/loadBalanced'); 
		},
		handleSelect(key, keyPath) {
			console.log(key);
		}
	}
};
</script>

<style>
html,
body,
#app,
.el-container {
	padding: 0px;
	margin: 0px;
	height: 100%;
}

.el-header {
	/* text-align: right;
	color: #333;
	height: 45px;*/ 
	/* padding: 0; */
	background-color: rgb(84, 92, 100);
}
.el-card {
	border: 1px solid #EBEEF5;
	background-color: rgba(255, 255, 255, 0.2);
	color: #303133;
	transition: .3s;
}
</style>
