<template>
	<div class="app-container">
		<el-page-header @back="goBack" content="客户端管理"></el-page-header>
		<el-row :gutter="20" style="margin-top: 20px;">
			<el-col>
				<el-card shadow="false" class="box-card">
					<div slot="header" class="clearfix">
						<span>客户端配置</span>
						<div style="float: right; margin-left: 10px;"><el-button icon="el-icon-delete" size="mini" type="warning" @click="resetForm">清 空</el-button></div>
						<div style="float: right; margin-left: 10px;"><el-button icon="el-icon-s-claim" size="mini" type="success" @click="submit">发 布</el-button></div>
					</div>

					<el-form size="small" :rules="rules" ref="form" :model="form" label-width="100px">
						<el-form-item label="名称" prop="name">
							<el-input v-model="form.name" style="width: 300px;" :disabled="nameDisabled"></el-input>
						</el-form-item>
						<el-form-item label="系统代号" prop="systemCode">
							<el-input v-model="form.systemCode" style="width: 300px;" :disabled="nameDisabled"></el-input>
						</el-form-item>
						<el-form-item label="分组" prop="groupCode">
							<el-select filterable v-model="form.groupCode" placeholder="请选择分组" style="width: 300px;">
								<el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value" />
							</el-select>
						</el-form-item>
						<el-form-item label="IP" prop="ip">
							<el-input v-model="form.ip" style="width: 300px;"></el-input>
						</el-form-item>
						<el-form-item label="状态" prop="status">
							<el-radio-group v-model="form.status">
								<el-radio label="0">启用</el-radio>
								<el-radio label="1">禁用</el-radio>
							</el-radio-group>
						</el-form-item>
						<el-form-item label="备注" prop="remarks">
							<el-input type="textarea" v-model="form.remarks" style="width: 500px;"></el-input>
						</el-form-item>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>
import {addClient,updateClient} from '@/api/client_api.js'

export default {
	data() {
		return {
			form: {
				id: null,
				name: null,
				systemCode: null,
				groupCode: null,
				ip: null,
				status: null,
				remarks: null
			},
			rules: {
				name: [
					{ required: true, message: '请输入客户端名称', trigger: 'blur' }, 
					{ min: 2, max: 40, message: '长度在 2 到 40 个字符', trigger: 'blur' },
				],
				systemCode: [
					{ required: true, message: '请输入客户端系统代号', trigger: 'blur' }, 
					{ min: 2, max: 40, message: '长度在 2 到 40 个字符', trigger: 'blur' },
				],
				groupCode: [
					{ required: true, message: '请选择分组', trigger: 'change' },
				],
				ip: [
					{ required: true, message: '请输入客户端IP', trigger: 'blur' }, 
					{ min: 8, max: 16, message: '长度在 8 到 16 个字符，如：0.0.0.0', trigger: 'blur' },
				],
				status: [
					{ required: true, message: '请选择状态', trigger: 'change' }
				],
				remarks: [
					{ min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' }
				]
			},
			handleType: 'add',
			nameDisabled: false,
			groupOptions: this.GLOBAL_VAR.groups
		};
	},
	created: function() {
		//在组件创建完毕后加载
		let query = this.$route.query;
		if (query){
			this.handleType = query.handleType;
			if (this.handleType === 'edit'){
				// this.nameDisabled = true;
				let client = query.client;
				console.log('client', client);
				this.init(client);
			}
		}
	},
	methods: {
		init(client) {
			if (client){
				this.form = client;
			}
		},
		goBack() {
			this.$router.push({ path: '/gateway/clientList', query: {} });
		},
		submit() {
			let _this = this;
			this.$refs['form'].validate((valid) => {
				if (valid) {
					console.log(_this.form);
					if (this.handleType === 'edit'){
						updateClient(_this.form).then(function(result){
							_this.GLOBAL_FUN.successMsg();
						});
					} else {
						addClient(_this.form).then(function(result){
							_this.GLOBAL_FUN.successMsg();
						});
					}
				} else {
					console.log('error submit!!');
					return false;
				}
			});
		},
		resetForm() {
			this.form = {
				id: this.handleType === 'edit'?this.form.id:null,
				name: this.handleType === 'edit'?this.form.name:null,
				groupCode: null,
				ip: null,
				status: null,
				remarks: null
			};
		}
	}
};
</script>

<style>
</style>
