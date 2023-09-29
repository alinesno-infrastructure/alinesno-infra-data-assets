<template>
	<div class="app-container">
		<el-page-header @back="goBack" content="网关路由规则组件"></el-page-header>
		<el-dialog title="添加规则组件" :visible.sync="dialogFormVisible" width="70%" :close-on-click-modal="false">
			<el-form :model="groovyForm" status-icon :rules="rules" ref="groovyForm" label-width="100px" size="mini">
				<el-form-item label="组件名称" prop="name">
					<el-input v-model="groovyForm.name" autocomplete="off" placeholder="请输入组件名称"></el-input>
				</el-form-item>
				<el-form-item label="扩展参数" prop="extendInfo">
					<el-input v-model="groovyForm.extendInfo" autocomplete="off" placeholder="请输入组件参数，建议使用json格式"></el-input>
				</el-form-item>
				<el-form-item label="执行事件" prop="event">
					<!-- <el-input v-model="groovyForm.event" autocomplete="off"></el-input> -->
					<el-radio v-model="groovyForm.event" label="request">
						<el-tag size="small" effect="plain">REQUEST</el-tag>
					</el-radio>
  					<el-radio v-model="groovyForm.event" label="response">
						  <el-tag size="small" type="success" effect="plain">RESPONSE</el-tag>
					  </el-radio>
				</el-form-item>
				<el-form-item label="组件代码" prop="content">
					<el-input type="textarea" :rows="25" v-model="groovyForm.content" placeholder="请输入内容"></el-input>
				</el-form-item>
				<el-form-item label="备注" prop="remarks">
					<el-input v-model="groovyForm.remarks" autocomplete="off"></el-input>
				</el-form-item>
				
				<el-form-item label="编译说明">
					<el-tag type="warning">组件代码采用GroovyScipt动态脚本语言，无缝支持java语法和包</el-tag> &nbsp;&nbsp;
					<el-link type="primary" @click="showGroovyScriptCode">导入GroovyScipt示例代码</el-link> &nbsp;&nbsp;&nbsp;&nbsp; 
					<el-link v-show="submitVisible" icon="el-icon-upload2" type="primary"> 提交中 >  </el-link> 
					<el-link v-show="submitVisible" icon="el-icon-loading" type="primary"> 编译中 >  </el-link>
					<el-link v-show="successVisible" icon="el-icon-finished" type="success"> 编译成功 </el-link>
					<el-link v-show="failVisible" icon="el-icon-close" type="danger" @click="showLog"> 编译失败  查看失败日志 </el-link>
				</el-form-item>
			</el-form>
			<el-dialog width="65%" title="编译失败日志" :visible.sync="innerVisible" append-to-body>
<pre class="language-java line-numbers" style="height: 500px;"><code>{{codeErrorMsg}}</code></pre>
				<div slot="footer" class="dialog-footer">
					<el-button icon="el-icon-s-release" size="mini" type="warning" @click="innerVisible = false">关 闭</el-button>
				</div>
			</el-dialog>
			<div slot="footer" class="dialog-footer">
				<el-button icon="el-icon-s-claim" size="mini" type="success" @click="submitForm">提 交</el-button>
				<el-button icon="el-icon-s-release" size="mini" type="warning" @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<el-dialog title="查看组件代码" :visible.sync="dialogCodeVisible" width="60%" :close-on-click-modal="false">
			<div >
