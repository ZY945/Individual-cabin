<script>
import axios from 'axios'

export default {
  name: "ChatLogin",
  data() {
    return {
      username: '',
      password: '',
      usernameActive: false,
      passwordActive: false,
    }
  },
  methods: {
    login() {
      axios.post('/cabinChat/api/login', {
        username: this.username,
        password: this.password
      }).then(() => {
        this.$router.push('/')
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    register() {
      axios.post('/cabinChat/api/login', {
        username: this.username,
        password: this.password
      }).then(() => {
        this.$router.push('/')
      }).catch(() => {
        alert('Invalid login credentials')
      })
    }
  }
}
</script>
<template>
  <v-app>
    <v-main>
      <div class="background">
        <v-card class="login-app">
          <v-card-title class="form-label">Login</v-card-title>
          <v-divider></v-divider>
          <v-card-text>
            <v-form >
              <div class="form-text-wrapper">
                <a class="form-label">username</a>
                <div class="form-text">
                  <v-text-field v-model="username"
                                :class="{ 'form-text-active': usernameActive }"
                                @mouseover="usernameActive = true"
                                @mouseleave="usernameActive = false"/>
                </div>
              </div>
              <div  class="form-text-wrapper">
                <a class="form-label">password</a>
                <div  class="form-text">
                  <v-text-field v-model="password"
                                type="password"
                                :class="{ 'form-text-active': passwordActive }"
                                @mouseover="passwordActive = true"
                                @mouseleave="passwordActive = false"/>
                </div>
              </div>
            </v-form>
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-btn color="white" @click="login()" class="login-btn">Login</v-btn>
          </v-card-actions>
          <v-card-actions>
            <v-btn color="white" @click="register()" class="login-btn">Register</v-btn>
          </v-card-actions>
          <div class="d-flex justify-center align-center" style=" width:30px;margin: 20px auto;">
            <v-card-text>
              <v-btn  @click="login()"  class="login-btn">
                <v-icon left src="../../assets/img/google.png" width="30">
                  mdi-google
                </v-icon>
              </v-btn>
            </v-card-text>
            <v-card-text >
              <v-btn  @click="login()" class="login-btn">
                <v-icon left src="../../assets/img/github.png" width="30">
                  mdi-github
                </v-icon>
              </v-btn>
            </v-card-text>
            <v-card-text>
              <v-btn @click="login()" class="login-btn">
                <!-- Ant Design 官方图标库：https://www.iconfont.cn/collections/detail?cid=9402 -->
                <img src="../../assets/img/dingtalk.png" alt="钉钉扫码登录" width="30">
              </v-btn>
            </v-card-text>
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
.login-btn {
  max-width: 98px;
  max-height: 44px;
  margin: 0 auto;
  background: linear-gradient(rgb(74, 164, 231), rgb(74, 70, 204)); /* 标准的语法 */
}
.background {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 20px;
  background: linear-gradient(rgb(131,12,231), rgb(16,47,154)); /* 标准的语法 */
}
.form-label {
  color: white;
}
.form-text-wrapper{
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
}
</style>