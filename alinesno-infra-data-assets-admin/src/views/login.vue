<template>
  <el-container class="login-container">
    <el-header style="z-index: 100;height: 45px;background: #3b5998;box-shadow: 0 2px 4px 0 var(--cb-color-shadow,rgba(0,0,0,.16));">
      <div>
        <div style="float: left;font-size: 30px;color: rgb(255, 255, 255);margin-top: 5px;">
          <img src="http://portal.infra.linesno.com/logo.png" style="width:35px" alt="">
        </div>
        <div class="banner-text">
          数据资产服务
        </div>
        <a href="http://portal.infra.linesno.com/" target="_blank" lass="banner-text" style="float: right;font-weight: 500;font-size: 13px;margin-top: 14px;color: rgb(255, 255, 255);">
          <i class="fas fa-link"></i> 官网
        </a>
      </div>
    </el-header>
    <el-main class="main-box" style="">

      <div class="login-content">
        <div class="marketing-wrapper">
          <img class="market-img" src="" alt="">
          <div class="title-header">
            <span class="main-title">利用AIP构筑创新业务 构建业务服务实现数字转型高质量发展</span>
            <span class="sub-title">个人和企业版本免费下载使用</span>
          </div>
        </div>
        <div class=" logo-box">
          <div class="wrapper">
            <div class="qr-method method ">
              <div class="alipay-channel">
                <div class="next-loading next-loading-inline" style="display: block; height: 100%;">
                  <div class="next-loading-wrap">
                    <div class="qrlogin-title">扫码登录</div>
                    <div class="qrcode-wrap app-text-wrap">
                      <div class="alipay-qrcode-wrapper ">
                        <img class="alipay-qrcode"
                          src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAZAAAAGQAQAAAACoxAthAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAAAmJLR0QAAd2KE6QAAAAJcEhZcwAAFxEAABcRAcom8z8AAAMpSURBVHja7ZwxcvNACIXxuHC5R9BRfDTraD6Kj6DShUf8Ah67sv+4SBGHzLytNPJ+aRAsPNiIfnsJESJEfhi5Siydz6pLPNvT6XHQ7XFa2v2oik1HIpWRyWy62ndwk+b2veitLYKN25Mc7N227kRqI9u7YyDNbW5fRGzUWSYFsn0ljcgvIPP5tlkyfVFOYdQ0IJE/hLh/3iOQwlMDFiJ/BekBVtPm8XMgGqH2TUwmUgkZaQ9i8oZ4srPu373JlIgUQvZrs/4DsLu0p0ISR24uIoWRzdKwvrgjR6bkT7OYSwNu6chEaiLhyJbgutM+lSVWa8YXYQXKi+8TKYdMEAY8Eo8K0ywdNtdAbBuRyohbNa2PWiUsvco4VF0L6jGZyKcQgX9G2pPSjb9rMGXKOUSqIlZX5kaBWGD7MoPFn5Hz3pGJlEQkstWoHBXZ6uJnpavkseZeYRKpiuiIxNnciLoyNNh0ZIfvREojKEZc44m6ckh4lx6no4ulRAoj2QXxqNtCTj/n8QqJPay/j8lECiLqDrrKcOTIj+5ArMJcs/4kUhrJLqXlR0uqPdLGQRvdEv8OlEhhJKyKVHeRiMSQ021jr1+wiHwG0eweX/zJWo4QcTRUuK9KUSIVkewjjxRHn7Jaa0iueaYSqYz46hsjxZFsPnZpNU5SItWR5RQ56k3GnAcyWOnDAfvjlUhRBHLA0GB9UCfbHJIdK32RCIhUQzxbVcTk3lFuQ8LL4Z2cviJSFXHrHzUXorM5dgy1Lm8EIiLlkJ4p7dUep9cYBICs91XSS6QUEpNWByg7cOTuvvuRACXyQcRSHMzaIAEaT2FK7b8SKY34ywfua8jIZWPA0UuTY7Yh70QqI1F4PI9NpRwQ4zm4FiCNSG2kL/vZXTqU12xz9HtX+wyWSEXkCtviJa4sLhg79mQH1+VGpkSkJjLZL2vkR+nIUypAJuEprK9EqiOL5M1HiHSXmMPCSB0GdbI1SaQ+0gf/3ZF7NSlDlx0nMpHSyGx7XkYCcNsKuk8j8kkEAXZ3Vtq5uJcN/lPhiJRERtrT/4PGdeqnpiTSb4wTqYp8dxEhQuRHkX9Z1cd7zqPOIwAAAABJRU5ErkJggg=="
                          alt="二维码">
                      </div>
                      <div class="methods-text">使用
                        <span class="methods">二维码登陆（集成中）</span>
                      </div>
                      <div class="methods-text hover-orange">下载AIP基设平台，快速提升</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="password-and-mobile-method method">
                  <div class="tabs-nav">
                    <div class="tabs-item active">
                      <div class="tabs-item-text">账号登录</div>
                    </div>
                  </div>
                  <div class="tab-content">
                    <div class="tab-content-item ">
                      <div>
                        <div id="infra-login-iframe" class="password-login-wrapper iframe-loaded">

                              <div class="login">
                                <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
                                  <el-form-item prop="username">
                                    <el-input
                                      v-model="loginForm.username"
                                      type="text"
                                      size="large"
                                      auto-complete="off"
                                      placeholder="账号"
                                    >
                                      <template #prefix><svg-icon icon-class="user" class="el-input__icon input-icon" /></template>
                                    </el-input>
                                  </el-form-item>
                                  <el-form-item prop="password">
                                    <el-input
                                      v-model="loginForm.password"
                                      type="password"
                                      size="large"
                                      auto-complete="off"
                                      placeholder="密码"
                                      @keyup.enter="handleLogin"
                                    >
                                      <template #prefix><svg-icon icon-class="password" class="el-input__icon input-icon" /></template>
                                    </el-input>
                                  </el-form-item>
                                  <el-form-item prop="code" v-if="captchaEnabled">
                                    <el-input
                                      v-model="loginForm.code"
                                      size="large"
                                      auto-complete="off"
                                      placeholder="验证码"
                                      style="width: 60%"
                                      @keyup.enter="handleLogin"
                                    >
                                      <template #prefix><svg-icon icon-class="validCode" class="el-input__icon input-icon" /></template>
                                    </el-input>
                                    <div class="login-code">
                                      <img :src="codeUrl" @click="getCode" class="login-code-img"/>
                                    </div>
                                  </el-form-item>
                                  <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
                                  <el-form-item style="width:100%;">
                                    <el-button
                                      :loading="loading"
                                      size="large"
                                      type="primary"
                                      style="width:100%;border:0px;background:#3b5998;"
                                      @click.prevent="handleLogin"
                                    >
                                      <span v-if="!loading">登 录</span>
                                      <span v-else>登 录 中...</span>
                                    </el-button>
                                    <div style="float: right;" v-if="register">
                                      <router-link class="link-type" :to="'/register'">立即注册</router-link>
                                    </div>
                                  </el-form-item>
                                </el-form>
                              </div>


                        </div>
                      </div>
                    </div>

                  </div>
            </div>
          </div>
          <div class="protocol">
            <div class="sc-eCImPb infra-dxc">登录视为您已同意<span class="hover-orange"><a class="protocol">第三方账号绑定协议</a>、<a
                  class="protocol">服务条款</a>、<a class="protocol">隐私政策</a></span></div>
          </div>

        </div>
      </div>
    </el-main>
    <el-footer style="background: rgb(250, 250, 250);position: fixed;bottom: 0px;width: 100%;height: 45px;">
      <div class="copyright-text">© 2009-2023 portal.infra.linesno.com 版权所有 ICP证：浙B2-20080101</div>
    </el-footer>
  </el-container>
