import { login, ssoLogout , logout, getInfo , goSsoAuthUrl , doLoginByTicket , isSsoLogin } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import defAva from '@/assets/images/profile.jpg'

const useUserStore = defineStore(
  'user',
  {
    state: () => ({
      token: getToken(),
      nickName: '' ,
      name: '',
      avatar: '',
      roles: [],
      org: {},
      dept: [],
      permissions: []
    }),
    actions: {
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sso_start >>>>>>>>>>>>>>>>.
      // 重定向至认证中心
      goSsoAuthUrl(clientLoginUrl) {
        return new Promise((resolve, reject) => {
          goSsoAuthUrl(clientLoginUrl).then(res => {
            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 根据ticket值登录
      doLoginByTicket(ticket) {
        return new Promise((resolve, reject) => {
          doLoginByTicket(ticket).then(res => {
            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 查询当前会话是否登录
      isSsoLogin() {
        return new Promise((resolve, reject) => {
          isSsoLogin().then(res => {
            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> sso_end >>>>>>>>>>>>>>>>.
      // 登录
      login(userInfo) {
        const username = userInfo.username.trim()
        const password = userInfo.password
        const code = userInfo.code
        const uuid = userInfo.uuid
        return new Promise((resolve, reject) => {
          login(username, password, code, uuid).then(res => {
            setToken(res.token)
            this.token = res.token
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 获取用户信息
      getInfo() {
        return new Promise((resolve, reject) => {
          getInfo().then(res => {
            const user = res.user
            const avatar = (user.avatar == "" || user.avatar == null) ? defAva : 'data:image/jpeg;base64,' + user.avatar;

            if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
              this.roles = res.roles
              this.permissions = res.permissions
            } else {
              this.roles = ['ROLE_DEFAULT']
            }

            this.name = user.userName
            this.nickName = user.nickName

            this.avatar = avatar;
            this.dept = user.dept
            this.org = user.org

            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 退出系统
      logOut() {
        return new Promise((resolve, reject) => {
          logout(this.token).then(() => {
            this.token = ''
            this.roles = []
            this.permissions = []
            removeToken()
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 单点登陆退出
      ssoLogOut() {
        return new Promise((resolve, reject) => {
          ssoLogout().then(() => {
            removeToken()
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      }
    }
  })

export default useUserStore