<script>
import axios from "axios";

export default {
  name: "RegisterAccount",
  props: {},
  components: {},
  data() {
    return {
      registerUsername: '',
      registerEmail: '',
      registerPassword: '',
      registerUserNameActive: false,
      registerEmailActive: false,
      registerPassWordActive: false,
    }
  },
  methods: {
    register() {
      axios.post('/oauth2/register/account', null, {
        params: {
          userName: this.registerUsername,
          email: this.registerEmail,
          passWord: this.registerPassword,
        }
      }).then(response => {
        const token = response.data.data
        if (response.data.code === 200) {
          //存储
          window.localStorage.setItem('token', JSON.stringify(token))
          this.$router.push('/')
        } else {
          alert('注册失败')
        }
      }).catch(() => {
        alert('注册失败')
      })
    },
  },
}
</script>
<template>
  <v-app>
    <v-main>
      <div class="registerBackground">
        <v-card class="register-app">
          <div class="form-table-name">
            <div class="Account_login_loginBox_tab">
              <button>Account</button>
            </div>
          </div>
          <v-card-text class="form-account-app">
            <v-form>
              <div>
                <a class="form-label">Username</a>
                <div class="form-text">
                  <v-text-field v-model="registerUsername"
                                :class="{ 'form-text-active': registerUserNameActive }"
                                @mouseover="registerUserNameActive = true"
                                @mouseleave="registerUserNameActive = false"
                                class="form-text-wrapper"/>
                </div>
              </div>
              <div class="form-text-wrapper" style="margin-top: 10px">
                <a class="form-label">Email</a>
                <div class="form-text">
                  <v-text-field v-model="registerEmail"
                                type="registerPassword"
                                :class="{ 'form-text-active': registerEmailActive }"
                                @mouseover="registerEmailActive = true"
                                @mouseleave="registerEmailActive = false"/>
                </div>
              </div>
              <div class="form-text-wrapper" style="margin-top: 10px">
                <a class="form-label">Password</a>
                <div class="form-text">
                  <v-text-field v-model="registerPassword"
                                type="registerPassword"
                                :class="{ 'form-text-active': registerPassWordActive }"
                                @mouseover="registerPassWordActive = true"
                                @mouseleave="registerPassWordActive = false"/>
                </div>
              </div>
            </v-form>
            <v-card-actions>
              <v-btn color="white" @click="register()" class="login-btn">Register</v-btn>
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
  max-height: 488px;
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


.form-account-app {
  max-width: 260px;
  max-height: 330px;
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