</template>

<script setup>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";
import useUserStore from '@/store/modules/user'

const userStore = useUserStore()
const router = useRouter();
const { proxy } = getCurrentInstance();

const loginForm = ref({
  username: "admin",
  password: "admin123",
  rememberMe: false,
  code: "",
  uuid: ""
});

const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  code: [{ required: true, trigger: "change", message: "请输入验证码" }]
};

const codeUrl = ref("");
const loading = ref(false);
// 验证码开关
const captchaEnabled = ref(true);
// 注册开关
const register = ref(false);
const redirect = ref(undefined);

function handleLogin() {
  proxy.$refs.loginRef.validate(valid => {
    if (valid) {
      loading.value = true;
      // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
      if (loginForm.value.rememberMe) {
        Cookies.set("username", loginForm.value.username, { expires: 30 });
        Cookies.set("password", encrypt(loginForm.value.password), { expires: 30 });
        Cookies.set("rememberMe", loginForm.value.rememberMe, { expires: 30 });
      } else {
        // 否则移除
        Cookies.remove("username");
        Cookies.remove("password");
        Cookies.remove("rememberMe");
      }
      // 调用action的登录方法
      userStore.login(loginForm.value).then(() => {
        router.push({ path: redirect.value || "/" });
      }).catch(() => {
        loading.value = false;
        // 重新获取验证码
        if (captchaEnabled.value) {
          getCode();
        }
      });
    }
  });
}

function getCode() {
  getCodeImg().then(res => {
    captchaEnabled.value = res.captchaEnabled === undefined ? true : res.captchaEnabled;
    if (captchaEnabled.value) {
      codeUrl.value = "data:image/gif;base64," + res.img;
      loginForm.value.uuid = res.uuid;
    }
  });
}

function getCookie() {
  const username = Cookies.get("username");
  const password = Cookies.get("password");
  const rememberMe = Cookies.get("rememberMe");
  loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    password: password === undefined ? loginForm.value.password : decrypt(password),
    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
  };
}

getCode();
getCookie();
</script>

<style lang="scss">
</style>