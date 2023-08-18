<script>
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";
import {set5mCookie} from "@/assets/js/utils";

export default {
  setup() {
    let registerUsername = ref('');
    let registerEmail = ref('');
    let registerEmailCode = ref('');
    let registerPassword = ref('');
    let registerUserNameActive = ref(false);
    let registerEmailActive = ref(false);
    let registerEmailCodeActive = ref(false);
    let registerPassWordActive = ref(false);
    let router = useRouter();

    const register = () => {
      axios.post('/oauth2/register/account', null, {
        params: {
          userName: registerUsername.value,
          email: registerEmail.value,
          code: registerEmailCode.value,
          passWord: registerPassword.value,
        }
      }).then(response => {
        const token = response.data.data
        if (response.data.code === 200) {
          // 存储
          window.localStorage.setItem('token', JSON.stringify(token))
          // 存储cookie,后端从请求获取的
          set5mCookie("token", JSON.stringify(token))
          router.push('/')
        } else {
          alert('注册失败')
        }
      }).catch(() => {
        alert('注册失败')
      })
    };
    const sendCode = () => {
      if (registerEmail.value != null && registerEmail.value !== "") {
        axios.get('/oauth2/register/email/sendCode', {
          params: {
            userEmail: registerEmail.value,
          }
        }).then(() => {
        }).catch(() => {
          alert('Invalid login credentials')
        })
      } else {
        alert("请输入正确邮箱");
      }
    };

    return {
      sendCode,
      register,
      registerUsername,
      registerEmail,
      registerEmailCode,
      registerPassword,
      registerUserNameActive,
      registerEmailActive,
      registerEmailCodeActive,
      registerPassWordActive,
    }
  }
}
</script>
<template>
  <v-app>
    <v-main>
      <div class="registerBackground">
        <v-card class="register-app">
          <div class="register-form-table-name">
            <div class="Account_login_loginBox_tab">
              <button>Account</button>
            </div>
          </div>
          <v-card-text class="register-form-account-app">
            <v-form>
              <div>
                <a class="register-form-label">Username</a>
                <div class="register-form-text">
                  <v-text-field v-model="registerUsername"
                                :class="{ 'register-form-text-active': registerUserNameActive }"
                                @mouseover="registerUserNameActive = true"
                                @mouseleave="registerUserNameActive = false"
                                class="register-form-text-wrapper"/>
                </div>
              </div>
              <div class="register-form-text-wrapper" style="margin-top: 10px">
                <a class="register-form-label">Email</a>
                <div class="register-form-text">
                  <v-text-field v-model="registerEmail"
                                :class="{ 'register-form-text-active': registerEmailActive }"
                                @mouseover="registerEmailActive = true"
                                @mouseleave="registerEmailActive = false"/>
                </div>
              </div>
              <div class="register-form-text-wrapper" style="margin-top: 15px;">
                <a class="register-form-label">code</a>
                <div class="register-form-text-code-all">
                  <div class="register-form-text-code">
                    <v-text-field v-model="registerEmailCode"
                                  type="password"
                                  :class="{ 'register-form-text-active': registerEmailCodeActive }"
                                  @mouseover="registerEmailCodeActive = true"
                                  @mouseleave="registerEmailCodeActive = false"/>
                  </div>
                  <v-btn class="register-code-btn" color="white" @click="sendCode()">Send Code</v-btn>

                </div>
              </div>
              <div class="register-form-text-wrapper" style="margin-top: 10px">
                <a class="register-form-label">Password</a>
                <div class="register-form-text">
                  <v-text-field v-model="registerPassword"
                                type="password"
                                :class="{ 'register-form-text-active': registerPassWordActive }"
                                @mouseover="registerPassWordActive = true"
                                @mouseleave="registerPassWordActive = false"/>
                </div>
              </div>
            </v-form>
            <v-card-actions>
              <v-btn color="white" @click="register()" class="register-login-btn">Register</v-btn>
            </v-card-actions>
          </v-card-text>
        </v-card>
      </div>
    </v-main>
  </v-app>

</template>

<style>
.register-app {
  justify-content: center;
  align-items: center;
  max-width: 300px;
  max-height: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgb(26, 26, 42);
}

.registerBackground {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 20px;
  background: linear-gradient(rgb(204, 204, 204), rgb(16, 47, 154)); /* 标准的语法 */
}


.register-form-account-app {
  max-width: 260px;
  max-height: 450px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgb(26, 26, 42);
}

.register-form-table-name {
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

.register-login-btn {
  max-width: 98px;
  max-height: 44px;
  margin: 20px auto;
  background: linear-gradient(rgb(74, 164, 231), rgb(74, 70, 204)); /* 标准的语法 */
}

.register-code-btn {
  max-width: 110px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
}

.register-form-label {
  color: white;
}

.register-form-text-wrapper {
  max-width: 236px;
  max-height: 88px;
  position: relative;
}

.register-form-text-active {
  max-width: 236px;
  max-height: 44px;
  background-color: rgb(58, 58, 72);
}

.register-form-text {
  max-width: 236px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
}

.register-form-text-code-all {
  max-width: 236px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
  display: flex;
  align-items: center;
}

.register-form-text-code {
  max-width: 110px;
  max-height: 44px;
  background-color: rgb(7, 7, 14);
  color: white;
  margin-left: 10px;
}
</style>