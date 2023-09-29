<template>
  <div>
      <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="55%" :close-on-click-modal="false">
            <i class="iconfont icon-icon_renwujincheng" style="font-size: 16pt; color: #90A0A5;"></i>
            <span class="span_14">7日请求总量</span>
            <el-divider style="width: 80%;"></el-divider>
            <div id="route7dayAccessChart" class="chart_line_2"></div>
            <br/>
            <i class="iconfont icon-yibiaopan" style="font-size: 16pt; color: #90A0A5;"></i>
            <span class="span_14">24小时请求总量</span>
            <el-divider style="width: 80%;"></el-divider>
            <div id="route24HourAccessChart" class="chart_line_2"></div>
            <br/>
            <i class="iconfont icon-yibiaopan" style="font-size: 16pt; color: #90A0A5;"></i>
            <span class="span_14">60分钟请求总量</span>
            <el-divider style="width: 80%;"></el-divider>
            <div id="route60MinAccessChart" class="chart_line_2"></div>
			<div slot="footer" class="dialog-footer">
				<el-button icon="el-icon-s-release" size="mini" type="warning" @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
  </div>
</template>

<script>
import {countAppRequestTotal} from '@/api/count_api.js'
export default {
  data() {
    return {
      name: "routeRequestCount",
      dialogFormVisible: false,
      dialogTitle: '统计详情'
    };
  },
  created: function () {
  },
  methods: {
    count(obj){
        this.dialogTitle = obj.name +　'统计详情';
        let _this = this;
        countAppRequestTotal({id:obj.id}).then(function(result){
            console.log(result);
            if (result.data){
                _this.drawLine('route7dayAccessChart', result.data.dayCounts, '#37A2FF','rgba(128, 255, 165)', 'rgba(1, 191, 236)');
                _this.drawLine('route24HourAccessChart', result.data.hourCounts, '#FFBF00','rgba(55, 162, 255)', 'rgba(116, 21, 219)');
                _this.drawLine('route60MinAccessChart', result.data.minCounts, '#00DDFF', 'rgba(0, 221, 255)', 'rgba(77, 119, 255)');
            }
        });
    },
    drawLine(id, result, color, color0, color1) {
        let xAxisData =  [];
        let seriesData = [];
        if (result != undefined && result.dates && result.counts){
            xAxisData = result.dates;
            seriesData = result.counts;
        }
        //console.log("chart data", seriesData);
        let _this = this;
        // 基于准备好的dom，初始化echarts实例
        let accessChart = this.$echarts.init(document.getElementById(id));
        //渐变色效果
        var option = {
            // color: ['#80FFA5', '#00DDFF', '#37A2FF', '#00DDFF', '#FFBF00'],
            color: [color],
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
                top: '5%',
                left: '1%',
                right: '2%',
                bottom: '1%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxisData
                }
            ],
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    type: 'line',
                    smooth: true,
                    showSymbol: true,
                    areaStyle: {
                        opacity: 0.8,
                        color: this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: color0
                        }, {
                            offset: 1,
                            color: color1
                        }])
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: seriesData
                }
            ]
        };
        // 绘制图表
        accessChart.setOption(option, true);
    }
  }
};
</script>

<style>
.chart_line_2 {
    width: 100%;
    height: 180px;
}
</style>