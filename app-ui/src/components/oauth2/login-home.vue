<script>
import axios from 'axios'
import {onBeforeUnmount, ref, watchEffect} from "vue";
import {useRouter} from "vue-router";

export default {
  setup() {
    //vue3写法
    //重定向后会销毁实例,参数会初始化(可以把第三方登录的服务提供作为重定向url的参数,code会&到后面)
    //两种方法,使用父组件去保存,传入子组件
    let code = ref('');
    let eMailCode = ref('');
    let feiShuUrl = ref('');
    let gitHubUrl = ref('');
    let giteeUrl = ref('');
    let email = ref('');
    let username = ref('');
    let password = ref('');
    let emailActive = ref(false);
    let codeActive = ref(false);
    let userNameActive = ref(false);
    let passWordActive = ref(false);
    let loginType = ref('account');
    let intervalId;
    let router = useRouter();
    // 轮询函数
    const pollData = async () => {
      // 处理轮询逻辑
      const params = window.location.search;
      const searchParams = new URLSearchParams(params);
      code.value = searchParams.get('code');//feiShuUserId
      // const oauthType = window.localStorage.getItem('oauthType');
      const oauthType = searchParams.get('oauthType');
      if (code.value != null) {
        console.log(oauthType);
        if (oauthType === 'feishu') {
          console.log(oauthType);
          loginByFeiShuCode(code.value);
        } else if (oauthType === "gitee") {
          loginByGiteeCode(code.value);
        } else if (oauthType === "github") {
          loginByGitHubCode(code.value);
        }

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

    const feiShuLogin = () => {
      axios.get('/oauth2/feishu/code', {}).then(response => {
        feiShuUrl.value = response.data;
        window.location.href = feiShuUrl.value;

      }).catch(() => {
        alert('Invalid login credentials')
      })
    };

    const gitHubLogin = () => {
      axios.get('/oauth2/github/code', {}).then(response => {
        gitHubUrl.value = response.data;
        window.location.href = gitHubUrl.value;

      }).catch(() => {
        alert('Invalid login credentials')
      })
    }

    const login = () => {
      axios.post('/oauth2/login/account', null, {
        params: {
          userName: username.value,
          passWord: password.value
        }
      }).then(response => {
        if (response.data.code === 200) {
          const token = response.data.data
          window.localStorage.setItem('token', JSON.stringify(token))
          router.push('/chatApp')
        } else {
          alert('账户登录失败')
        }
      }).catch(() => {
        alert('账户登录异常')
      })
    };
    const loginByEmail = () => {
      if (eMailCode.value != null && eMailCode.value !== "") {
        axios.post('/oauth2/login/email', null, {
          params: {
            userEmail: email.value,
            code: eMailCode.value
          }
        }).then(response => {
          if (response.data.code === 200) {
            const token = response.data.data
            window.localStorage.setItem('token', JSON.stringify(token))
            router.push('/chatApp')
          } else {
            alert('Captcha error')
          }
        }).catch(() => {
          alert('Invalid login credentials')
        })
      } else {
        alert("请输入正确验证码格式");
      }
    };
    const sendCode = () => {
      if (email.value != null && email.value !== "") {
        axios.get('/oauth2/login/email/sendCode', {
          params: {
            userEmail: email.value,
          }
        }).then(() => {
        }).catch(() => {
          alert('Invalid login credentials')
        })
      } else {
        alert("请输入正确邮箱");
      }

    };
    const giteeLogin = () => {
      axios.get('/oauth2/gitee/code', {}).then(response => {
        feiShuUrl.value = response.data;
        window.location.href = feiShuUrl.value;

      }).catch(() => {
        alert('Invalid login credentials')
      })
    };
    const loginByFeiShuCode = (code) => {
      axios.get('/oauth2/feishu/access_token', {
        params: {
          code: code
        }
      }).then(response => {
        if (response.data.data.token === null) {
          window.localStorage.setItem('feiShuId', JSON.stringify(response.data.data.userId))
          router.push('/bind')
        } else {
          const token = response.data.data.token
          // window.localStorage.removeItem("feiShuId");
          //存储
          window.localStorage.setItem('token', JSON.stringify(token))
          router.push('/chatApp')
        }
      }).catch(() => {
        alert('Invalid login credentials')
      })
    };

    const loginByGitHubCode = (code) => {
      axios.get('/oauth2/github/access_token', {
        params: {
          code: code
        }
      }).then(response => {
        if (response.data.data.token === null) {
          window.localStorage.setItem('gitHubId', JSON.stringify(response.data.data.userId))
          router.push('/bind')
        } else {
          const token = response.data.data.token
          // window.localStorage.removeItem("gitHubId");
          //存储
          window.localStorage.setItem('token', JSON.stringify(token))
          router.push('/chatApp')
        }
      }).catch(() => {
        alert('Invalid login credentials')
      })
    };

    const loginByGiteeCode = (code) => {
      axios.get('/oauth2/gitee/access_token', {
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
    //暴露方法
    return {
      feiShuLogin,
      gitHubLogin,
      login,
      loginByEmail,
      giteeLogin,
      sendCode,
      code,
      eMailCode,
      feiShuUrl,
      gitHubUrl,
      giteeUrl,
      email,
      username,
      password,
      emailActive,
      codeActive,
      userNameActive,
      passWordActive,
      loginType,
    }
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
              <div class="form-text-wrapper" style="margin-top: 15px;">
                <a class="form-label">code</a>
                <div class="form-text-code-all">
                  <div class="form-text-code">
                    <v-text-field v-model="eMailCode"
                                  type="password"
                                  :class="{ 'form-text-active': codeActive }"
                                  @mouseover="codeActive = true"
                                  @mouseleave="codeActive = false"/>
                  </div>
                  <v-btn class="code-btn" color="white" @click="sendCode()">Send Code</v-btn>

                </div>
              </div>
            </v-form>
            <v-card-actions>
              <v-btn color="white" @click="loginByEmail()" class="login-btn">Login</v-btn>
            </v-card-actions>
          </v-card-text>
          <div class="login-png-app">
            <v-btn @click="googleLogin" class="login-png">
              <img src="../../assets/img/google.svg" alt="谷歌登录" width="30">
            </v-btn>
            <v-btn @click="gitHubLogin()" class="login-png">
              <img src="../../assets/img/github.svg" alt="github登录" width="30">
            </v-btn>
            <v-btn @click="dingtalkLogin" class="login-png">
              <!-- Ant Design 官方图标库：https://www.iconfont.cn/collections/detail?cid=9402 -->
              <img src="../../assets/img/dingtalk.svg" alt="钉钉扫码登录" width="30">
            </v-btn>
            <v-btn @click="feiShuLogin()" class="login-png">
              <!-- Ant Design 官方图标库：https://www.iconfont.cn/collections/detail?cid=9402 -->
              <img src="../../assets/img/feishu.svg" alt="飞书扫码登录" width="30">
            </v-btn>
          </div>
          <div class="login-png-app">
            <v-btn @click="giteeLogin()" class="login-png">
              <!-- Ant Design 官方图标库：https://www.iconfont.cn/collections/detail?cid=9402 -->
              <img src="../../assets/img/gitee.svg" alt="码云扫码登录" width="30">
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

.code-btn {
  max-width: 110px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
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

.form-text-code-all {
  max-width: 236px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
  display: flex;
  align-items: center;
}

.form-text-code {
  max-width: 110px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
  margin-left: 10px;
}
</style>