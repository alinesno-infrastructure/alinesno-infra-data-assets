<template>
	<div class="app-container">
		<el-card shadow="false" class="box-card">
			<el-row>
				<el-col :span="10">
					<div style="margin-bottom: 9px;">
						<span style="font-size: 18pt;font-weight: bold; ">负载管理</span>
					</div>
				</el-col>
				<el-col :span="14">
					<div style="float: right; margin-left: 10px;">
						<el-button icon="el-icon-folder-add" type="primary" @click="handleCreateBalanced" title="创建负载服务"></el-button>
					</div>
					<div style="float: right;">
						<el-input placeholder="请输入网关服务名称" v-model="form.name" class="input-with-select" style="width: 620px;"
							clearable>
							<el-select v-model="form.groupCode" slot="prepend" placeholder="请选择分组"
								style="width: 140px; margin-right: 10px;">
								<el-option label="所有" value="" />
								<el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value" />
							</el-select>
							<el-popover placement="bottom" slot="prepend" trigger="click">
								<el-radio v-model="form.status" v-for="item in statusOptions" :key="item.value"
									:label="item.value">{{ item.label }}</el-radio>
								<el-button slot="reference">
									服务状态:{{ form.status === '0' ? '启用' : form.status === '1' ? '禁用' : '所有' }}<i
										class="el-icon-caret-bottom el-icon--right"></i>
								</el-button>
							</el-popover>
							<el-button slot="append" icon="el-icon-search" @click="search" title="查询网关服务"></el-button>
						</el-input>
					</div>
				</el-col>
			</el-row>
		</el-card>

		<el-row :gutter="16" style="margin-top: 20px;">
			<el-col :span="11">
				<el-card shadow="false" class="box-card">
					<el-table size="small" :data="tableData" style="width: 100%">
						<el-table-column label="服务名称">
							<template slot-scope="scope">
								<el-tooltip effect="dark" :content="scope.row.name" placement="top-start">
									<el-tag size="small" type="warning" style="font-weight: bold;">{{ scope.row.name }}</el-tag>
								</el-tooltip>
							</template>
						</el-table-column>

						<el-table-column label="分组" width="90">
							<template slot-scope="scope">
								<el-tag v-for="group in groupOptions" :key="group.value" v-show="(group.value === scope.row.groupCode)"
									size="small" type="">{{ group.label }}</el-tag>
							</template>
						</el-table-column>
						<el-table-column label="断言地址" width="250" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{ parent }}{{ scope.row.loadUri }}
								<el-popover trigger="click" placement="bottom">
									<div style="font-size: 10pt;">
										<div style="margin-bottom: 8px;">
											<i class="iconfont icon-zuzhiqunzu" style="font-size: 16pt; color: #90A0A5;"></i>
											<span class="route-title">网关代理地址</span>
										</div>
										<span>
											<el-tag size="small" type="success"
												style="font-weight: bold;">{{ GLOBAL_VAR.gatewayRoutesURL }}{{ parent }}{{ scope.row.loadUri }}</el-tag>
											<el-button slot="reference" style="padding: 3px 0; " icon="el-icon-document-copy" type="text"
												@click="handleCopy(scope.row.loadUri)" title="复制"></el-button>
										</span>
										<br />
									</div>
									<el-button slot="reference" style="padding: 3px 0; " icon="iconfont icon-IP" type="text"
										title="网关代理地址"></el-button>
								</el-popover>
							</template>
						</el-table-column>
						<el-table-column label="创建时间" align="center" width="135" prop="createTime"></el-table-column>
						<el-table-column label="状态" width="65" align="center" prop="status" :formatter="formatterStatus">
							<template slot-scope="scope">
								<el-tag effect="dark" size="small" v-if="scope.row.status === '0'" type="">启用</el-tag>
								<el-tag effect="dark" size="small" v-if="scope.row.status === '1'" type="danger">禁用</el-tag>
							</template>
						</el-table-column>
						<el-table-column label="操作" align="center" width="80">
							<template :v-if="scope.row.id != null" slot-scope="scope">
								<el-dropdown trigger="click" @command="handleCommandGateway">
									<el-button size="mini" type="warning">
										管理<i class="el-icon-arrow-down el-icon--right"></i>
									</el-button>
									<el-dropdown-menu slot="dropdown">
										<el-dropdown-item icon="el-icon-edit"
											:command="{ command: 'edit', row: scope.row }">编辑</el-dropdown-item>
										<el-dropdown-item :command="{ command: 'start', row: scope.row }" divided><i class="el-icon-success"
												style="color: #409EFF;"></i>启用</el-dropdown-item>
										<el-dropdown-item :command="{ command: 'stop', row: scope.row }"><i class="el-icon-error"
												style="color: red;"></i>禁用</el-dropdown-item>
										<el-dropdown-item icon="el-icon-delete" :command="{ command: 'delete', row: scope.row }"
											divided>删除</el-dropdown-item>
									</el-dropdown-menu>
								</el-dropdown>
							</template>
						</el-table-column>
						<el-table-column label="查看" width="58">
							<template slot-scope="scope" style="border: 1px solid red;">
								<el-button size="mini" @click="handleClickBalanced(scope.row)" circle title="请点击选中查看"
									:class="(selectedId === scope.row.id) ? 'el-icon-arrow-right breathe-keyframes btn-select' : 'el-icon-arrow-down btn-not-select'">
								</el-button>
							</template>
						</el-table-column>
					</el-table>
					<div class="block" style="margin-top: 20px;">
						<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
							:current-page="currentPage" :page-sizes="[10, 30, 50]" :page-size="pageSize"
							layout="total, sizes, prev, pager, next, jumper" :total="totalNum"></el-pagination>
					</div>
				</el-card>
			</el-col>
			<el-col :span="5">
				<el-card shadow="false" class="box-card">
					<div style="margin-bottom: 14px;">
						<i class="iconfont icon-wumoxing" style="font-size: 16pt; color: #90A0A5;"></i>
						<span class="route-title">转发路由</span>
					</div>
					<el-collapse v-model="activeName">
						<el-collapse-item v-for="(item, index) in routeTable" :key="index" :name='(index + 1)'>
							<template slot="title" style="width: 100%;">
								<div style="width: 100%;">
									<el-tag size="" type="success"
										style="font-weight: bold; background-color: #FFFFFF; color: #303133;">{{ item.name }}
										<i class="el-icon-circle-check" v-if="item.status === '0'"
											style="font-size: 12pt; font-weight: bold; color: #409EFF;" title="服务已启用"></i>
										<i class="el-icon-circle-close" v-if="item.status === '1'"
											style="font-size: 12pt; font-weight: bold; color: #F56C6C;" title="服务已禁用"></i>
									</el-tag>
								</div>
							</template>
							<div style="line-height: 28px;color: #727CF5; font-weight: bold;"><i class="el-icon-s-promotion"
									style="font-size: 12pt; font-weight: bold;"></i>&nbsp;&nbsp;{{ item.routeId }}</div>
							<div style="line-height: 28px;color: #1890FF; font-weight: bold;"><i class="el-icon-connection"
									style="font-size: 12pt; font-weight: bold;"></i>&nbsp;&nbsp;{{ item.uri }}</div>
						</el-collapse-item>
					</el-collapse>
				</el-card>
			</el-col>
			<el-col :span="8">
				<boxCardComponent ref="boxCard" :balanced-name="balancedName" :route-table="routeTable"></boxCardComponent>
			</el-col>
		</el-row>
	</div>
