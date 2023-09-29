// jianglong
//global_function.js
// 专门放置 全局函数
// import {Loading,Message} from 'element-ui'

import { ref } from 'vue'
import { ElLoading , ElMessage  } from 'element-plus'

//let 声明变量
const err_msg = "操作失败"
const success_msg = "操作成功"
let loading;

/**
 * 加载中的样式
 */
function startLoading() {
	loading = ElLoading.service({
		lock: true,
		text: '加载中....',
		background: 'rgba(0, 0, 0, 0.7)'
	})
}

/**
 * 关闭加载中的样式
 */
function endLoading() {
	loading.close()
}

/**
 * 成功消息
 * @param {Object} msg		内容
 * @param {Object} duration 时长
 */
function successMsg(msg, duration) {
	msg = (msg) ? msg : success_msg
	ElMessage ({
		message: msg,
		showClose: true,
		type: 'success',
		duration : duration == undefined ? 3000: duration
	});
}

/**
 * 失败消息
 * @param {Object} msg		内容
 * @param {Object} duration 时长
 */
function errorMsg(msg, duration) {
	msg = (msg) ? msg : err_msg
	Message({
		message: msg,
		showClose: true,
		type: 'error',
		duration : duration == undefined ? 3000: duration
	});
}

/**
 * 打开新的窗口
 * @param {Object} _this
 * @param {Object} path
 * @param {Object} tabTitle
 * @param {Object} query
 */
function openNewTab(_this,path,tabTitle,query){
	//调用父类的方法，打开新的窗口
	_this.$root.$children[0].$children[0].addTab(path,tabTitle,query);
}

/**
 * 获取Tab的对象与传参
 * @param {Object} _this
 * @param {Object} tabTitle
 */
function getOpenTab(_this,tabTitle){
	//调用父类的方法，获取指定打开的窗口
	return _this.$root.$children[0].$children[0].getTab(tabTitle);
}

/**
 * 将信息存储到本地sessionStorage中,sessionStorage仅在当前会话下有效，关闭页面或浏览器后被清除
 * @param {Object} key
 * @param {Object} value
 */
function setSessionStorage(key,value){
	if(!window.sessionStorage){
	    alert("浏览器不支持localstorage");
	    return false;
	}else{
		removeSessionStorage(key);
	    window.sessionStorage.setItem(key, value);
	}
}

/**
 * 获取存储到本地sessionStorage中key对应的值
 * @param {Object} key
 */
function getSessionStorage(key){
	return window.sessionStorage.getItem(key);
}

/**
 * 删除存储到本地sessionStorage中key对应的值
 * @param {Object} key
 */
function removeSessionStorage(key){
	return window.sessionStorage.removeItem(key);
}

/**
 * 将信息存储到本地localStorage中
 * @param {Object} key
 * @param {Object} value
 */
function setLocalStorage(key,value){
	if(!window.localStorage){
	    alert("浏览器不支持localstorage");
	    return false;
	}else{
		removeLocalStorage(key);
	    window.localStorage.setItem(key, value);
	}
}

/**
 * 获取存储到本地localStorage中key对应的值
 * @param {Object} key
 */
function getLocalStorage(key){
	return window.localStorage.getItem(key);
}

/**
 * 删除存储到本地localStorage中key对应的值
 * @param {Object} key
 */
function removeLocalStorage(key){
	return window.localStorage.removeItem(key);
}

/**
 * 存储登录token
 * @param {Object} token
 */
function setToken(token){
	setLocalStorage('token', token);
}

/**
 * 获取token
 */
function getToken(){
	return getLocalStorage('token');
}

/**
 * 删除token
 */
function removeToken(){
	return removeLocalStorage('token');
}

/**
 * 遍历生成菜单树
 * @param {Object} pcode
 * @param {Object} data
 */
function appendTree(pcode, data){
	let treeData = [];
	let _data = data;
	data.forEach((row, index) => {
		if (pcode == row.pcode){
			let newChild = {
				id: row.id,
				code: row.code,
				pcode: row.pcode,
				label: row.name,
				children: appendTree(row.code, _data)
			}
			treeData.push(newChild);
		}
	});
	return treeData;
}

/**
 * 获取原始数据，加工生成树结构
 * @param {Object} data
 */
function initTree(data) {
	let treeData = [];
	data.forEach((row, index) => {
		let newChild = {
			id: row.id,
			code: row.code,
			pcode: row.pcode,
			label: row.name,
			children: [],
		}
		//将当前数据对象与树中的所有节点进行比较，data 是否为父节点或子节点
		let t;
		for (let i = 0; i < treeData.length; i++) {
			t = append(treeData[i], newChild);
			if (t == 1) { //表示当前treeData[i] 是newChild的父节点，找到父节点后，不在遍历
				break;
			} else if (t == 2) { //表示当前treeData[i] 是newChild的子节点，则继续遍历treeData，查找是否还有其它子节点
				treeData[i] = {};
				t = undefined;
			}
		}
		if (t == undefined) {
			treeData.push(newChild);
		}
	});
	let dataArray = [];
	treeData.forEach(d => {
		if (d != undefined && d.id) {
			dataArray.push(d);
		}
	})
	console.log(dataArray);
	return dataArray;
}

/**
 * 判断树节点关系
 * @param {Object} data
 * @param {Object} newChild
 */
function append(data, newChild) {
	if (data.code == newChild.pcode) {
		if (data.children) {
			data.children[data.children.length] = newChild;
			return 1; //表示child是data子节点
		}
	} else if (data.pcode == newChild.code) {
		newChild.children[newChild.children.length] = data;
		return 2; //表示child是data父节点
	} else if (data.children && data.children.length > 0) {
		let t;
		for (let i = 0; i < data.children.length; i++) {
			t = append(data.children[i], newChild);
			if (t == 1) {
				break;
			}
		}
		return t;
	}
	return undefined; //表示child与data无父子关系
}

/**
 * 复制内容
 * @param {Object} data 
 */
function copy(data){
	let inputObj = document.createElement('input');
	inputObj.value = data;
	document.body.appendChild(inputObj);
	inputObj.select();
	document.execCommand('Copy');
	successMsg('复制成功');
	inputObj.remove();
}

//导出全局变量
export default {
	errorMsg,
	successMsg,
	startLoading,
	endLoading,
	openNewTab,
	getOpenTab,
	getToken,
	setToken,
	removeToken,
	initTree,
	appendTree,
	getLocalStorage,
	setLocalStorage,
	removeLocalStorage,
	getSessionStorage,
	setSessionStorage,
	removeSessionStorage,
	copy
}
