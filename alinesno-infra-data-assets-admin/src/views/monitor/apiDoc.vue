<template>
	<div class="app-container">
		<el-row :gutter="18">
			<el-col :span="4">
				<el-card shadow="false" >
					<span style="font-size: 18pt; font-weight: bold;">接口文档</span>
					<el-tree :data="apiTreeData" :props="defaultProps" @node-click="handleNodeClick" :expand-on-click-node="false" style="margin-top: 20px;">
						<span slot-scope="{ node, data }">							
							<span style="font-size: 10pt;">{{ node.label }}</span>							
							<span>
							  <el-button size="mini" circle icon="el-icon-edit" title="编辑" 
							  style="border: 0px; margin-left: 0px; font-size: 10pt; padding: 2px;" 
							  @click="() => handleNodeEdit(data)" :disabled="handleType=='edit' && id != data.id" ></el-button>
							  <el-button v-show="isNotGroups(node.data.code)" size="mini" circle icon="el-icon-view" 
							  title="详情" style="border: 0px; margin-left: 0px; font-size: 10pt; padding: 2px;" 
							  @click="() => handleNodeView(data)"></el-button>
							</span>
						</span>
					</el-tree>
				</el-card>
			</el-col>
			<el-col :span="20">
				<!-- 说明 -->
				<el-card shadow="false">
					<div class="clearfix">
						<i class="el-icon-monitor"></i> 
						<span style="font-weight: bold;">{{infoForm.name}}</span>						
						<span v-show="infoForm.isItem" style="margin-left: 50px;">
							<i class="el-icon-position"></i> 
							<span style="font-size: 11pt;">
								<el-tag size="mini" style="font-weight: bold;">{{infoForm.id}}</el-tag>
							</span>
						</span>
						<span v-show="infoForm.isItem" style="margin-left: 30px;">
							<i class="el-icon-link"></i> 
							<span style="font-size: 11pt;">
								<el-tag size="mini" type="success" style="font-weight: bold;">{{infoForm.path}}</el-tag>
							</span>
						</span>	
						<span v-show="infoForm.isItem" style="margin-left: 30px;">
							<i class="el-icon-connection"></i> 
							<span style="font-size: 11pt;">
								<el-tag size="mini" type="success" style="font-weight: bold;">{{infoForm.uri}}</el-tag>
							</span>
						</span>	
					</div>
				</el-card>
				<!-- 在线markdown编辑器 https://gitee.com/wCHCw/mavonEditor -->
				<span v-show="handleType=='edit'">
					<mavon-editor v-show="handleType=='edit'" v-model="content" :toolbars="toolbars" @save="handleSave" style="height: 700px; margin-top: 18px;" />	
					<!-- 提交 -->
					<el-button v-show="handleType=='edit'" type="primary" style="margin-left: 47%; margin-top: 20px;" @click="handleSave">保存</el-button>
				</span>
				
				<span v-show="handleType==''"> 
					<mavon-editor v-show="handleType==''" v-model="content" :subfield="false" :defaultOpen="'preview'" :toolbarsFlag="false" 
					:editable="false" :scrollStyle="true" :ishljs="true" style="margin-top: 18px; " />
				</span>
			</el-col>
		</el-row>
		
		<el-drawer :visible.sync="drawer" :direction="direction" :before-close="handleClose" :with-header="false" size="24%">
			<!-- 父组件传参与子组件方法监听 -->
			<routeInfoComponent ref="routeInfo" :infoForm="infoForm"></routeInfoComponent>
		</el-drawer>
	</div>
</template>

<script>
	
import {apiDocList,saveApiDoc,findByApiDoc} from '@/api/apidoc_api.js'
import routeInfoComponent from '@/components/RouteInfo.vue'
	
