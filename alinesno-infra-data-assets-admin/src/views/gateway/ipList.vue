<template>
	<div class="app-container">
		<el-dialog title="IP管理" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">
			<el-form :model="form" ref="form" :label-width="formLabelWidth" :rules="rules">
				<el-form-item label="IP" size="mini" prop="ip">
					<el-input v-model="form.ip" autocomplete="off" :maxlength="15" style="width: 240px;"></el-input>
				</el-form-item>
				<el-form-item label="状态" size="mini" prop="status">
					<el-radio v-model="form.status" label="0">允许通行</el-radio>
					<el-radio v-model="form.status" label="1">禁止通行</el-radio>
				</el-form-item>
				<el-form-item label="备注" size="mini" prop="remarks">
					<el-input type="textarea" :rows="3" v-model="form.remarks" :maxlength="200" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button icon="el-icon-s-claim" size="mini" type="success" @click="submit">提 交</el-button>
				<el-button icon="el-icon-s-release" size="mini" type="warning" @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<el-card shadow="false" class="box-card">
			<el-row>
				<el-col :span="10">
					<div style="margin-bottom: 9px;">
						<span style="font-size: 18pt;font-weight: bold; ">IP名单管理</span>
					</div>
				</el-col>
				<el-col :span="14">
					<div style="float: right; margin-left: 10px;">
						<el-button icon="el-icon-folder-add" type="primary" @click="handleCreateIp"></el-button>
					</div>
					<div style="float: right;">
						<el-input placeholder="请输入IP" v-model="ip" :maxlength="15" class="input-with-select" style="width: 520px;" clearable>
							<el-select v-model="status" slot="prepend" placeholder="请选择" style="width: 120px;">
								<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
							</el-select>
							<el-button slot="append" icon="el-icon-search" @click="search"></el-button>
						</el-input>
					</div>
				</el-col>
			</el-row>
			<el-table size="small" :data="tableData" style="width: 100%">
				<el-table-column label="IP地址" prop="ip">
					<template slot-scope="scope">
						<el-tag size="small" type="success" style="font-weight: bold;">{{scope.row.ip}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="状态" prop="status">
					<template slot-scope="scope">
						<el-tag effect="dark" size="small" v-if="scope.row.status === '0'" type="">允许通行</el-tag>
						<el-tag effect="dark" size="small" v-if="scope.row.status === '1'" type="danger">禁止通行</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="创建时间" prop="createTime"></el-table-column>
				<el-table-column label="备注" prop="remarks"></el-table-column>
				<el-table-column label="操作" width="160">
					<template slot-scope="scope">
						<el-dropdown trigger="click" @command="handleCommandClient">
						   <el-button size="mini" type="warning">
						      管理<i class="el-icon-arrow-down el-icon--right"></i>
						    </el-button>
						  <el-dropdown-menu slot="dropdown">									
							<el-dropdown-item :command="{command:'start', row: scope.row}"><i class="el-icon-success" style="color: #409EFF;"></i>允许通行</el-dropdown-item>
							<el-dropdown-item :command="{command:'stop', row: scope.row}"><i class="el-icon-error" style="color: red;"></i>禁止通行</el-dropdown-item>
							<el-dropdown-item icon="el-icon-delete-solid" :command="{command:'delete', row: scope.row}" divided>删除</el-dropdown-item>
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
					:page-sizes="[10, 30, 50]"
					:page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="totalNum"
				></el-pagination>
			</div>
		</el-card>
	</div>
</template>

<script>
import { addIp, updateIp, deleteIp, ipPageList } from '@/api/ip_api.js';

var is_ip=function(strIP){if(!strIP && strIP!==0 && typeof strIP!=="boolean"){return false}var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;if(re.test(strIP)){if(RegExp.$1<256&&RegExp.$2<256&&RegExp.$3<256&&RegExp.$4<256){return true}}return false};

export default {
	data() {
		return {
			dialogFormVisible: false,
			formLabelWidth: '60px',
			status: null,
			ip: '',
			form: {
				ip: '',
				status: '0',
				remarks: ''
			},
			tableData: [],
			currentPage: 1,
			pageSize: 10,
			totalNum: 0,
			options: [{ value: '', label: '所有' }, { value: '0', label: '允许通行' }, { value: '1', label: '禁止通行' }],
			rules: {
				ip: [
					{ required: true, message: '请输入IP地址', trigger: 'blur' }, 
					{ min: 4, max: 15, message: '长度在 4 到 15 个字符', trigger: 'blur' },
				],
				status: [
					{ required: true, message: '请选状态', trigger: 'change' },
				],
				remarks: [
					{ required: false }, 
					{ min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' },
				]
			}
		};
	},
	created: function() {
		this.init();
	},
	mounted: function() {},
	beforeDestroy: function() {},
	methods: {
		init() {
			this.search();
			console.log(JSON.stringify(this.tableData));
		},
		handleSizeChange(val) {
			this.pageSize = val;
		},
		handleCurrentChange(val) {
			this.currentPage = val;
		},
		handleCommandClient(obj){
			console.log("command" , obj);
			let _this = this;
			if (obj.command === 'start'){
				if (obj.row.status === '1'){
					obj.row.status='0';
					updateIp(obj.row).then(function(result){
						_this.GLOBAL_FUN.successMsg();
						_this.search();
					});
				}
			} else if (obj.command === 'stop'){
				if (obj.row.status === '0'){
					obj.row.status='1';
					updateIp(obj.row).then(function(result){
						_this.GLOBAL_FUN.successMsg();
						_this.search();
					});
				}
			} else if (obj.command === 'delete'){
				this.$confirm('确认删除IP:"'+obj.row.ip+'"？').then(_ => {
					deleteIp({ip:obj.row.ip}).then(function(result){
						_this.GLOBAL_FUN.successMsg();
						_this.search();
					})
				}).catch(_ => {});
			}
		},
		handleCreateIp() {
			this.dialogFormVisible = true;
			this.form = {
				ip: '',
				status: '0',
				remarks: ''
			};
		},
		submit() {
			let _this = this;
			this.$refs['form'].validate(valid => {
				if (valid) {
					if (!is_ip(this.form.ip)){
						this.GLOBAL_FUN.errorMsg('IP格式错误！');
						return false;
					}
					addIp(this.form).then(function(result) {
						_this.dialogFormVisible = false;
						_this.GLOBAL_FUN.successMsg();
						_this.search();
					});
				} else {
					return false;
				}
			});
		},
		search() {
			let _this = this;
			ipPageList({ ip: this.ip, status: this.status, currentPage: this.currentPage, pageSize: this.pageSize }).then(function(result) {
				if (result.data) {
					_this.tableData = result.data.lists;
					_this.totalNum = result.data.totalNum;
				}
			});
		}
	}
};
</script>

<style>
.input-with-select .el-input-group__prepend {
	background-color: #fff;
}
</style>
