<template>
	<div class="app-container">
		<el-page-header @back="goBack" content="负载管理"></el-page-header>
		<el-card shadow="false" class="box-card" style="margin-top: 20px;">
			<div slot="header" class="clearfix">
				<span>负载管理配置</span>
				<el-popover trigger="click" placement="bottom">
					<div style="font-size: 10pt;">
						<span>配置说明：</span><br/>
						<span>1. 名称不能重复。</span><br/>
						<span>2. 路径不能为空，路径规则需要满足gateway断言Path规则，请根据实际API调用路径设置。</span><br/>
						<span>3. 路径/parent/为默认根路径，不可更改，只做网关路由规则识别，用于区分负载路由路径与普通路由路径。</span><br/>
						<span>4. 实际转发到服务端不包含该/parent/根路径。</span><br/>
						<span>5. 网关转发路由规则：http://gateway_host:gateway_port/server-id/api 或 http://gateway_host:gateway_port/api。</span><br/>
					</div>
					<el-button slot="reference" style="padding: 3px 0; " icon="el-icon-question" type="text" title="说明"></el-button>
				</el-popover>
				<div style="float: right; margin-left: 10px;">
					<el-button icon="el-icon-delete" size="small" type="warning" @click="resetForm">清 空</el-button>
				</div>
				<div style="float: right; margin-left: 10px;">
					<el-button icon="el-icon-s-claim" size="small" type="success" @click="submit">发 布</el-button>
				</div>
				<div style="float: right; margin-left: 10px;">
					<el-button icon="el-icon-connection" size="small" type="primary" @click="search" title="查找服务端">添加服务</el-button>
				</div>
			</div>

			<el-dialog title="添加服务端" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
				<el-table size="mini" :data="routeTableData" style="width: 100%">
					<el-table-column label="服务ID" prop="routeId"></el-table-column>
					<el-table-column label="服务名称" prop="name"></el-table-column>
					<el-table-column label="服务地址" prop="uri"></el-table-column>
					<el-table-column label="操作" width="60">
						<template slot-scope="scope">
							<el-button v-if="scope.row.selected !== true" size="mini" circle icon="el-icon-plus" type="success" title="点击添加" @click="handleAddRegServer(scope.row)"></el-button>
							<el-button v-if="scope.row.selected === true" size="mini" circle icon="el-icon-check" type="primary" title="已添加" @click="handleAddRegServer(scope.row)"></el-button>
						</template>
					</el-table-column>
				</el-table>
				<div class="block" style="margin-top: 20px;">
					<el-pagination
						@size-change="handleRouteSizeChange"
						@current-change="handleRouteCurrentChange"
						:current-page="currentPage"
						:page-sizes="[10,  30, 50]"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="totalNum">
					</el-pagination>
				</div>
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFormVisible = false" size="small">关 闭</el-button>
				</div>
			</el-dialog>

			<el-row :gutter="24">
				<el-col :span="8">
					<el-card shadow="false" class="box-card">
						<el-form size="small" :rules="rules" ref="form" :model="form" label-width="70px">
							<el-form-item label="名称" prop="name">
								<el-input v-model="form.name" style="width: 60%;"></el-input>
							</el-form-item>
							<el-form-item label="分组" prop="groupCode">
								<el-select filterable v-model="form.groupCode" placeholder="请选择分组" style="width: 60%;">
									<el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value" />
								</el-select>
							</el-form-item>
							<el-form-item label="状态" prop="status">
								<el-radio-group v-model="form.status">
									<el-radio label="0">启用</el-radio>
									<el-radio label="1">禁用</el-radio>
								</el-radio-group>
							</el-form-item>
							<el-form-item label="路径" prop="loadUri">
								<el-row :gutter="20">
									<el-col :span="4" style="border: 0px solid red; padding: 0;">
										<el-input v-model="parent" :disabled="true"  title="默认负载地址前缀'/parent/',请不要在自定义地址前加/"></el-input>
									</el-col>
									<el-col :span="19" style="border: 0px solid red; padding: 0;">
										<el-input v-model="form.loadUri" :disabled="uriDisabled" placeholder="请输入网关Path,示例：producer/** 或 producer/api"></el-input>
									</el-col>
								</el-row>
							</el-form-item>
							<el-form-item label="备注" prop="remarks">
								<el-input type="textarea" v-model="form.remarks"></el-input>
							</el-form-item>
						</el-form>
					</el-card>
				</el-col>
				<el-col :span="16">
					<el-card shadow="false" class="box-card">
						<el-table size="small" :data="tableData" style="width: 100%">
						  <el-table-column prop="settingId" label="路由ID"></el-table-column>
						  <el-table-column prop="name" label="路由名称"></el-table-column>
						  <el-table-column prop="uri" label="路由地址"></el-table-column>
						  <el-table-column label="路由权重" width="80">
							  <template slot-scope="scope">
								  <el-tag size="small" type="" style="font-weight: bold;">{{scope.row.weight}}</el-tag>
							  </template>
						  </el-table-column>
						  <el-table-column label="配置比例">
							  <template slot-scope="scope">
							    <div class="block">
							      <el-slider v-model="scope.row.weight"></el-slider>
							    </div>
							  </template>
						  </el-table-column>
						  <el-table-column label="操作" width="60">
						  	<template slot-scope="scope">
						  		<el-button size="mini" circle icon="el-icon-delete" title="删除" style="border: 0px; font-size: 12pt;" @click="handleDeleteServer(scope.row)"></el-button>
						  	</template>
						  </el-table-column>
						</el-table>
					</el-card>
				</el-col>
			</el-row>
		</el-card>
	</div>
