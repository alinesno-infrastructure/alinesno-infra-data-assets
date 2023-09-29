<template>
	<div class="app-container">
		<el-page-header @back="goBack" content="已注册服务端管理"></el-page-header>
		<el-dialog title="添加服务端" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
			<el-table size="mini" :data="routeTableData" style="width: 100%">
				<el-table-column label="服务ID" prop="routeId"></el-table-column>
				<el-table-column label="服务名称" prop="name"></el-table-column>
				<el-table-column label="服务地址" prop="uri"></el-table-column>
				<el-table-column label="操作" width="60">
					<template slot-scope="scope">
						<el-button size="mini" circle icon="el-icon-plus" type="success" title="添加" @click="handleAddRegServer(scope.row)"></el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="block" style="margin-top: 20px;">
				<el-pagination
					@size-change="handleRouteSizeChange"
					@current-change="handleRouteCurrentChange"
					:current-page="routeCurrentPage"
					:page-sizes="[10,  30, 50]"
					:page-size="routePageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="routeTotalNum">
				</el-pagination>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false" size="small">关 闭</el-button>
			</div>
		</el-dialog>

		<el-row :gutter="20" style="margin-top: 20px;">
			<el-col>
				<el-card shadow="false" class="box-card">
					<div slot="header" class="clearfix">
						<span>网关服务端</span>
						<el-popover trigger="click" placement="bottom">
							<div style="font-size: 10pt;">
								<span>添加服务端说明：</span><br/>
								<span>1. 开启路由服务ID过滤后，只有请求参数或Header中带有clientId="当前客户端ID(注册KEY)"才能访问指定服务端。</span><br/>
								<span>2. 开启路由服务ID过滤后，此功能主要适用于对第三方开放服务，提供简单认证访问。</span><br/>
								<span>3. 新添加的路由服务端后，默认为禁止访问路由服务，请手动开启允许访问，才能生效。</span><br/>
							</div>
							<el-button slot="reference" style="padding: 3px 0; " icon="el-icon-question" type="text" title="说明"></el-button>
						</el-popover>
						<span style="margin-left: 50px;">
							<i class="el-icon-monitor"></i>
							<span style="font-size: 11pt;">
								<el-tag size="mini" style="font-weight: bold;">{{form.name}}</el-tag> -
								<el-tag size="mini" type="success" style="font-weight: bold;">{{form.ip}}</el-tag>
							</span>
						</span>
						<div style="float: right; margin-left: 10px;">
						    <el-button icon="el-icon-circle-plus-outline" size="small" type="success" @click="search" title="查找服务端"> 添加服务 </el-button>
						</div>
						<div style="float: right; margin-left: 10px;">
						    <el-button icon="el-icon-s-claim" size="small" type="primary" @click="startAll" title="启用所有客户端通行"> 全部允许 </el-button>
						</div>
						<div style="float: right; margin-left: 10px;">
						    <el-button icon="el-icon-circle-close" size="small" type="danger" @click="stopAll" title="禁用所有客户端通行"> 全部禁止 </el-button>
						</div>
					</div>

					<el-table size="small" :data="tableData" style="width: 100%">
						<el-table-column label="服务ID" prop="routeId"></el-table-column>
						<el-table-column label="服务名称" prop="name"></el-table-column>
						<el-table-column label="服务地址" prop="uri"></el-table-column>
						<el-table-column label="断言路径" prop="path"></el-table-column>
						<el-table-column label="注册时间" prop="regServerTime" width="200"></el-table-column>
						<el-table-column label="状态" prop="regServerStatus">
							<template slot-scope="scope">
								<div v-if="scope.row.regServerStatus==='0'"><i class="el-icon-success" style="color: #409EFF;"></i>&nbsp;<el-tag size="mini">{{'允许通行'}}</el-tag></div>
								<div v-if="scope.row.regServerStatus==='1'"><i class="el-icon-error" style="color: #f00000;"></i>&nbsp;<el-tag size="mini" type="danger">{{'禁止通行'}}</el-tag></div>
							</template>
						</el-table-column>
						<el-table-column label="操作" width="80">
							<template slot-scope="scope">
								 <el-dropdown trigger="click" @command="handleCommandRegServer">
									<el-button size="mini" circle icon="el-icon-setting" title="设置" style="border: 0px;"></el-button>
								  <el-dropdown-menu slot="dropdown">
									<el-dropdown-item :command="{command:'start', row: scope.row}"><i class="el-icon-success" style="color: #409EFF;"></i>允许通行</el-dropdown-item>
									<el-dropdown-item :command="{command:'stop', row: scope.row}"><i class="el-icon-error" style="color: red;"></i>禁止通行</el-dropdown-item>
									<el-dropdown-item :command="{command:'delete', row: scope.row}" divided><i class="el-icon-delete"></i>移除</el-dropdown-item>
								  </el-dropdown-menu>
								</el-dropdown>
							</template>
						</el-table-column>
					</el-table>
					<div class="block" style="margin-top: 20px;">
						<el-pagination
							@size-change="handleSizeChange"
							@current-change="handleCurrentChange"
							:current-page="currentPage"
							:page-sizes="[10,  30, 50]"
							:page-size="pageSize"
							layout="total, sizes, prev, pager, next, jumper"
							:total="totalNum"
						></el-pagination>
					</div>

				</el-card>
			</el-col>
		</el-row>

	</div>
