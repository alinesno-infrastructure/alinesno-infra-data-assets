<template>
	<el-card shadow="false" class="box-card-component" >
		<div slot="header" class="box-card-header">
			<el-row>
				<el-col :span="12">&nbsp;
				<div style="margin-left: 20px;margin-top:20px">
					<i class="iconfont icon-guizeyinqing" style="font-size: 16pt; color: #90A0A5;"></i>
					<span style="font-size: 14pt; color: #90A0A5; font-weight: bold; margin-left: 6px;">访问量统计</span>
				</div>
				</el-col>
				<el-col :span="12">
					<el-radio-group v-model="dateType" size="mini" style="margin-top: 18px; margin-right: 18px; float: right;" @change="loadAccessCard">
					  <el-radio-button label="min">60分</el-radio-button>
					  <el-radio-button label="hour">24小时</el-radio-button>
					  <el-radio-button label="day">7天</el-radio-button>
					</el-radio-group>
				</el-col>
			</el-row>
			<el-divider style="width: 80%;"></el-divider>
			<!-- <img src="https://wpimg.wallstcn.com/e7d23d71-cf19-4b90-a1cc-f56af8c0903d.png"> -->
			<RouteAccessChart ref="routeAccessChart"></RouteAccessChart>
		</div>
		
		<div style="position:relative;">
			<!-- <pan-thumb :image="avatar" class="panThumb" /> -->
			<mallki class-name="mallki-text" :text="balancedName + '-权重占比'" />
			<div style="padding-top: 35px;">
				<div v-for="(item, index) in routeTable" :key="index" class="progress-item">
					<span>{{item.name}}</span>
					<el-progress :percentage="item.weight" :color="colors[index]" />
				</div>
			</div>
		</div>
	</el-card>
</template>

<script>
import Mallki from './TextHoverEffect/Mallki.vue';
import PanThumb from './PanThumb';
import RouteAccessChart from './RouteAccessChart.vue';

export default {
	components: { PanThumb, Mallki, RouteAccessChart },
	filters: {
		statusFilter(status) {
			const statusMap = {
				success: 'success',
				pending: 'danger'
			};
			return statusMap[status];
		}
	}, 
	props:['routeTable','balancedName'],//父组件传参
	data() {
		return {
			name: 'boxCard',
			dateType: 'min',
			balancedId:null,
			routeIds: [],
			timer: '',
			avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
			statisticsData: {
				article_count: 1024,
				pageviews_count: 1024
			},
			colors: ['#00ADD0', '#FFA12F', '#B62AFF', '#727CF5', '#1890FF', '#00f6ff', '#20C0F4', '#95F300', '#04FDB8', '#AF5AFF'],			
			// customColors: [
			// 	{ color: '#00ADD0', percentage: 20 },
			// 	{ color: '#FFA12F', percentage: 40 },
			// 	{ color: '#B62AFF', percentage: 60 },
			// 	{ color: '#727CF5', percentage: 80 },
			// 	{ color: '#1890FF', percentage: 100 }
			// ]
		};
	},
	created: function() {
		this.init();
	},
	mounted: function() {
		//定时刷新访问接口，获取最新数据，设置刷新频率30秒
		this.timer = setInterval(this.loadAccessCard, 30000);
	},
	beforeDestroy: function() {
		clearInterval(this.timer);
	},
	methods:{
		init() {
			console.log("routeTable", this.routeTable);
		},
		loadCard(balancedId, data){
			console.log("data", data);
			this.balancedId = balancedId;
			if (data != null && data != undefined){
				this.routeIds = [];
				data.forEach((row,index)=>{					
					this.routeIds.push(row.routeId);
				});
				this.loadAccessCard();
			}
		},
		loadAccessCard(){
			if (this.routeIds != null && this.routeIds.length > 0){
				this.$refs.routeAccessChart.loadAccessCard(this.balancedId, this.routeIds, this.dateType);
			}
		}
	}
};
</script>

<style>
.box-card-component .el-card__header {
	padding: 0px !important;
}
</style>
<style scoped="scoped">
.box-card-component {
	.box-card-header {
		position: relative;
		height: 220px;
		img {
			width: 100%;
			height: 100%;
			transition: all 0.2s linear;
			&:hover {
				transform: scale(1.1, 1.1);
				filter: contrast(130%);
			}
		}
	}
}

.progress-item {
	margin-bottom: 10px;
	font-size: 10px;
}

@media only screen and (max-width: 1510px) {
	.mallki-text {
		display: none;
	}
}

.el-divider--horizontal {
    display: block;
    height: 1px;
    width: 92%;
    margin: 14px 20px;
}
.el-divider {
    background-color: #EBEEF5;
    position: relative;
}
</style>