<pre class="language-java line-numbers" style="height: 700px;"><code v-html="groovScriptCode"></code></pre>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button icon="el-icon-s-release" size="mini" type="warning" @click="dialogCodeVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		
		<el-row :gutter="20" style="margin-top: 20px;">
			<el-col>
				<el-card shadow="false" class="box-card">
					<div slot="header" class="clearfix">
						<span>规则组件</span>
						<el-popover trigger="click" placement="bottom">
							<div style="font-size: 10pt;">
								<span>添加规则组件说明：</span><br/>
								<span>1. 开启路由服务端规则组件后，客户端请求到网关路由后，会进入规则组件过滤器。</span><br/>								
								<span>2. 规则组件过滤器根据添加的GroovyScript组件排序一一触发执行，当顺序执行组件过程抛出任意异常，则打断请求进程，直接返回客户端提示。</span><br/>
								<span>3. 新添加的路由服务端规则组件后，默认为关闭状态，请手动开启后，立即生效。</span><br/>
								<span>4. 路由服务端规则组件代码采用GroovyScipt动态脚本语言，无缝支持java语法和包。</span><br/>
								<span>5. 路由服务端规则组件适用场景为，解决网关代理调用路由服务端之前，增加业务规则较验，如：签名较验、数据加签、参数验证、格式化处理、权限判断等。</span><br/>
							</div>
							<el-button slot="reference" style="padding: 3px 0;" icon="el-icon-question" type="text" title="说明"></el-button>
						</el-popover>
						<span style="margin-left: 50px;">
							<i class="el-icon-monitor"></i> 
							<span style="font-size: 11pt;">
								<el-tag size="mini" style="font-weight: bold;">{{routeForm.name}}</el-tag> 
							</span>	
						</span>
						<div style="float: right; margin-left: 10px;">
						    <el-button icon="el-icon-circle-plus-outline" size="small" type="success" @click="addRule" title="查找服务端"> 添加组件脚本 </el-button>
						</div>
					</div>

					<el-tabs v-model="activeName" tab-position="left" @tab-click="handleClickTabs" style="height: 600px;">
						<el-tab-pane name="request">
							<span slot="label"><i class="el-icon-position" style="color:#409EFF;"></i> REQUEST</span>
							<!-- 父组件传参与子组件方法监听 -->
							<groovyScriptTableComponent ref="groovyScriptTable" 
								:tableData="requestTableData" 
								@queryCode="queryCode" 
								@handleCommandRegServer="handleCommandRegServer"/>
						</el-tab-pane>
						<el-tab-pane name="response">
							<span slot="label"><i class="el-icon-s-promotion" style="color:#67C23A;"></i> RESPONSE</span>
							<!-- 父组件传参与子组件方法监听 -->
							<groovyScriptTableComponent ref="groovyScriptTable" 
								:tableData="responseTableData" 
								@queryCode="queryCode" 
								@handleCommandRegServer="handleCommandRegServer"/>
						</el-tab-pane>
					</el-tabs>
					
				</el-card>
			</el-col>
		</el-row>
		
	</div>
</template>

