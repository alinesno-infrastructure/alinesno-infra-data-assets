<template>
	<div>
		<div style="height: 52px; background-color: #005bd4;font-size: 14pt; font-weight: bold; color: #F0F0F0; padding-top: 18px; padding-left: 20px;">
			<i class="el-icon-s-custom"></i>&nbsp;客户端详情
		</div>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">名称:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small">{{infoForm.name}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">地址:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.ip}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">注册服务:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{this.totalNum}}个</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">创建时间:</el-col>
			<el-col :span="18" class="gateway-info-value"><el-tag size="small" type="success">{{infoForm.createTime}}</el-tag></el-col>
		</el-row>
		<el-row :gutter="24" style="border: 0px solid red;">
			<el-col :span="6" class="gateway-info-label">服务状态:</el-col>
			<el-col :span="18" class="gateway-info-value">
				<el-tag size="small" type="danger">{{infoForm.status === '0'?'启用':'禁用'}}</el-tag>
			</el-col>
		</el-row>
		<el-divider content-position="left"><span style="color: #606266; ">已注册服务</span></el-divider>
		<el-table size="small" :data="tableData" style="width: 100%" height="400">
			<el-table-column label="服务ID" prop="id"></el-table-column>
			<el-table-column label="服务名称" prop="name"></el-table-column>
			<el-table-column label="状态" width="70" prop="status">
				<template>
					<el-tag size="mini">{{'允许通行'}}</el-tag>
				</template>
			</el-table-column>		
			<el-table-column label="注册时间"  prop="regServerTime"></el-table-column>
		</el-table>
	</div>
</template>

<script>
	import {regServerPageList} from '@/api/regserver_api.js'
	export default {
		data() {
			return {
				name: 'clientInfo',
				tableData: [{
				}],
				totalNum: 0
			};
		},
		props:['infoForm'],//父组件传参
		created: function() {
			this.init();
		},
		mounted: function() {
	    
		},
		beforeDestroy: function() {
	
		},
		methods:{
			init() {
				this.regServerList();
			},
			regServerList(){
				let _this = this;
				regServerPageList({clientId: this.infoForm.id, currentPage: this.currentPage, pageSize: this.pageSize}).then(function(result){
					if (result.data){
						_this.tableData = result.data.lists;
						_this.totalNum = result.data.totalNum;
					}
				});
			}
			
		}
	}
</script>

<style>
</style>
