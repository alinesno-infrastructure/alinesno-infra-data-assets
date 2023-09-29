<template>
	<div class="app-container">
		<el-page-header @back="goBack" content="服务管理"></el-page-header>
		<el-row :gutter="24" style="margin-top: 20px;">
			<el-col :span="16">
				<el-card shadow="false" >
					<div slot="header" class="clearfix">
					    <i class="el-icon-orange" style="font-size: 10pt;"></i>&nbsp;<span style="font-size: 12pt;">注册网关-客户端拓扑结构</span>
					  </div>
				  <div id="topologyChart" :style="{ width: '100%', height: '750px', padding: '0px', margin: '0px', border: '0px #0298F9 solid' }"></div>
				</el-card>
			</el-col>
			<el-col :span="8" style="border: 0px solid red;">
				<el-card shadow="false" >
					<div slot="header" class="clearfix">
					    <i class="el-icon-s-custom" style="font-size: 10pt;"></i>&nbsp;<span style="font-size: 12pt;">注册客户端列表</span>
					</div>
					<el-card shadow="false" class="paper-card__body" v-for="(item,index) in clientList" :key="index" style="margin-bottom: 10px; font-size: 10pt;">					
						<el-row :gutter="24">
							<el-col :span="20" style="border: 0px solid red;">
								<i class="el-icon-s-platform" style="font-size: 10pt;"></i>&nbsp;<el-tag size="small" style="font-weight: bold;">{{item.name}}</el-tag>
								<i class="el-icon-caret-right" style="font-size: 10pt;"></i>&nbsp;<el-tag size="small" type="warning" style="font-weight: bold;">{{item.id}}</el-tag>
							</el-col>
							<el-col :span="4" style="border: 0px solid red;">
								<el-switch v-model="item.regServerStatus" @change="handleSwitchChange(item,index)" active-color="#13ce66" inactive-color="#ff4949" active-value="0" inactive-value="1" style="float: right;"></el-switch>
							</el-col>
						</el-row>
					</el-card>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import {regClientList,stopRegClient,startRegClient} from '@/api/regserver_api.js'
	
export default {
	data() {
		return {
			form: {},
			clientList:[]
		};
	},
	created: function() {
		//在组件创建完毕后加载
		let query = this.$route.query;
		if (query){
			let route = query.route;
			console.log('route', route);
			this.form = route.form;
		}
	},
	mounted: function() {
		this.init();
	},
	beforeDestroy: function() {
		
	},
	methods: {
		init() {
			this.regClientList();
		},
		regClientList(){
			let _this = this;
			regClientList({routeId: this.form.id}).then(function(result){
				if (result.data){
					_this.clientList = result.data;
					_this.drawLine();
				}
			})
		},
		handleSwitchChange(item, index){
			let _this = this;
			if (item && item.regServerStatus === '0'){
				startRegClient({id:item.regServerId}).then(function(result){
					_this.GLOBAL_FUN.successMsg();
				});
			}else {
				stopRegClient({id:item.regServerId}).then(function(result){
					_this.GLOBAL_FUN.successMsg();
				});
			}
		},
		goBack() {
			this.$router.push({path:'/gatewayList',query:{}});
		},
		drawLine() {
			let _this = this;
			// 基于准备好的dom，初始化echarts实例
			let topologyChart = this.$echarts.init(document.getElementById('topologyChart'));
			var colors = ['#00ADD0', '#FFA12F', '#B62AFF', '#727CF5', '#1890FF', '#00f6ff', '#20C0F4', '#95F300', '#04FDB8', '#AF5AFF'];
			var getdata = function getData() {
				let data = {
					name: _this.form.name,
					value: 0,
					children: []
				};
				if (_this.clientList != null){
					let legnth = _this.clientList.length;
					for (let i = 0; i < legnth; i++) {
						let client = _this.clientList[i];
						let obj = {
							name: client.name ,
							value: i,
							children: []
						};					
						data.children.push(obj);
					}
				}
				let arr = [];
				arr.push(data);
				//
				arr = handle(arr, 0);
				console.log(arr);
				return arr;
			};
			var handle = function handleData(data, index, color = '#00f6ff') {
				//index标识第几层
				return data.map((item, index2) => {
					//计算出颜色
					if (index == 1) {
						color = colors.find((item, eq) => eq == index2 % 10);
					}
					// 设置节点大小
					if (index === 0 || index === 1) {
						item.label = {
							position: 'inside'
							//   rotate: 0,
							//   borderRadius: "50%",
						};
					}
					// 设置label大小
					switch (index) {
						case 0:
							item.symbolSize = 90;
							break;
						case 1:
							item.symbolSize = 60;
							break;
						default:
							item.symbolSize = 20;
							break;
					}
					// 设置线条颜色
					item.lineStyle = { color: color };

					if (item.children) {
						//存在子节点
						item.itemStyle = {
							borderColor: color,
							color: color
						};
						item.children = handle(item.children, index + 1, color);
					} else {
						//不存在
						item.itemStyle = {
							color: 'transparent',
							borderColor: color
						};
					}
					return item;
				});
			};

			var option = {
				type: 'tree',
				// backgroundColor: '#000',
				toolbox: {
					//工具栏
					show: true,
					iconStyle: {
						borderColor: '#03ceda'
					},
					feature: {
						restore: {}
					}
				},
				tooltip: {
					//提示框
					trigger: 'item',
					triggerOn: 'mousemove',
					backgroundColor: 'rgba(1,70,86,1)',
					borderColor: 'rgba(0,246,255,1)',
					borderWidth: 0.5,
					textStyle: {
						fontSize: 10
					},
					formatter: '{b}'
				},
				series: [
					{
						type: 'tree',
						hoverAnimation: true, //hover样式
						data: getdata(),
						top: 0,
						bottom: 0,
						left: 0,
						right: 0,
						layout: 'radial',
						symbol: 'circle',
						symbolSize: 10,
						nodePadding: 20,
						animationDurationUpdate: 750,
						expandAndCollapse: true, //子树折叠和展开的交互，默认打开
						initialTreeDepth: 2,
						roam: true, //是否开启鼠标缩放和平移漫游。scale/move/true
						focusNodeAdjacency: true,
						itemStyle: {
							borderWidth: 1
						},
						label: {
							//标签样式
							// color: '#fff',
							fontSize: 10,
							fontWeight: 'bold',
							fontFamily: 'SourceHanSansCN',
							position: 'inside',
							rotate: 0
						},
						lineStyle: {
							width: 1,
							curveness: 0.5
						}
					}
				]
			};
			// 绘制图表
			topologyChart.setOption(option);
		}
	}
};
</script>

<style scoped>
	.paper-card__body >>> .el-card__body{
		padding: 12px;
	}
	.paper-card__body >>> .el-card__header {
	    padding: 10px 20px;
	    border-bottom: 1px solid #EBEEF5;
	    box-sizing: border-box;
	}
</style>
