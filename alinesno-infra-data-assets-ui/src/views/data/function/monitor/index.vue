<template>
  <div class="app-container">
    <el-card shadow="never" class="box-card">
      <el-row>
        <el-col :span="10">
          <span class="span_1">接口统计</span>
        </el-col>
        <el-col :span="14">
          <div style="float: right;">
            <el-button icon="Refresh" style="font-size: 16px; padding: 6px 12px;" @click="count" title="刷新统计图表"></el-button>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="24" style="margin-top: 16px;">
        <el-col :span="12">
          <div style="margin-left: 20px;">
            <i class="iconfont icon-icon_renwujincheng" style="font-size: 16pt; color: #90A0A5;"></i>
            <span class="span_14">7日请求总量</span>
          </div>
          <el-divider style="width: 80%;"></el-divider>
          <div id="apiAccessChart" class="chart_line_1"></div>
        </el-col>
        <el-col :span="12">
          <div style="margin-left: 20px;">
            <i class="iconfont icon-yibiaopan" style="font-size: 16pt; color: #90A0A5;"></i>
            <span class="span_14">24小时请求总量</span>
          </div>
          <el-divider style="width: 80%;"></el-divider>
          <div id="requestAccessChart" class="chart_line_1"></div>
        </el-col>
      </el-row>

      <el-row style="margin-top: 18px;">
        <el-col :span="10">&nbsp;</el-col>
        <el-col :span="14">
          <div style="float: right;">
            <el-input size="large" placeholder="请输入服务端名称" v-model="form.name" class="input-with-select" style="width: 520px;">
              <template #prepend>
                <el-select size="large" v-model="form.groupCode" placeholder="请选择分组" style="width: 150px; margin-right: 10px;">
                  <el-option label="所有" value=""/>
                  <el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value"/>
                </el-select>
              </template>
              <template #append>
                <el-button icon="Search" @click="search"></el-button>
              </template>
            </el-input>
          </div>
        </el-col>
      </el-row>

      <el-table :data="tableData" style="margin-top: 18px;">
        <el-table-column label="服务ID" width="300">
          <template #default="scope">
            <el-tag type="warning">{{scope.row.id}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="分组">
          <template #default="scope">
            <el-tag v-for="group in groupOptions" :key="group.value" v-show="(group.value === scope.row.groupCode)" type="">{{group.label}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="服务名称">
          <template #default="scope">
            <el-tag type="success">{{scope.row.name}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="服务地址" prop="uri" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="断言路径" prop="path"></el-table-column>
        <el-table-column label="最近请求时间" prop="createTime" width="220"></el-table-column>
        <el-table-column label="今日请求总次数" prop="count" width="140" style="font-weight: bold;"></el-table-column>
        <el-table-column label="查看" align="center" width="120">
          <template #default="scope">
            <el-button bg text type="primary" @click="handleShowRouteCount(scope.row)" title="请点击选中查看" icon="PieChart">
              统计
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block" style="margin-top: 20px;">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10,  30, 50]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalNum">
        </el-pagination>
      </div>
    </el-card>

    <RouteRequestCountComponent ref="routeRequestCount"></RouteRequestCountComponent>
  </div>
</template>

<script setup>
import RouteRequestCountComponent from './routeRequestCount.vue'
import { countRoutePageList, countRequestTotal } from '@/api/data/asset/count_api.js'
import { getCurrentInstance } from 'vue'

import * as echarts from "echarts";

/// 声明定义一下echart
const echart = echarts;

const { proxy } = getCurrentInstance()
const routeRequestCount = ref(null);

const form = {
  name: null,
  groupCode: null
}

let direction = 'rtl',
    dayCounts = [],
    hourCounts = [],
    tableData = ref([]),
    currentPage = 1,
    pageSize = 10,
    totalNum = ref(0),
    statusOptions = [
      { value: null, label: '所有' },
      { value: '0', label: '启用' },
      { value: '1', label: '禁用' },
    ],
    groupOptions = ref([
      { value: 'public_api', label: '公共API' },
      { value: 'external_api', label: '第三方API' },
      { value: 'interior_api', label: '内网API' },
      { value: 'pay_api', label: '支付API' },
      { value: 'other_api', label: '其它API' }
    ])

const init = () => {
  search()
  count()
}

const handleSizeChange = (val) => {
  pageSize = val
  search()
}

const handleCurrentChange = (val) => {
  currentPage = val
  search()
}

const handleShowRouteCount = (row) => {
  // RouteRequestCountComponent.refs.dialogFormVisible = true
  routeRequestCount.value.dialogFormVisible = true
  routeRequestCount.value.count(row)
}

const count = () => {
  countRequestTotal().then(function (result) {
    console.log(result)
    if (result.data) {
      drawLine('apiAccessChart', result.data.dayCounts)
      drawLine('requestAccessChart', result.data.hourCounts)
    }
  })
}

const search = () => {
  countRoutePageList({ name: form.name, groupCode: form.groupCode, currentPage, pageSize }).then(function (result) {
    if (result.data) {
      tableData.value = result.data.lists
      totalNum.value = result.data.totalNum
    }
  })
}

const drawLine = (id, result) => {
  console.log('id = ' + id)
  console.log(echart)

  let xAxisData = []
  let seriesData = []
  if (result !== undefined) {
    xAxisData = result.dates
    seriesData = result.counts
  }
  console.log("chart data", seriesData)

  let accessChart = echart.init(document.getElementById(id))
  var option = {
    color: '#1890FF',
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
    yAxis: {
      type: 'value'
    },
    xAxis: {
      type: 'category',
      data: xAxisData
    },
    series: [{
      data: seriesData,
      type: 'line',
      smooth: true
    }]
  }
  accessChart.setOption(option, true)
}

init() ;

</script>


<!-- <script>

import RouteRequestCountComponent from '@/components/RouteRequestCount.vue'
import {countRoutePageList, countRequestTotal} from '@/api/count_api.js'

export default {
	data() {
		return {
			form:{
				name:null,
				groupCode: null
			},
			direction: 'rtl',
			dayCounts: [],
			hourCounts: [],
			tableData: [],
			currentPage: 1,
			pageSize: 10,
			totalNum: 0,
			statusOptions: [
				{value: null, label: '所有'},
				{value: '0',label: '启用'},
				{value: '1',label: '禁用'},
			],
			groupOptions: this.GLOBAL_VAR.groups
		};
	},
	components:{
		RouteRequestCountComponent
	},
	created: function() {
		this.init();
	},
	mounted: function() {

	},
	beforeDestroy: function() {
	},
	methods:{
		init() {
			this.search();
			this.count();
		},
		handleSizeChange(val) {
			this.pageSize = val;
			this.search();
		},
		handleCurrentChange(val) {
			this.currentPage = val;
			this.search();
		},
		handleShowRouteCount(row){
			this.$refs.routeRequestCount.dialogFormVisible = true;
			this.$refs.routeRequestCount.count(row);
		},
		count(){
			let _this = this;
			countRequestTotal().then(function(result){
				console.log(result);
				if (result.data){
					_this.drawLine('apiAccessChart', result.data.dayCounts);
					_this.drawLine('requestAccessChart', result.data.hourCounts);
				}
			});
		},
		search(){
			let _this = this;
			countRoutePageList({name:this.form.name, groupCode: this.form.groupCode, currentPage: this.currentPage, pageSize: this.pageSize}).then(function(result){
				if (result.data){
					_this.tableData = result.data.lists;
					_this.totalNum = result.data.totalNum;
				}
			});
		},
		drawLine(id, result) {

			console.log('id = ' + id);
			console.log('echarts = ' + proxy.$echarts) ;
			console.log('echarts = ' + this.$echarts) ;

			let xAxisData =  [];
			let seriesData = [];
			if (result != undefined){
				xAxisData = result.dates;
				seriesData = result.counts;
			}
			console.log("chart data", seriesData);
			let _this = this;

			// 基于准备好的dom，初始化echarts实例
			let accessChart = this.$echarts.init(document.getElementById(id));
			var option = {
				color:'#1890FF',
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
				yAxis: {
					type: 'value'
				},
				xAxis: {
					type: 'category',
					//data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
					data: xAxisData
				},
				series: [{
					//data: [820, 932, 901, 934, 1290, 1330, 1320],
					data: seriesData,
					type: 'line',
					smooth: true
				}]
			};
			// 绘制图表
			accessChart.setOption(option, true);
		}
	}
};


</script> -->

<style>
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
.el-divider--horizontal {
  display: block;
  height: 1px;
  width: 97%;
  margin: 14px;
}
.span_1{
  font-size: 18pt;
  font-weight: bold;
  margin-bottom: 9px;
}
.span_14 {
  font-size: 14pt;
  color: #90A0A5;
  font-weight: bold;
  margin-left: 6px;
}
.chart_line_1 {
  width: 100%;
  height: 180px;
}
</style>