export default {
	data() {
		return {
			id:'',
			handleType: '',
			isEdit: false,
			drawer: false,
			direction: 'rtl',
			infoForm:{
				name:'API接口'
			},	
			tableData: [],
			content: '', //输入的数据
			toolbars: {
				bold: true, // 粗体
				italic: true, // 斜体
				header: true, // 标题
				underline: true, // 下划线
				mark: true, // 标记
				superscript: true, // 上角标
				quote: true, // 引用
				ol: true, // 有序列表
				link: true, // 链接
				imagelink: true, // 图片链接
				help: true, // 帮助
				code: true, // code
				table: true, // 表格
				subfield: true, // 是否需要分栏
				fullscreen: true, // 全屏编辑
				readmodel: true, // 沉浸式阅读
				/* 1.3.5 */
				undo: true, // 上一步
				trash: true, // 清空
				save: true, // 保存（触发events中的save事件）
				/* 1.4.2 */
				navigation: true ,// 导航目录
				editable: true,
				preview: true, // 预览
				defaultOpen: 'preview' //edit： 默认展示编辑区域 ， preview： 默认展示预览区域 
			},
			apiTreeData: [],
			defaultProps: {
			  children: 'children',
			  label: 'label'
			}
		};
	},
	created: function() {
		this.id = '';
		this.handleType = '';		
		this.init();
	},
	components:{
		routeInfoComponent
	},
	mounted: function() {},
	beforeDestroy: function() {},
	methods: {
		init() {
			this.list();
		},
		list(){
			let _this = this;
			apiDocList().then(function(result){//获取tree数据
				let data = result.data;
				_this.tableData = data;
				if (data){
					let treeData = []
					let i = 1;
					_this.GLOBAL_VAR.groups.forEach((row,index)=>{
						treeData.push({id:'' + i, code:row.value, pcode:'', name: row.label});
						i ++;
					})					
					data.forEach((row,index)=>{
						treeData.push({id:row.id, code:row.id, pcode:row.groupCode, name: row.name});
					})
					_this.apiTreeData = _this.GLOBAL_FUN.initTree(treeData);
				}
			});
		},
		isNotGroups(groupCode){//过滤父级接口的编辑和查看按钮
			let groups = this.GLOBAL_VAR.groups;
			let length = groups.length;
			for (var i=0;i<length;i++){
				let itme = groups[i];
				if (itme.value === groupCode){
					return false;
				}
			}
			return true;
		},
		handleNodeClick(data){//点击节点，判断是否在编辑
			let oldhandleType = this.handleType;
			if (this.isEdit === true){
				this.handleType = 'edit';
			}
			if (this.handleType === 'edit'){
				if (oldhandleType === 'edit'){
					if (this.id == data.id){
						return true;
					}
					let _this = this;
					this.$confirm('当前正在编辑文档内容，如未保存将会丢失已编辑内容，请确认是否切换？').then(_ => {
						if (this.isEdit != true){
							_this.handleType = '';
						}					
						_this.info(data);
					}).catch(_ => {});
				}else {
					this.info(data);
				}				
			}else {
				if (this.id == data.id){
					return true;
				}
				this.handleType = '';
				this.info(data);
			}
			this.isEdit = false;
		},
		info(data){//点击tree节点，获取节点信息
			if (this.isNotGroups(data.code)){
				this.infoForm = this.getTableDataItem(data.id);
				this.infoForm.isItem = true;
			} else {
				this.infoForm = {
					id: data.id,
					name:data.label,
					isItem:false
				}
			}
			this.getApiDoc(data.id);
		},
		handleNodeEdit(data){//编辑
			this.isEdit = true;
		},
		handleNodeView(data){//查看接口详情
		    this.drawer = true;
		    this.infoForm = this.getTableDataItem(data.id);
		},
		handleClose(done) {//关闭详情
			// this.$confirm('确认关闭？').then(_ => {
				done();
			// }).catch(_ => {});
		},
		handleSave(){
			let isSubmit = true;
			if (this.content == null || this.content == undefined || this.content == '' || this.content.length <= 0){
				isSubmit = false;
				this.$confirm('文档内空是空的，请确认是否提交？').then(_ => {
					this.save()
				}).catch(_ => {});
			}else {
				this.save()
			}
		},
		getTableDataItem(id){//获取数组中的指定ID对象
			let routeData = {};
			let length = this.tableData.length;
			for (var i=0;i<length;i++){
				let item = this.tableData[i];
				if (id === item.id){
					routeData = item;
					break;
				}
			}
			return routeData;
		},
		save(){//保存文档
			let _this = this;
			saveApiDoc({id:this.infoForm.id, content: this.content}).then(function(result){
				console.log(result);
				_this.handleType = '';
				_this.GLOBAL_FUN.successMsg();
			});
		},
		getApiDoc(id){//获取文档
			this.content = '';
			this.id = id;
			let _this = this;		
			findByApiDoc({id:id}).then(function(result){
				if (result.data){
					_this.content = result.data.content;
				}
				
			});
		}
	}
};
</script>

<style>

.markdown-body{
	box-shadow: none !important;
	border: 1px solid #EBEEF5 !important ;
}

	.el-drawer__header {
	    -ms-flex-align: center;
	    align-items: center;
	    color: #72767b;
	    display: -ms-flexbox;
	    display: flex;
	    margin-bottom: 2px;
	    padding: 10px 20px 0;
	}
	.input-with-select .el-input-group__prepend {
	    background-color: #fff;
	 }
	 .gateway-info-label{
		 text-align: right; 
		 font-size: 14px; 
		 padding: 8px 0; 
		 color: #606266; 
		 vertical-align: middle;
	 }
	 .gateway-info-value{
		 font-size: 14px; 
		 padding: 8px 0px ; 
		 line-height: 20px;
	 }
	 .v-show-content {
		 background-color: white !important;
	 }
</style>
