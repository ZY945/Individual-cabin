<script>

export default {
  name: "cabinMenus",
  props: {msg: String},
  components: {},
  data() {
    return {
      drawer: true,
      rail: true,
      user: {
        avatar: "https://cdn.vuetifyjs.com/images/john.jpg",
        userName: "Sandra Adams",
        email: "sandra_a88@gmailcom"
      },
      //TODO 路由数据后端获取
      menusList: [
        {
          title: "home",
          "prepend-icon": "mdi-home",
          path: "/home",
        },
        {
          title: "gitee",
          "prepend-icon": "mdi-star",
          path: "/gitee",
        },
        {
          title: "jenkins",
          "prepend-icon": "mdi-star",
          path: "/jenkins",
        },
        {
          title: "jsonFormat",
          "prepend-icon": "mdi-star",
          path: "/jsonFormat",
        },
        {
          title: "chatApps",
          "prepend-icon": "mdi-star",
          path: "/chatApps",
        },
        {
          title: "login",
          "prepend-icon": "mdi-star",
          path: "/login",
        },
        {
          title: "feishu",
          "prepend-icon": "mdi-star",
          path: "/feishu",
        },
        {
          title: "chatApp",
          "prepend-icon": "mdi-star",
          path: "/chatApp",
        },
        {
          title: "shortUrl",
          "prepend-icon": "mdi-star",
          path: "/shortUrl",
        },
      ],
    }
  },
  methods: {},
}
</script>
<template>
  <div>
    {{ msg }}
    <v-app>
      <v-navigation-drawer
          image="https://cdn.vuetifyjs.com/images/backgrounds/bg-2.jpg"
          theme="dark"
          v-model="drawer"
          :rail="rail"
          permanent
          @click="rail = false"
      >
        <!-- 侧边栏内容 -->
        <v-list-item
            :prepend-avatar='user.avatar'
            :title='user.userName'
            :subtitle='user.email'

        >
          <template v-slot:append>
            <v-btn
                variant="text"
                icon="mdi-menu"
                @click.stop="rail = !rail"
            >
            </v-btn>
          </template>
        </v-list-item>

        <!-- 分隔 -->
        <v-divider></v-divider>

        <!-- 路由 -->
        <v-list density="compact" v-for="menus in menusList" :key="menus.id">
          <v-list-item :prepend-icon='menus["prepend-icon"]' :title='menus.title' :to="menus.path">
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <!-- 主要内容       -->
      <v-main class="v-main">
        <router-view/>
      </v-main>
    </v-app>
  </div>
</template>

<style scoped>
</style>