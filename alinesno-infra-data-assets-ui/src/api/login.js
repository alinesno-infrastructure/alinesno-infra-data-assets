import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sso_start >>>>>>>>>>>>>>>>>>>>>>>>>>>>
// 查询当前会话是否登录
export function isSsoLogin(){
  return request({
    url: '/sso/isLogin',
    method: 'get'
  })
}

// 用户退出
export function ssoLogout() {
  return request({
    url: '/sso/logout' ,
    method: 'get'
  })
}

// 根据ticket值登录
export function doLoginByTicket(ticket){
  return request({
    url: '/sso/doLoginByTicket?ticket=' + ticket,
    method: 'get'
  })
}

// 重定向至认证中心
export function goSsoAuthUrl(clientLoginUrl){
  return request({
    url: '/sso/getSsoAuthUrl?clientLoginUrl=' + clientLoginUrl,
    method: 'get'
  })
}
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sso_start >>>>>>>>>>>>>>>>>>>>>>>>>>>>


// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}