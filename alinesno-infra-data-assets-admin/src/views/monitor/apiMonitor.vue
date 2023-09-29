<template>
	<div class="app-container">
		<el-dialog title="监控告警信息" :visible.sync="dialogFormVisible" width="25%" :close-on-click-modal="false">
			<el-form :model="infoForm" ref="infoForm" :label-width="formLabelWidth">
				<el-form-item label="服务ID：" size="mini">
					<el-tag size="small">{{infoForm.routeId}}</el-tag>
				</el-form-item>
				<el-form-item label="服务名称：" size="mini">
					<el-tag size="small">{{infoForm.name}}</el-tag>
				</el-form-item>
				<el-form-item label="服务地址：" size="mini">
					<el-tag size="small" type="success">{{infoForm.uri}}</el-tag>
				</el-form-item>
				<el-form-item label="断言路径：" size="mini">
					<el-tag size="small" type="success">{{infoForm.path}}</el-tag>
				</el-form-item>
				<el-form-item v-show="infoForm.method != ''" label="请求模式：" size="mini">
					<el-tag size="small" type="success">{{infoForm.method}}</el-tag>
				</el-form-item>
				<el-form-item label="告警状态：" size="mini">
					<el-tag v-show="infoForm.status != '2'" size="small" type="" effect="dark">正常</el-tag>
					<el-tag v-show="infoForm.status == '2'" size="small" type="danger" effect="dark">告警</el-tag>
				</el-form-item>
				<el-form-item v-show="infoForm.status == '2'" label="告警时间：" size="mini">
					<el-tag size="small" type="danger" effect="dark">{{infoForm.alarmTime}}</el-tag>
				</el-form-item>
				<el-form-item label="告警重试：" size="mini">
					<el-tag v-show="infoForm.recover == '0'" size="small" type="" effect="dark">启用</el-tag>
					<el-tag v-show="infoForm.recover == '1'" size="small" type="danger" effect="dark">禁止</el-tag>
				</el-form-item>
				<el-form-item label="通知频率：" size="mini">
					<el-tag v-for="item in monitorOptions" :key="item.value"  v-show="infoForm.frequency == item.value" size="small" type="" effect="dark">{{item.label}}</el-tag>
				</el-form-item>
				<el-form-item v-show="infoForm.emails != null && infoForm.emails != ''" label="通知邮箱：" size="mini">
					<el-tag size="small" type="" effect="dark">{{infoForm.emails}}</el-tag>
				</el-form-item>
				<el-form-item v-show="infoForm.sendTime != null && infoForm.sendTime != ''" label="通知时间：" size="mini">
					<el-tag size="small" type="" effect="dark">{{infoForm.sendTime}}</el-tag>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button icon="el-icon-document-delete" v-show="infoForm.status == '2'" size="mini" type="success" @click="close">关闭本次告警</el-button>
				<el-button icon="el-icon-s-release" size="mini" type="warning" @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>

		<el-card shadow="false" class="box-card" style="padding-bottom: 20px;">
			<el-row>
				<el-col :span="10">
					<span class="span_1">接口监控</span>
				</el-col>
				<el-col :span="14">
                    <div style="margin-top: 5px;" align="right">
						<el-radio-group v-model="status" size="mini" @change="search">
						  <el-radio-button label=""><i class="el-icon-menu" style="font-weight: bold;"></i>&nbsp;所有</el-radio-button>
						  <el-radio-button label="0"><i class="el-icon-loading" style="font-weight: bold;"></i>&nbsp;运行</el-radio-button>
						  <el-radio-button label="1"><i class="el-icon-circle-close" style="font-weight: bold;"></i>&nbsp;停止</el-radio-button>
						  <el-radio-button label="2"><i class="el-icon-bell" style="font-weight: bold;"></i>&nbsp;告警</el-radio-button>
						</el-radio-group>
					  </div>
				</el-col>
			</el-row>

            <div :key="index" v-for="(item, index) in taskList">
				<div :class="taskTopic.class[item.status]" :title="item.name + taskTopic.title[item.status]" @click="handleClickMonitorInfo(item)">
					<i :class="taskTopic.icon[item.status]" style="font-weight: bold;"></i>&nbsp;<span>{{taskTopic.des[item.status]}}</span>
					<div>
						<span>{{item.routeId}}</span>
					</div>
					<div class="item-name-style">
						<span>{{item.name}}</span>
					</div>
				</div>
			</div>
		</el-card>
	</div>
</template>

