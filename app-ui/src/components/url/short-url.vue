<template>
  <v-app>
    <v-main>
      <v-container>
        <v-card>
          <v-card-title>请输入URL</v-card-title>
          <v-card-text>
            <v-text-field v-model="url" label="URL"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="getShortUrl()">生成短链接</v-btn>
          </v-card-actions>
        </v-card>
        <v-card>
          <v-card-title>短链接跳转页面</v-card-title>
          <v-card-subtitle class="mb-4">
            以下是您要访问的短链接：
          </v-card-subtitle>
          <v-card-text>
            <v-link target="_blank">{{ shortUrl }}</v-link>
          </v-card-text>
        </v-card>
        <v-dialog v-model="dialog" persistent="">
          <v-card>
            <v-card-title>请输入URL</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" @click="goBack()">返回</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="limit" persistent="">
          <v-card>
            <v-card-title>十秒内限制请求一次</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" @click="goBack()">返回</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  name: "shortUrl",
  data() {
    return {
      url: '',
      shortUrl: '',
      dialog: false,
      limit: false
    }
  },
  methods: {
    getShortUrl() {
      if (this.url) {
        axios.get('/cabin/util/shortUrl', {
          params: {
            url: this.url,
          }
        })
            .then(response => {
              this.shortUrl = response.data.data
              //TODO 10s内再次请求,后端会返回指定的msg或code,然后把limit设置为true
            })
            .catch(error => {
              console.log(error)
            })
      } else {
        this.dialog = true
      }
    },
    goBack() {
      this.dialog = false
      this.limit=false
    }
  }
}
</script>

<style scoped>

</style>
