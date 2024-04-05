<template>
	<div>
		<div style="height: 52px; background-color: #005bd4;font-size: 14pt; font-weight: bold; color: #F0F0F0; padding-top: 18px; padding-left: 20px;">
			<i class="el-icon-connection"></i>&nbsp;服务详情
		</div>
		<!-- <el-divider content-position="left"><span style="color: #606266; ">网关服务详情</span></el-divider> -->
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">服务ID:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small">{{infoForm. id}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">服务名称:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small">{{infoForm.name}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">服务地址:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.uri}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;" v-if="infoForm.method != undefined && infoForm.method != ''">
			<el-col :span="6" class="gateway-info-label">请求模式:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.method}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">断言路径:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.path}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" v-if="infoForm.host != undefined && infoForm.host != ''" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">断言Host:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.host}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" v-if="infoForm.remoteAddr != undefined && infoForm.remoteAddr != ''" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">断言远程地址:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.remoteAddr}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" v-if="infoForm.header != undefined && infoForm.header != ''" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">断言Header:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.header}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">断言截取:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">StripPrefix={{infoForm.stripPrefix}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">请求参数:</el-col>
			<el-col :span="18" class="gateway-info-value" :title="infoForm.requestParameter"><el-tag size="small" type="success">RequestParameter={{infoForm.requestParameter}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" v-if="infoForm.rewritePath != undefined && infoForm.rewritePath != ''" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">重定向:</el-col>
			<el-col :span="18" class="gateway-info-value" :title="infoForm.rewritePath"><el-tag size="small" type="success">{{infoForm.rewritePath}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;" v-if="infoForm.filterGatewaName != undefined && infoForm.filterGatewaName != ''">
			<el-col :span="6" class="gateway-info-label">过滤器:</el-col>
			<el-col :span="18" class="gateway-info-value">
				<el-tag size="small" v-if="infoForm.filterGatewaName.indexOf('ip') != -1" type="warning">IP</el-tag>
				<el-tag size="small" v-if="infoForm.filterGatewaName.indexOf('token') != -1" type="warning">TOKEN</el-tag>
				<el-tag size="small" v-if="infoForm.filterGatewaName.indexOf('id') != -1" type="warning">ID</el-tag>
			</el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;" v-if="infoForm.filterHystrixName != undefined && infoForm.filterHystrixName != ''">
			<el-col :span="6" class="gateway-info-label">熔断器:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="warning">{{infoForm.filterHystrixName}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;" v-if="infoForm.filterRateLimiterName != undefined && infoForm.filterRateLimiterName != ''">
			<el-col :span="6" class="gateway-info-label">限流器:</el-col>
			<el-col :span="18" class="gateway-info-value">
				<el-tag size="small" type="warning">{{infoForm.filterRateLimiterName}}</el-tag>
			</el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;" v-if="infoForm.filterAuthorizeName != undefined && infoForm.filterAuthorizeName != ''">
			<el-col :span="6" class="gateway-info-label">鉴权器:</el-col>
			<el-col :span="18" class="gateway-info-value">
				<el-collapse accordion>
				  <el-collapse-item v-if="infoForm.filterAuthorizeName.indexOf('header') != -1">
					  <template slot="title">
						  <el-tag size="small" type="warning">HEADER验证</el-tag>
					  </template>
					<div><el-tag size="mini" type="">{{infoForm.accessHeader}}</el-tag></div>
				  </el-collapse-item>
				  <el-collapse-item v-if="infoForm.filterAuthorizeName.indexOf('ip') != -1">
					  <template slot="title">
					  	<el-tag size="small" type="warning">IP验证</el-tag>
					  </template>
					<div><el-tag size="mini" type="">ip={{infoForm.accessIp}}</el-tag></div>
				  </el-collapse-item>
				  <el-collapse-item v-if="infoForm.filterAuthorizeName.indexOf('parm') != -1">
					  <template slot="title">
					  	<el-tag size="small" type="warning">参数验证</el-tag>
					  </template>
					<div><el-tag size="mini" type="">{{infoForm.accessParameter}}</el-tag></div>
				  </el-collapse-item>
				  <el-collapse-item v-if="infoForm.filterAuthorizeName.indexOf('time') != -1">
					  <template slot="title">
					  	<el-tag size="small" type="warning">时间验证</el-tag>
					  </template>
					<div><el-tag size="mini" type="">时间区间：{{infoForm.accessTime}}</el-tag></div>
				  </el-collapse-item>
				  <el-collapse-item v-if="infoForm.filterAuthorizeName.indexOf('cookie') != -1">
					  <template slot="title">
					  	<el-tag size="small" type="warning">Cookie验证</el-tag>
					  </template>
					<div><el-tag size="mini" type="">{{infoForm.accessCookie}}</el-tag></div>
				  </el-collapse-item>
				</el-collapse>
			</el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">创建时间:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="danger">{{infoForm.createTime}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">服务状态:</el-col>
			<el-col :span="18" class="gateway-info-value">
				<el-tag size="small" type="danger">{{infoForm.status === '0'?'启用':'禁用'}}</el-tag>
			</el-col>
		</el-row>
		<!-- <el-divider content-position="left">服务运行状态</el-divider> -->
	</div>
</template>

<script>
	export default {
		data() {
			return {
				name:'routeInfo',
			}
		},
		created: function() {
			this.init();
		},
		props:['infoForm'],//父组件传参
		methods:{
			init() {
				console.log('infoForm',this.infoForm)
			}
		}
	}
</script>

<style>
</style>