</template>

<script>

	import {addBalanced, updateBalanced, loadServerRegList, loadServerNotRegList} from '@/api/balanced_api.js'

	export default {
		data() {
			return {
				dialogFormVisible: false,
				form: {
					id: null,
					name:null,
					groupCode:null,
					loadUri:null,
					status: '0',
					remarks: null
				},
				rules: {
					name: [
						{ required: true, message: '请输入负载名称', trigger: 'blur' },
						{ min: 2, max: 40, message: '长度在 2 到 40 个字符', trigger: 'blur' },
					],
					groupCode: [
						{ required: true, message: '请选择分组', trigger: 'change' },
					],
					loadUri: [
						{ required: true, message: '请输入负载地址', trigger: 'blur' },
						{ min: 2, max: 160, message: '长度在 2 到 160 个字符，如：/test.html', trigger: 'blur' },
					],
					status: [
						{ required: true, message: '请选择状态', trigger: 'change' }
					],
					remarks: [
						{ required: false, message: '请填写备注', trigger: 'blur' },
						{ min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' },
					]
				},
				parent: '/parent/',
				handleType: 'add',
				uriDisabled: false,
				groupOptions: this.GLOBAL_VAR.groups,
				currentPage: 1,
				pageSize: 10,
				totalNum: 1,
				routeTableData: [],
				tableData: []
			};
		},
		created: function() {
			//在组件创建完毕后加载
			let query = this.$route.query;
			if (query){
				let handleType = query.handleType;
				if (handleType === 'edit'){
					let balanced = query.balanced;
					console.log('balanced', balanced);
					this.form = balanced;
					this.handleType = handleType;
				}
			}
		},
		mounted: function() {
			this.init();
		},
		beforeDestroy: function() {

		},
		methods: {
			init(balanced) {
				this.serverRegList();
			},
			goBack() {
				this.$router.push({ path: '/gateway/loadBalanced', query: {} });
			},
			handleRouteSizeChange(val) {
				this.currentPage = val;
				this.addServerList();
			},
			handleRouteCurrentChange(val) {
				this.currentPage = val;
				this.addServerList();
			},
			handleAddRegServer(row){
				let isExist = false;
				this.tableData.forEach((_row, _index)=>{
					if (_row.routeId === row.id){
						isExist = true;
					}
				});
				if (isExist){
					return false;
				}
				row.selected = true;
				console.log(row);
				let server = {
					id: null,
					balancedId: this.form.id,
					routeId: row.id,
					name: row.name,
					uri: row.uri,
					weight: 0
				}
				this.tableData.push(server);
			},
			handleDeleteServer(row){
				let data = [];
				this.tableData.forEach((_row, _index)=>{
					if (_row.routeId !== row.routeId){
						data.push(_row);
					}
				});
				this.tableData = data;
			},
			addServerList(){
				let _this = this;
				loadServerNotRegList({balancedId: this.form.id, currentPage: this.currentPage, pageSize: this.pageSize}).then(function(result){
					if (result.data && result.data.lists){
						let lists = result.data.lists;
						//将已被选中的服务置为已选中图标
						if (lists != null){
							lists.forEach((row,index)=>{
								let selected = false;
								_this.tableData.forEach((_row, _index)=>{
									if (_row.routeId == row.id){
										selected = true;
									}
								});
								row.selected = selected;
							});
						}
						_this.routeTableData = lists;
						_this.totalNum = result.data.totalNum;
					}
				});
			},
			serverRegList(){
				if (this.form.id == null || this.form.id == undefined){
					this.tableData=[];
					return false;
				}
				let _this = this;
				loadServerRegList({balancedId: this.form.id}).then(function(result){
					console.log(result);
					if (result && result.data){
						_this.tableData = result.data;
					}
				});
			},
			submit(){
				let _this = this;
				let balanced = this.form;
				balanced.serverList = this.tableData;
				console.log(balanced);
				this.$refs['form'].validate((valid) => {
					if (valid) {
						if (this.handleType === 'edit'){
							updateBalanced(balanced).then(function(result){
								_this.GLOBAL_FUN.successMsg();
								_this.goBack();
							});
						}else {
							addBalanced(balanced).then(function(result){
								_this.GLOBAL_FUN.successMsg();
								_this.goBack();
							});
						}
					}
				});
			},
			search(){
				this.dialogFormVisible = true;
				//如果上次已查询，则直接返回页面缓存数据
				if (this.routeTableData == null || this.routeTableData.length <= 0){
					this.addServerList();
					this.handleDelete = false;
				}
			},
			resetForm(){
				if (this.handleType === 'add'){
					this.form = {
						id: null,
						name:null,
						groupCode:null,
						loadUri:null,
						status: '0',
						remarks: null
					}
				}
			}
		}
	}

</script>

<style>
</style>