</template>

<script>
import boxCardComponent from '@/components/BoxCard.vue'
import { balancedPageList, deleteBalanced, startBalanced, stopBalanced } from '@/api/balanced_api.js'
import { loadServerRegList } from '@/api/balanced_api.js'

export default {
	data() {
		return {
			form: {
				name: null,
				groupCode: null,
				status: null
			},
			parent: '/parent/',
			activeName: 1,
			formLabelWidth: '140px',
			gatewayServer: '',
			tableData: [],
			currentPage: 1,
			pageSize: 10,
			totalNum: 1,
			selectedId: 0,
			pickerOptions: {
				disabledDate(time) {
					return time.getTime() > Date.now();
				}
			},
			statusOptions: [
				{ value: null, label: '所有' },
				{ value: '0', label: '启用' },
				{ value: '1', label: '禁用' },
			],
			groupOptions: this.GLOBAL_VAR.groups,
			balancedName: null,
			routeTable: []
		};
	},
	components: {
		boxCardComponent
	},
	created: function () {
		this.init();
	},
	mounted: function () {
		console.log('this GLOBAL_VR = ' + this.GLOBAL_VAR);
	},
	beforeDestroy: function () {
	},
	methods: {
		init() {
			this.search();
		},
		handleSizeChange(val) {
			this.pageSize = val;
			this.search();
		},
		handleCurrentChange(val) {
			this.currentPage = val;
			this.search();
		},
		formatterStatus(row, index) {
			return row.status === '0' ? '启用' : '禁用';
		},
		handleClickBalanced(row) {
			let _this = this;
			this.selectedId = row.id;
			this.balancedName = row.name;
			loadServerRegList({ balancedId: row.id }).then(function (result) {
				if (result && result.data) {
					_this.routeTable = result.data;
					console.log(_this.$refs.boxCard);
					_this.$refs.boxCard.loadCard(_this.selectedId, result.data);
				}
			});
		},
		handleCreateBalanced() {
			this.$router.push({ path: '/createBalanced', query: { handleType: 'add' } });
		},
		handleCommandGateway(obj) {
			let _this = this;
			if (obj.command === 'edit') {
				this.$router.push({ path: '/createBalanced', query: { handleType: 'edit', balanced: obj.row } });
			} else if (obj.command === 'start') {
				startBalanced({ id: obj.row.id }).then(function (result) {
					_this.GLOBAL_FUN.successMsg();
					_this.search();
				});
			} else if (obj.command === 'stop') {
				stopBalanced({ id: obj.row.id }).then(function (result) {
					_this.GLOBAL_FUN.successMsg();
					_this.search();
				});
			} else if (obj.command === 'delete') {
				this.$confirm('确认删除"' + obj.row.name + '"网关路由？').then(_ => {
					deleteBalanced({ id: obj.row.id }).then(function (result) {
						_this.GLOBAL_FUN.successMsg();
						_this.search();
					})
				}).catch(_ => { });
			}
		},
		handleClose(done) {
			// this.$confirm('确认关闭？').then(_ => {
			done();
			// }).catch(_ => {});
		},
		handleCopy(val) {
			let value = this.GLOBAL_VAR.gatewayRoutesURL + this.parent + val;
			this.GLOBAL_FUN.copy(value);
		},
		search() {
			let _this = this;
			balancedPageList({ name: this.form.name, groupCode: this.form.groupCode, status: this.form.status, currentPage: this.currentPage, pageSize: this.pageSize }).then(function (result) {
				if (result.data && result.data.lists) {
					_this.tableData = result.data.lists;
					_this.totalNum = result.data.totalNum;
					if (_this.tableData.length > 0) {
						_this.handleClickBalanced(_this.tableData[0]);
					}
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

/* .breathe-keyframes {
	-webkit-animation-timing-function: ease-in-out;
	-webkit-animation-name: breathe;
	-webkit-animation-duration: 700ms;
	-webkit-animation-iteration-count: infinite;
	-webkit-animation-direction: alternate;
} */

/* vue中@-webkit-keyframes改成@keyframes */
@keyframes breathe {
	0% {
		margin-left: 10px;
		opacity: 0.75;
		/* box-shadow: 0 1px 2px rgba(24, 144, 255, 0.3); */
	}

	30% {
		margin-left: 7px;
		opacity: 0.85;
		/* box-shadow: 0 1px 2px rgba(24, 144, 255, 0.3); */
	}

	66% {
		margin-left: 4px;
		opacity: 0.95;
		/* box-shadow: 0 1px 2px rgba(24, 144, 255, 0.3); */
	}

	100% {
		margin-left: 0px;
		opacity: 1;
		/* border: 1px solid rgba(24, 144, 255, 1); */
		/* box-shadow: 0 1px 10px rgba(24, 144, 255, 1); */
	}
}

.btn-select {
	border: 1px solid rgb(64, 158, 255);
	font-size: 12pt;
	color: #409EFF;
	font-weight: 900;
}

.btn-not-select {
	border: 1px solid rgb(237, 237, 237);
	margin-left: 2px;
	margin-left: 5px;
	font-size: 12pt;
	color: rgb(237, 237, 237)
}

.route-title {
	font-size: 14pt;
	color: #90A0A5;
	font-weight: bold;
	margin-left: 6px;
}</style>
