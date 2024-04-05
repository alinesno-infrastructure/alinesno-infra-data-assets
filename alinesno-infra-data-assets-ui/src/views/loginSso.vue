<template>
  <div>
    this is a sso login page!!
  </div>
</template>

<script setup>

import router from '@/router'
import useUserStore from '@/store/modules/user'
import { setToken } from '@/utils/auth'
import { getParam } from '@/utils/ruoyi'

// 是否登录
const isLogin = ref(false);

debugger

// 获取参数
const back = getParam('back') || router.currentRoute.value.query.back;
const ticket = getParam('ticket') || router.currentRoute.value.query.ticket;

console.log('back = ' + back + ' , ticket = ' + ticket) ;
console.log('获取 back 参数：', back)
console.log('获取 ticket 参数：', ticket)

useUserStore().isSsoLogin().then((res) => {
  console.log('/isLogin 返回数据：', res);
  debugger
  isLogin.value = res.data;
}) ;

// 页面加载后触发
onMounted(() => {
  if(ticket) {
    doLoginByTicket(ticket);
  } else {
    goSsoAuthUrl();
  }
})

// 重定向至认证中心
function goSsoAuthUrl() {
    var clientLoginUrl =  location.origin + '/sso/login?back=' + encodeURIComponent(location.href);
    console.log('clientLoginUrl = ' + clientLoginUrl) ;

    useUserStore().goSsoAuthUrl(clientLoginUrl).then((res) => {
      console.log('/sso/getSsoAuthUrl 返回数据', res);

      debugger
      location.href = res.data;
    });
}

// 根据ticket值登录
function doLoginByTicket(ticket) {
    useUserStore().doLoginByTicket(ticket).then((res) => {
      console.log('/sso/getSsoAuthUrl 返回数据', res);

      debugger

      localStorage.setItem('satoken', res.data);
      setToken(res.adminToken);

      location.href = decodeURIComponent(back);
    });
}

</script>