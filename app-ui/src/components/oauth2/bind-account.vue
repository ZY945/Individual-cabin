<script>
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

export default {
  setup() {
    let bindEmail = ref('');
    let bindCode = ref('');
    let bindUsername = ref('');
    let bindPassword = ref('');
    let feiShuId = ref('');
    let gitHubId = ref('');
    let giteeId = ref('');
    let bindEmailActive = ref(false);
    let bindCodeActive = ref(false);
    let bindUserNameActive = ref(false);
    let bindPassWordActive = ref(false);
    let bindLoginType = ref('account');
    let router = useRouter();
    const sendCode = () => {
      axios.get('/oauth2/login/email/sendCode', {
        params: {
          userEmail: bindEmail.value,
        }
      }).then(() => {
      }).catch(() => {
        alert('Invalid login credentials')
      })
    };
    const bindByAccount = () => {
      const feiShuId = JSON.parse(window.localStorage.getItem('feiShuId'));
      const gitHubId = JSON.parse(window.localStorage.getItem('gitHubId'));
      const giteeId = JSON.parse(window.localStorage.getItem('giteeId'));

      if (feiShuId !== null) {
        axios.post('/oauth2/bind/feishu/account', {
          userName: bindUsername.value,
          passWord: bindPassword.value,
          feiShuId: feiShuId,
        }).then(response => {
          const token = response.data.data.token
          if (response.data.code === 200) {
            window.localStorage.setItem('token', JSON.stringify(token))

            router.push('/chatApp')
          }
        }).catch(() => {
          alert('Invalid login credentials')
        })
        window.localStorage.removeItem("feiShuId");
      }
      if (gitHubId !== null) {
        axios.post('/oauth2/bind/github/account', {
          userName: bindUsername.value,
          passWord: bindPassword.value,
          gitHubId: gitHubId
        }).then(response => {
          const token = response.data.data.token
          if (response.data.code === 200) {
            window.localStorage.setItem('token', JSON.stringify(token))

            router.push('/chatApp')
          }
        }).catch(() => {
          alert('Invalid login credentials')
        })
        window.localStorage.removeItem("gitHubId");
      }
      if (giteeId !== null) {
        axios.post('/oauth2/bind/gitee/account', {
          userName: bindUsername.value,
          passWord: bindPassword.value,
          giteeId: giteeId
        }).then(response => {
          const token = response.data.data.token
          if (response.data.code === 200) {
            window.localStorage.setItem('token', JSON.stringify(token))

            router.push('/chatApp')
          }
        }).catch(() => {
          alert('Invalid login credentials')
        })
        window.localStorage.removeItem("giteeId");
      }
    };
    const bindByEmail = () => {
      const feiShuId = JSON.parse(window.localStorage.getItem('feiShuId'));
      const gitHubId = JSON.parse(window.localStorage.getItem('gitHubId'));
      const giteeId = JSON.parse(window.localStorage.getItem('giteeId'));
      if (feiShuId !== null) {
        axios.post('/oauth2/bind/feishu/email', {
          userEmail: bindEmail.value,
          code: bindCode.value,
          feiShuId: feiShuId,
        }).then(response => {
          const token = response.data.data.token
          if (response.data.code === 200) {
            window.localStorage.setItem('token', JSON.stringify(token))

            router.push('/chatApp')
          }
        }).catch(() => {
          alert('Invalid login credentials')
        })
        window.localStorage.removeItem("feiShuId");
      }
      if (gitHubId !== null) {
        axios.post('/oauth2/bind/github/email', {
          userEmail: bindEmail.value,
          code: bindCode.value,
          gitHubId: gitHubId
        }).then(response => {
          const token = response.data.data.token
          if (response.data.code === 200) {
            window.localStorage.setItem('token', JSON.stringify(token))

            router.push('/chatApp')
          }
        }).catch(() => {
          alert('Invalid login credentials')
        })
        window.localStorage.removeItem("gitHubId");
      }
      if (giteeId !== null) {
        axios.post('/oauth2/bind/gitee/email', {
          userEmail: bindEmail.value,
          code: bindCode.value,
          giteeId: giteeId
        }).then(response => {
          const token = response.data.data.token
          if (response.data.code === 200) {
            window.localStorage.setItem('token', JSON.stringify(token))

            router.push('/chatApp')
          }
        }).catch(() => {
          alert('Invalid login credentials')
        })
        window.localStorage.removeItem("giteeId");
      }
    };


    return {
      sendCode,
      bindByAccount,
      bindByEmail,
      bindEmail,
      bindCode,
      bindUsername,
      bindPassword,
      feiShuId,
      gitHubId,
      giteeId,
      bindEmailActive,
      bindCodeActive,
      bindUserNameActive,
      bindPassWordActive,
      bindLoginType,
    }
  }
}
</script>
<template>
  <v-app>
    <v-main>
      <div class="bindBackground">
        <v-card class="bind-app">
          <div class="form-table-name">
            <div class="Account_login_loginBox_tab">
              <button @click="bindLoginType='account'">Account</button>
            </div>
            <div class="Mail_login_loginBox_tab">
              <button @click="bindLoginType='bindEmail'">Email</button>
            </div>
          </div>
          <v-card-text class="form-account-app" v-if="bindLoginType === 'account'">
            <v-form>
              <div>
                <a class="form-label">Username</a>
                <div class="form-text">
                  <v-text-field v-model="bindUsername"
                                :class="{ 'form-text-active': bindUserNameActive }"
                                @mouseover="bindUserNameActive = true"
                                @mouseleave="bindUserNameActive = false"
                                class="form-text-wrapper"/>
                </div>
              </div>
              <div class="form-text-wrapper" style="margin-top: 10px">
                <a class="form-label">Password</a>
                <div class="form-text">
                  <v-text-field v-model="bindPassword"
                                type="password"
                                :class="{ 'form-text-active': bindPassWordActive }"
                                @mouseover="bindPassWordActive = true"
                                @mouseleave="bindPassWordActive = false"/>
                </div>
              </div>
            </v-form>
            <v-card-actions>
              <v-btn color="white" @click="bindByAccount()" class="login-btn">Bind</v-btn>
            </v-card-actions>
          </v-card-text>
          <v-card-text class="form-account-app" v-if="bindLoginType === 'bindEmail'">
            <v-form>
              <div>
                <a class="form-label">Email</a>
                <div class="form-text">
                  <v-text-field v-model="bindEmail"
                                :class="{ 'form-text-active': bindEmailActive }"
                                @mouseover="bindEmailActive = true"
                                @mouseleave="bindEmailActive = false"
                                class="form-text-wrapper"/>
                </div>
              </div>
              <div class="form-text-wrapper" style="margin-top: 10px">
                <a class="form-label">Code</a>
                <div class="form-text" style="display: flex; margin-top: 10px; align-items: center;">
                  <v-text-field v-model="bindCode"
                                type="bindPassword"
                                :class="{ 'form-text-active': bindCodeActive }"
                                @mouseover="bindCodeActive = true"
                                @mouseleave="bindCodeActive = false"
                  />
                  <v-btn color="white" style="margin-left: 10px;" @click="sendCode()">Send Code</v-btn>
                </div>
              </div>
            </v-form>
            <v-card-actions>
              <v-btn color="white" @click="bindByEmail()" class="login-btn">Bind</v-btn>
            </v-card-actions>
          </v-card-text>
        </v-card>
      </div>
    </v-main>
  </v-app>

</template>

<style>
.bind-app {
  justify-content: center;
  align-items: center;
  max-width: 300px;
  max-height: 488px;
  margin: 0 auto;
  padding: 20px;
  background-color: rgb(26, 26, 42);
}

.bindBackground {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 20px;
  background: linear-gradient(rgb(204, 204, 204), rgb(16, 47, 154)); /* 标准的语法 */
}


.form-account-app {
  max-width: 260px;
  max-height: 300px;
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