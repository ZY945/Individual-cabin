<template>
  <v-app>
    <v-container fluid>
      <v-row>
        <v-col cols="12" md="8" offset-md="2">
          <!-- 聊天室标题 -->
          <v-card class="mb-3">
            <v-card-title primary-title>
              <div>
                <h3 class="headline mb-0">Chat Room</h3>
              </div>
              <div class="Mail_login_loginBox_tab">
                <v-btn @click="logout()" color="primary">
                  logout
                </v-btn>
              </div>
            </v-card-title>
          </v-card>

          <!-- 聊天消息列表 -->
          <v-card class="mb-3">
            <v-list ref="chatList">
              <v-list-item v-for="message in messages" :key="message.id">
                <v-list-item-subtitle class="mb-1">
                  <strong>{{ message.sender }}:</strong> {{ message.content }}
                </v-list-item-subtitle>
                <v-list-item-subtitle>
                  {{ new Date(message.timestamp).toLocaleString() }}
                </v-list-item-subtitle>
              </v-list-item>
            </v-list>
          </v-card>

          <!-- 发送消息表单 -->
          <v-card class="mb-3">
            <v-form>
              <v-textarea
                  outlined
                  v-model="newMessage"
                  label="Enter your message here"
                  rows="5"
              ></v-textarea>
              <v-btn @click.prevent="sendMessage" color="primary" class="mr-4">
                Send
              </v-btn>
            </v-form>
          </v-card>

          <!-- 连接和断开按钮 -->
          <v-combobox
              label="本人"
              v-model="from"
              :items="fromList"
          ></v-combobox>
          <v-combobox
              label="好友"
              v-model="to"
              :items="toList"
          ></v-combobox>
          <v-card class="mb-3">
            <v-btn v-if="!connected" @click.prevent="connect" color="primary">
              Connect
            </v-btn>
            <v-btn v-else @click.prevent="disconnect" color="error">
              Disconnect
            </v-btn>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>
<style>
.Mail_login_loginBox_tab {
  display: inline-block;
  max-width: 130px;
  max-height: 245px;
  font-size: 30px;
  color: white;
}
</style>
<script>


import {useRouter} from "vue-router";
import {isLogin} from "@/assets/js/utils";
import axios from "axios";

export default {
  name: "ChatApp",
  data() {
    return {
      socket: null, // WebSocket 对象
      connected: false, // 是否已连接
      messages: [], // 聊天消息列表
      newMessage: "", // 待发送的消息
      fromList: ["Alice",
        "Bob"],// 发送人，需要从登录信息中获取
      toList: ["Alice",
        "Bob"], // 接收人，需要从路由参数中获取
      from: "",
      to: ""
    };
  },
  mounted() {
    const router = useRouter();
    if (router.currentRoute.value.meta.requiresAuth && !isLogin()) {
      // 如果当前路由需要登录但用户未登录，则跳转到登录页面
      router.push("/login");
    } else {
      // 连接 WebSocket
      this.connect(); // 组件挂载后自动连接 WebSocket
    }
  },
  beforeUnmount() {
    this.disconnect(); // 组件销毁前断开 WebSocket 连接
  },
  methods: {
    logout() {
      const userToken = JSON.parse(window.localStorage.getItem("token"));
      axios.post('/oauth2/user/logout', null, {
        params: {
          token: userToken,
        }
      }).then(response => {
        if (response.data.code === 200) {
          window.localStorage.removeItem("token");

          this.$router.push('/login')
        }
      }).catch(() => {
        alert('Invalid login credentials')
      })
    },
    // 连接 WebSocket
    connect() {
      this.socket = new WebSocket(
          `ws://localhost:8081/api/websocket/${this.from}/${this.to}`
      );
      this.socket.onopen = () => {
        this.connected = true;
      };
      this.socket.onmessage = this.handleMessage;
    },

    // 断开 WebSocket
    disconnect() {
      if (this.socket) {
        this.socket.close();
        this.socket = null;
        this.connected = false;
      }
    },

    // 发送消息
    sendMessage() {
      if (!this.newMessage) {
        return;
      }
      const message = {
        content: this.newMessage.trim(),
        sender: this.from,
        timestamp: new Date().getTime(),
      };
      try {
        this.socket.send(JSON.stringify(message)); // 发送消息
        this.newMessage = ""; // 清空输入框
      } catch (e) {
        // 处理发送失败的情况
        console.error(e);
      }
    },

    // 处理接收到的消息
    handleMessage(event) {
      const message = JSON.parse(event.data);
      this.messages.push(message); // 添加新消息
      this.$nextTick(() => {
        this.$refs.chatList.$el.scrollTop =
            this.$refs.chatList.$el.scrollHeight; // 滚动到底部
      });
    },
  },
};
</script>