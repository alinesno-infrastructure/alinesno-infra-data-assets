// jianglong
// global_variable.js
// 专门放置 全局变量

//const 声明常量,注意此处在webpack.config.js中做了代理转发
let baseHost = "127.0.0.1:8770"
let baseURL = "http://"+baseHost+"/api"
let gatewayRoutesURL = "http://127.0.0.1:8771"
const errMsg = "操作失败"
let successMsg = "操作成功"
let contentType = "application/json;charset=UTF-8"
let systemVersion = "snapshot-nacos.v.3.2"

// 分组类型，暂不放到数据字典，直接由前端添加
const groups = [
		{ value: 'public_api', label: '公共API' },
		{ value: 'external_api', label: '第三方API' },
		{ value: 'interior_api', label: '内网API' },
		{ value: 'pay_api', label: '支付API' },
		{ value: 'other_api', label: '其它API' }
	]


//导出全局变量
export default{
	baseHost,
	baseURL,
	gatewayRoutesURL,
	contentType,
	systemVersion,
	errMsg,
	successMsg,
	groups
}
