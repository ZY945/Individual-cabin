<script>
import axios from 'axios'
import {onBeforeUnmount, ref, watchEffect} from "vue";
import {useRouter} from "vue-router";

export default {
  name: "ChatLogin",
  components: {},
  data() {
    return {
      feiShuUrl: '',
      email: '',
      code: '',
      username: '',
      password: '',
      emailActive: false,
      codeActive: false,
      userNameActive: false,
      passWordActive: false,
      loginType: 'account',
    }
  },
  methods: {
    login() {
      axios.post('/cabinChat/api/login', {
        username: this.username,
        password: this.password
      }).then(response => {
        if (response.data.code === 200) {
          const token = response.data.data.token
          localStorage.setItem("token", token)
          this.$router.push('/chatApp')
        } else {
          alert('Login error')
        }
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    loginByEmail() {
      axios.post('/oauth2/user/login/email', null, {
        params: {
          userEmail: this.email,
          code: this.code
        }
      }).then(response => {
        if (response.data.code === 200) {
          const token = response.data.data
          localStorage.setItem("token", token)
          this.$router.push('/chatApp')
        } else {
          alert('Captcha error')
        }
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    sendCode() {
      axios.get('/oauth2/user/login/email/sendCode', {
        params: {
          userEmail: this.email,
        }
      }).then(() => {
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    googleLogin() {
      axios.post('/oauth2/api/login', {
        username: this.username,
        password: this.password
      }).then(() => {
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    githubLogin() {
      axios.post('/oauth2/api/login', {
        username: this.username,
        password: this.password
      }).then(() => {
        this.$router.push('/')
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    dingtalkLogin() {
      axios.post('/oauth2/api/login', {
        username: this.username,
        password: this.password
      }).then(() => {
        this.$router.push('/')
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    feishuLogin() {
      axios.get('/oauth2/feishu/code', {}).then(response => {
        this.feiShuUrl = response.data;
        window.location.href = this.feiShuUrl;

      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
  },
  setup() {
    //vue3写法
    let code = ref(0);
    let intervalId;
    let router = useRouter();
    // 轮询函数
    const pollData = async () => {
      // 处理轮询逻辑
      const params = window.location.search;
      const searchParams = new URLSearchParams(params);
      code.value = searchParams.get('code');//feiShuUserId
      if (code.value != null) {
        loginByFeiShuCode(code.value);
        clearInterval(intervalId);
      }
    };

    // 监听数据变化并轮询
    watchEffect(() => {
      const params = window.location.search;
      const searchParams = new URLSearchParams(params);
      code.value = searchParams.get('code');//feiShuUserId
      if (code.value != null) {//TODO 有参数时在进行,这里可以优化成直接调用,但是目前报错,不知道如何修改
        intervalId = setInterval(pollData, 1); // 每隔 1ms 轮询一次
      }
    });

    const loginByFeiShuCode = (code) => {
      axios.get('/oauth2/feishu/access_token', {
        params: {
          code: code
        }
      }).then(response => {
        if (response.data.data.token === null) {
          window.localStorage.setItem('userId', JSON.stringify(response.data.data.userId))
          router.push('/bind')
        } else {
          const token = response.data.data.token
          //存储
          window.localStorage.setItem('token', JSON.stringify(token))
          router.push('/chatApp')
        }
      }).catch(() => {
        alert('Invalid login credentials')
      })
    };
    // 在组件卸载时停止轮询
    onBeforeUnmount(() => {
      clearInterval(intervalId);
    });
  }
}
</script>
<template>
  <v-app>
    <v-main>
      <div class="background">
        <v-card class="login-app">
          <div class="form-table-name">
            <div class="Account_login_loginBox_tab">
              <button @click="loginType='account'">Account</button>
            </div>
            <div class="Mail_login_loginBox_tab">
              <button @click="loginType='email'">Email</button>
            </div>
          </div>
          <v-card-text class="form-account-app" v-if="loginType === 'account'">
            <v-form>
              <div>
                <a class="form-label">username</a>
                <div class="form-text">
                  <v-text-field v-model="username"
                                :class="{ 'form-text-active': userNameActive }"
                                @mouseover="userNameActive = true"
                                @mouseleave="userNameActive = false"
                                class="form-text-wrapper"/>
                </div>
              </div>
              <div class="form-text-wrapper" style="margin-top: 10px">
                <a class="form-label">password</a>
                <div class="form-text">
                  <v-text-field v-model="password"
                                type="password"
                                :class="{ 'form-text-active': passWordActive }"
                                @mouseover="passWordActive = true"
                                @mouseleave="passWordActive = false"/>
                </div>
              </div>
            </v-form>
            <v-card-actions>
              <v-btn color="white" @click="login()" class="login-btn">Login</v-btn>
            </v-card-actions>
          </v-card-text>
          <v-card-text class="form-account-app" v-if="loginType === 'email'">
            <v-form>
              <div>
                <a class="form-label">email</a>
                <div class="form-text">
                  <v-text-field v-model="email"
                                :class="{ 'form-text-active': emailActive }"
                                @mouseover="emailActive = true"
                                @mouseleave="emailActive = false"
                                class="form-text-wrapper"/>
                </div>
              </div>
              <div class="form-text-wrapper" style="margin-top: 10px">
                <a class="form-label">code</a>
                <div class="form-text" style="display: flex; margin-top: 10px; align-items: center;">
                  <v-text-field v-model="code"
                                type="password"
                                :class="{ 'form-text-active': codeActive }"
                                @mouseover="codeActive = true"
                                @mouseleave="codeActive = false"
                  />
                  <v-btn color="white" style="margin-left: 10px;" @click="sendCode()">Send Code</v-btn>
                </div>
              </div>
            </v-form>
            <v-card-actions>
              <v-btn color="white" @click="loginByEmail()" class="login-btn">Login</v-btn>
            </v-card-actions>
          </v-card-text>
          <div class="login-png-app">
            <v-btn @click="googleLogin()" class="login-png">
              <img src="../../assets/img/google.svg" alt="谷歌登录" width="30">
            </v-btn>
            <v-btn @click="githubLogin()" class="login-png">
              <img src="../../assets/img/github.svg" alt="github登录" width="30">
            </v-btn>
            <v-btn @click="dingtalkLogin()" class="login-png">
              <!-- Ant Design 官方图标库：https://www.iconfont.cn/collections/detail?cid=9402 -->
              <img src="../../assets/img/dingtalk.svg" alt="钉钉扫码登录" width="30">
            </v-btn>
            <v-btn @click="feishuLogin()" class="login-png">
              <!-- Ant Design 官方图标库：https://www.iconfont.cn/collections/detail?cid=9402 -->
              <img src="../../assets/img/feishu.svg" alt="飞书扫码登录" width="30">
            </v-btn>
          </div>
        </v-card>
      </div>
    </v-main>
  </v-app>

</template>

<style>
.login-app {
  max-width: 300px;
  max-height: 488px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgb(26, 26, 42);
}

.background {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 20px;
  background: linear-gradient(rgb(131, 12, 231), rgb(16, 47, 154)); /* 标准的语法 */
}


.form-account-app {
  max-width: 260px;
  max-height: 230px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgb(26, 26, 42);
}

.form-table-name {
  display: flex;
  justify-content: space-between; /* 将两个按钮左右分别对齐 */
  align-items: flex-start; /* 将两个按钮顶部对齐 */
}

.Account_login_loginBox_tab {
  display: inline-block;
  max-width: 130px;
  max-height: 245px;
  font-size: 30px;
  color: white;
}

.Mail_login_loginBox_tab {
  display: inline-block;
  max-width: 130px;
  max-height: 245px;
  font-size: 30px;
  color: white;
}

.login-btn {
  max-width: 98px;
  max-height: 44px;
  margin: 20px auto;
  background: linear-gradient(rgb(74, 164, 231), rgb(74, 70, 204)); /* 标准的语法 */
}

.bind-style {
  max-width: 1000px;
  max-height: 1000px;
}

.register-btn {
  max-width: 98px;
  max-height: 44px;
  margin: 0 auto;
  background: linear-gradient(rgb(74, 164, 231), rgb(74, 70, 204)); /* 标准的语法 */
}

.login-png-app {
  display: flex;
  justify-content: center;
  width: 30px;
  margin: 20px auto;
}

.login-png {
  width: 30px;
  height: 30px;
  margin: 0 auto;
  border-radius: 50%;
  background: linear-gradient(rgb(74, 164, 231), rgb(74, 70, 204)); /* 标准的语法 */
}

.form-label {
  color: white;
}

.form-text-wrapper {
  max-width: 236px;
  max-height: 88px;
  position: relative;
}

.form-text-active {
  max-width: 236px;
  max-height: 44px;
  background-color: rgb(58, 58, 72);
}

.form-text {
  max-width: 236px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
}
</style>