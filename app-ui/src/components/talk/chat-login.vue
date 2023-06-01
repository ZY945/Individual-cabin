<script>
import axios from 'axios'

export default {
  name: "ChatLogin",
  data() {
    return {
      username: '',
      password: ''
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
    }
  }
}
</script>
<template>
  <v-card>
    <v-card-title>Login</v-card-title>
    <v-divider></v-divider>
    <v-card-text>
      <v-form>
        <v-text-field v-model="username" label="Username"></v-text-field>
        <v-text-field v-model="password" label="Password" type="password"></v-text-field>
      </v-form>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn color="primary" @click="login()">Login</v-btn>
    </v-card-actions>
  </v-card>
</template>


