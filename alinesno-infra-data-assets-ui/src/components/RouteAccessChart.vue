<template>
	<div id="routeAccessChart" :style="{ width: '100%', height: '220px'}"></div>
</template>

<script>
	import {countBalancedRequest} from '@/api/count_api.js'
	
export default {
	data() {
		return {};
	},
	methods: {
		loadAccessCard(balancedId, routeIds, dateType){
			let _this = this;
			console.log("balancedId", balancedId);
			countBalancedRequest({balancedId: balancedId,routeIds: routeIds, dateType:dateType}).then(function(result){
				if (result && result.data){
					_this.drawLine(result.data);
				}
			});
		},
		drawLine(result) {
			let xAxisData = result.dates;
			let seriesData = [];
			result.datas.forEach((row,index)=>{
				let data = {
				    name: row.routeId,
				    type: 'line',
				    stack: '总量',
					smooth: true,
					symbol: 'none',
				    areaStyle: {},
				    data: row.counts
			    }
				seriesData.push(data);
			});
			console.log("chart data", seriesData);
			let _this = this;
			// 基于准备好的dom，初始化echarts实例
			let routeAccessChart = this.$echarts.init(document.getElementById('routeAccessChart'));
			var colors = ['#00ADD0', '#FFA12F', '#B62AFF', '#727CF5', '#1890FF', '#00f6ff', '#20C0F4', '#95F300', '#04FDB8', '#AF5AFF'];
			var option = {
				color: colors,
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            label: {
			                backgroundColor: '#6a7985'
			            }
			        }
			    },
			    grid: {
					top: '10%',
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: [
			        {
			            type: 'category',
			            boundaryGap: false,
						data: xAxisData
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
						boundaryGap: [0, '10%']
			        }
			    ],
			    series: seriesData
			};
			// 绘制图表
			routeAccessChart.setOption(option, true);
		}
	},
}
	
</script>

<style>
</style>