<script>
	import {monitorList, closeMonitor} from '@/api/monitor_api.js'

	export default {
		data() {
			return {
				infoForm: {},
				dialogFormVisible: false,
				formLabelWidth: '100px',
                status: '',
				taskList: [],
				taskTopic: {
					icon: {
						'0':'el-icon-loading',
						'1':'el-icon-circle-close',
						'2':'el-icon-bell'
					},
					class:{
						'0':'breathe-run breathe-style breathe-keyframes-run breathe-div-border',
						'1':'breathe-stop breathe-style breathe-div-border',
						'2':'breathe-urgent breathe-style breathe-keyframes-urgent breathe-div-border'
					},
					title:{
						'0':'，服务正常运行',
						'1':'，服务已停止',
						'2':'，30分钟内发生告警'
					},
					des:{
						'0':'服务运行中...',
						'1':'服务已停止...',
						'2':'服务告警...'
					},
				},
				monitorOptions: [
					{value: '30m',label: '30分钟一次'},
					{value: '1h', label: '1小时一次'},
					{value: '5h',label: '5小时一次'},
					{value: '12h',label: '12小时一次'},
					{value: '24h',label: '24小时一次'}
				]
			};
		},
		created: function() {
			this.search();
		},
		mounted: function() {

		},
		beforeDestroy: function() {
		},
		methods:{
			search(){
				let _this = this;
				let _status = this.status === ''? null : this.status;
				monitorList({status: _status}).then(function(result){
					if (result.data){
						_this.taskList = result.data;
					}
				});
			},
			close() {
				if (this.infoForm.status == '0'){
					return false;
				}
				let _this = this;
				closeMonitor({id: this.infoForm.id}).then(function(result) {
					_this.infoForm.status = '0';
					_this.GLOBAL_FUN.successMsg();
					_this.setTableMonitorStatus();
				});
			},
			handleClickMonitorInfo(row){
				console.log(row);
				this.dialogFormVisible = true;
				this.infoForm = {
				  id: row.id,
          routeId: row.routeId,
					name: row.name,
					uri: row.uri,
					path: row.path,
					method: row.method,
					status: row.status,
					alarmTime: row.monitor.alarmTime,
					recover: row.monitor.recover,
					frequency: row.monitor.frequency,
					sendTime: row.monitor.sendTime,
					emails: row.monitor.emails
				}
			},
			setTableMonitorStatus(){
				let length = this.taskList.length;
				for (var i=0;i<length;i++){
					if (this.taskList[i].id == this.infoForm.id){
						this.taskList[i].status = this.infoForm.status;
						break;
					}
				}
			}
		}
	};
</script>

<style>
.span_1{
    font-size: 18pt;
    font-weight: bold;
    margin-bottom: 9px;
}

.breathe-run {
	border: 1px solid #2b92d4;
}

.breathe-stop {
	border: 1px solid #333;
	opacity: 1;
}

.breathe-urgent {
	border: 1px solid #F0565A;
}

.breathe-style {
	float: left;
    border-radius: 5px;
	position: relative;
	width: 150px;
	height: 80px;
	margin: 10px 10px;
	padding-left: 10px;
	padding-top: 6px;
	line-height: 22px;
	/* color: #fff; */
	font-size: 8pt;
	text-align: left;
	cursor: pointer;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
	overflow: hidden;
}

/* .breathe-keyframes-run:hover {
	-webkit-animation-timing-function: ease-in-out;
	-webkit-animation-name: breathe-run;
	-webkit-animation-duration: 500ms;
	-webkit-animation-iteration-count: infinite;
	-webkit-animation-direction: alternate;
}

.breathe-keyframes-urgent {
	-webkit-animation-timing-function: ease-in-out;
	-webkit-animation-name: breathe-urgent;
	-webkit-animation-duration: 700ms;
	-webkit-animation-iteration-count: infinite;
	-webkit-animation-direction: alternate;
} */

.item-name-style{
	white-space:nowrap;
	overflow:hidden;
	text-overflow: ellipsis;
	font-weight: bold;
}

/* vue中@-webkit-keyframes改成@keyframes */
@keyframes breathe-run {
	0% {
		opacity: 0.75;
		box-shadow: 0 1px 2px rgba(24, 144, 255, 0.3);
	}
	30% {
		opacity: 0.85;
		box-shadow: 0 1px 2px rgba(24, 144, 255, 0.3);
	}
	66% {
		opacity: 0.95;
		box-shadow: 0 1px 2px rgba(24, 144, 255, 0.3);
	}
	100% {
		opacity: 1;
		border: 1px solid rgba(24, 144, 255, 1);
		box-shadow: 0 1px 20px rgba(24, 144, 255, 1);
	}
}

@keyframes breathe-urgent {
	0% {
		opacity: 0.6;
		box-shadow: 0 1px 2px rgba(255, 255, 255, 0.1);
	}
	100% {
		opacity: 1;
		border: 1px solid rgba(240, 86, 90, 1);
		box-shadow: 0 1px 20px rgba(240, 86, 90, 1);
	}
}

.breathe-div-border:before,.breathe-div-border:after {
	content:'';
	position:absolute;
	top:0;
	right:0;
	height:2px;
	width:0;
	background:#727CF5;
	transition:400ms ease all;
}

.breathe-div-border:after {
	right:inherit;
	top:inherit;
	left:0;
	bottom:0;
}

.breathe-div-border:hover:before,.breathe-div-border:hover:after {
	width:100%;
	transition:555ms ease all;
}
</style>
