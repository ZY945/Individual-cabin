<script>
import {ref} from 'vue'

export default {
  name: 'ChatApps',
  setup() {
    const username = ref('')
    const content = ref('')
    const messages = ref([
      {id: 1, username: 'Alice', content: 'Hello world!'},
      {id: 2, username: 'Bob', content: 'Hi there!'}
    ])

    function sendMessage() {
      if (content.value.trim() === '') {
        return
      }
      messages.value.push({
        id: messages.value.length + 1,
        username: username.value,
        content: content.value
      })
      content.value = ''
    }

    return {
      username,
      content,
      messages,
      sendMessage
    }
  }
}
</script>

<template>
  <div class="chat-app">
    <v-card>
      <v-toolbar color="primary" dark>
        <v-toolbar-title>Vue Chat</v-toolbar-title>
      </v-toolbar>
      <v-divider></v-divider>
      <v-card-text>
        <v-list>
          <v-list-item v-for="message in messages" :key="message.id">
            <v-list-item-avatar>
              <v-icon>mdi-account-circle</v-icon>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title>{{ message.username }}</v-list-item-title>
              <v-list-item-subtitle>{{ message.content }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-text-field v-model="username" label="Username"></v-text-field>
        <v-text-field v-model="content" label="Message" @keyup.enter="sendMessage"></v-text-field>
        <v-btn color="primary" @click="sendMessage">Send</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>


<style>
.chat-app {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}
</style>