</template>

<script>
	import {addRegServer,deleteRegServer,stopRegServer,startRegServer,regServerPageList,startAllRegServer,stopAllRegServer,notRegServerPageList} from '@/api/regserver_api.js'

	export default {
		data() {
			return {
				dialogFormVisible: false,
				formLabelWidth: '100px',
				form:{},
				tableData: [],
				currentPage: 1,
				pageSize: 10,
				totalNum: 1,
				routeTableData: [],
				routeCurrentPage: 1,
				routePageSize: 10,
				routeTotalNum: 1,
			};
		},
		created: function() {
			//在组件创建完毕后加载
			let query = this.$route.query;
			if (query){
				let client = query.client;
				console.log('client', client);
				this.init(client);
			}
		},
		mounted: function() {

		},
		beforeDestroy: function() {

		},
		methods:{
			init(client) {
				if (client){
					this.form = client;
					this.regServerList();
				}
			},
			goBack() {
			    console.log('go back');
				this.$router.push({path:'/clientList',query:{}});
			},
			handleSizeChange(val) {
				this.pageSize = val;
				this.regServerList();
			},
			handleCurrentChange(val) {
				this.currentPage = val;
				this.regServerList();
			},
			handleRouteSizeChange(val) {
				this.routePageSize = val;
				this.routeList();
			},
			handleRouteCurrentChange(val) {
				this.routeCurrentPage = val;
				this.routeList();
			},
			handleCommandRegServer(obj){
				console.log("command" , obj);
				let _this = this;
				if (obj.command === 'start'){
					this.$confirm('确认允许当前客户端访问"'+obj.row.name+'"该注册服务？').then(_ => {
						startRegServer({id:obj.row.regServerId}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							_this.regServerList();
						});
					}).catch(_ => {});
				} else if (obj.command === 'stop'){
					this.$confirm('确认禁止当前客户端访问"'+obj.row.name+'"该注册服务？').then(_ => {
						stopRegServer({id:obj.row.regServerId}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							_this.regServerList();
						});
					}).catch(_ => {});
				} else if (obj.command === 'delete'){
					this.$confirm('确认删除"'+obj.row.name+'"该注册服务？').then(_ => {
						deleteRegServer({id:obj.row.regServerId}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							_this.regServerList();
						})
					}).catch(_ => {});
				}
			},
			handleAddRegServer(row){
				let _this = this;
				addRegServer({clientId: this.form.id, routeId: row.id}).then(function(result){
					_this.GLOBAL_FUN.successMsg();
					_this.regServerList();
					_this.routeList();
				});
			},
			startAll(){
				let _this = this;
				this.$confirm('确认要允许当前客户端访问所有已注册服务？').then(_ => {
					startAllRegServer({clientId: _this.form.id,}).then(function(result){
						_this.GLOBAL_FUN.successMsg();
						_this.regServerList();
					});
				}).catch(_ => {});
			},
			stopAll(){
				let _this = this;
				this.$confirm('确认要禁止当前客户端访问所有已注册服务？').then(_ => {
					stopAllRegServer({clientId: _this.form.id,}).then(function(result){
						_this.GLOBAL_FUN.successMsg();
						_this.regServerList();
					});
				}).catch(_ => {});
			},
			regServerList(){
				let _this = this;
				regServerPageList({clientId: this.form.id, currentPage: this.currentPage, pageSize: this.pageSize}).then(function(result){
					if (result.data){
						_this.tableData = result.data.lists;
						_this.totalNum = result.data.totalNum;
					}
				});
			},
			routeList(){
				let _this = this;
				notRegServerPageList({clientId: this.form.id, currentPage: this.routeCurrentPage, pageSize: this.routePageSize}).then(function(result){
					console.log(result);
					if (result.data && result.data.lists){
						_this.routeTableData = result.data.lists;
						_this.routeTotalNum = result.data.totalNum;
					}
				});
			},
			search(){
				this.dialogFormVisible = true;
				this.routeList();
			}
		}
	}
</script>

<style>
	.el-icon-setting:before {
	    content: "\E6CA";
	    font-size: 12pt;
	}
</style>

