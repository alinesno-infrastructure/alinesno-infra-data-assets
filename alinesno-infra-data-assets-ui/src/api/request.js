// jianglong
//request.js
import axios from "axios";
import qs from "qs";
import app from "../main.js";

//引用全局变量文件
import GLOBAL_FUN from './global_function.js'
import GLOBAL_VAR from './global_variable.js'

//测试token
//let token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaXNzIjoicHJvLXNlcnZlciJ9.4-eF585mt-AcixNoeUbX-30Jc6uq8Eiz7tqrQUGrCM8'

/****** 创建axios实例 ******/
const request = axios.create({
	baseURL: GLOBAL_VAR.baseURL, // api的base_url
	timeout: 30000 ,// 请求超时时间
	withCredentials:true//启用session,每次请求带sessionid
});

/****** request拦截器==>对请求参数做处理 ******/
request.interceptors.request.use(config => {
	 //config.method === 'get' ? config.params = qs.stringify({ ...config.data}) : config.params = { ...config.params };
	if (config.method === 'get'){
		config.params = {...config.data};
	}else {
		config.params = {...config.params};
	}
	if (config.loading === false){
	}else {
		GLOBAL_FUN.startLoading()
	}
	//json数据格式进行前后端交付
	config.headers['Content-Type'] = GLOBAL_VAR.contentType;	
	//添加登录token到header中
	let token = GLOBAL_FUN.getToken();
	if (token && token !== null && token !=='null'){
		config.headers['token'] = token ;
	}
	return config;
}, error => { //请求错误处理
	GLOBAL_FUN.endLoading()
	Promise.reject(error)
});


/****** respone拦截器==>对响应做处理 ******/
request.interceptors.response.use(
	response => { //成功请求到数据
		GLOBAL_FUN.endLoading();
		console.log('response');
		//console.log(response);
		//这里根据后端提供的数据进行对应的处理
		if (response.data) {
			let code = response.data.code ;
			let msg = response.data.msg;
			if (code == '1') {
				return response.data;
			} else if (code == '-1'){
				logout();				
			}
			GLOBAL_FUN.errorMsg(msg);
		} else {
			return response;
		}
	},
	error => { //响应错误处理
		console.log(error);		
		// let text = JSON.parse(JSON.stringify(error)).response.status === 404 ?'404':'网络异常，请重试';
		GLOBAL_FUN.endLoading();
		let msg = "操作失败";
		if (error) {
			if (error.response){
				if (error.response.status == 404 || error.response.status == 504) {
					msg = '网络异常，请稍后重试'
				} else if (error.response.status == 403) {
					msg = '您无权限访问该资源'
				} else {
					if (error.response.data){
						if (error.response.data.msg){
							msg += ": " +error.response.data.msg;
						}
						if (error.response.data.code == '-1') {
							console.log(msg);
							GLOBAL_FUN.errorMsg(msg);
						}
					}
				}
			}
		}
		GLOBAL_FUN.errorMsg(msg);
		return Promise.reject(error)
	}
);

export default request;