<script>
	
	import groovyScriptTableComponent from '@/components/GroovyScriptTable.vue'
	import {deleteGroovyScript,stopGroovyScript,startGroovyScript,upGroovyScript,downGroovyScript,groovyScriptList,groovyScriptCode} from '@/api/groovyscript_api'

	export default {
		data() {
			return {
				innerVisible: false,
				dialogCodeVisible: false,
				dialogFormVisible: false,
				formLabelWidth: '100px',
				handleType: null,
				routeForm:{},
				groovyForm:{
					name: '',
					extendInfo: '',
					event: 'request',
					content: '',
					remarks: ''
				},
				rules:{
					name: [
						{ required: true, message: '请输入组件名称', trigger: 'blur' },
						{ min: 3, max: 40, message: '长度在 3 到 40 个字符', trigger: 'blur' }
					],
					content: [
						{ required: true, message: '请输入组件代码', trigger: 'blur' }
					],
				},
				tableData: [],
				requestTableData: [],
				responseTableData: [],
				submitVisible: false,
				successVisible: false,
				failVisible: false,
				groovScriptCode: '',
				codeErrorMsg: '',
				activeName: 'request'
			};
		},
		components:{
			groovyScriptTableComponent
		},
		created: function() {
			//在组件创建完毕后加载
			let query = this.$route.query;
			if (query){
				let route = query.route;
				console.log('route', route);
				this.init(route);
			}
		},
		mounted: function() {
		
		},
		beforeDestroy: function() {
			
		},
		methods:{
			init(route) {
				if (route){
					this.routeForm = route.form;
					this.groovyScriptList();
				}
			},
			goBack() {
			    console.log('go back');
				this.$router.push({path:'/gatewayList',query:{}});
			},
			handleCommandRegServer(obj){
				console.log("command" , obj);
				let _this = this;
				this.handleType = obj.command;
				if (obj.command === 'edit'){
					this.groovyForm = JSON.parse(JSON.stringify(obj.row)); 
					this.resetVisible();
					this.dialogFormVisible = true;
				} else if (obj.command === 'start'){
					this.$confirm('确认开启"'+obj.row.name+'"规则引擎动态脚本组件？').then(_ => {
						startGroovyScript({id:obj.row.id}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							obj.row.status='0';
						});
					}).catch(_ => {});
				} else if (obj.command === 'stop'){
					this.$confirm('确认关闭"'+obj.row.name+'"规则引擎动态脚本组件？').then(_ => {
						stopGroovyScript({id:obj.row.id}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							obj.row.status='1';
						});
					}).catch(_ => {});
				} else if (obj.command === 'up'){
					let rowObj = obj.row;
					if (rowObj && rowObj.orderNum === 1){
						_this.GLOBAL_FUN.successMsg('已经是排序第一位');
						return false;
					}
					this.$confirm('确认上移"'+rowObj.name+'"规则引擎动态脚本组件？').then(_ => {
						upGroovyScript({id:rowObj.id}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							_this.groovyScriptList();
						});
					}).catch(_ => {});
				} else if (obj.command === 'down'){
					let rowObj = obj.row;
					let maxRow = this.tableData[this.tableData.length -1]
					if (rowObj && maxRow && rowObj.id === maxRow.id){
						_this.GLOBAL_FUN.successMsg('已经是排序最后一位');
						return false;
					}
					this.$confirm('确认下移"'+rowObj.name+'"规则引擎动态脚本组件？').then(_ => {
						downGroovyScript({id:rowObj.id}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							_this.groovyScriptList();
						});
					}).catch(_ => {});
				} else if (obj.command === 'delete'){
					this.$confirm('确认删除"'+obj.row.name+'"规则引擎动态脚本组件？').then(_ => {
						deleteGroovyScript({id:obj.row.id}).then(function(result){
							_this.GLOBAL_FUN.successMsg();
							_this.groovyScriptList();
						})
					}).catch(_ => {});
				}
			},
			queryCode(row){		
				this.groovScriptCode = row.content;
				this.dialogCodeVisible = true;
				setTimeout(()=>Prism.highlightAll(),50);
			},
			addRule(){
				this.resetVisible();
				if (this.$refs['groovyForm']){
					this.$refs['groovyForm'].resetFields();
				}
				this.handleType = 'add';
				this.groovyForm = this.$options.data().groovyForm;
				this.dialogFormVisible = true;
			},
			submitForm(){
				console.log('handleType', this.handleType);
				let _this = this;				
				this.$refs['groovyForm'].validate((valid) => {
					if (valid) {		
						this.submitVisible = true;
						this.failVisible = false;
						this.successVisible = false;
						this.groovyForm.routeId = this.routeForm.id;
						let path = this.handleType == 'add' ? '/groovyScript/add' : '/groovyScript/update' ;
						this.$ajax
						.post(this.GLOBAL_VAR.baseURL + path, this.groovyForm)
						.then((result) => {
							if (result && result.data) {
								if (result.data.code == '1') {
									_this.successVisible = true;
									_this.GLOBAL_FUN.successMsg();
									_this.groovyScriptList();
								}
							}
						})
						.catch((error) => { // 请求失败处理
							if (error && error.response) {
								_this.failVisible = true;
								if (error.response.data){
									_this.codeErrorMsg = error.response.data.msg;
									_this.GLOBAL_FUN.errorMsg("操作失败,请查看失败日志!");									
								}else {
									_this.GLOBAL_FUN.errorMsg("操作失败!");
								}
							} 
						});
					}
				});
			},
			showLog(){ //显示日志
				this.innerVisible = true;
				setTimeout(()=>Prism.highlightAll(),50);
			},
			resetVisible(){ //重置弹窗标识
				this.submitVisible = false;
				this.successVisible = false;
				this.failVisible = false;
			},
			handleClickTabs(tab, event){
				console.log(tab, event);
				if (tab.index === '0'){
					this.tableData = this.requestTableData;
				} else {
					this.tableData = this.responseTableData;
				}
			},
			showGroovyScriptCode(){//导入示例代码
				this.groovyForm.content = groovyScriptCode();
			},
			groovyScriptList(){
				let _this = this;
				groovyScriptList({routeId: this.routeForm.id}).then((result) => {
					if (result.data){
						_this.requestTableData = [];
						_this.responseTableData = [];
						result.data.forEach((data, index)=>{
							if (data.event == 'request'){
								_this.requestTableData.push(data);
							} else {
								_this.responseTableData.push(data);
							}
						});
					}
				});
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


