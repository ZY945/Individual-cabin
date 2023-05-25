<script>
export default {
  name: "cabinMenus",
  props: {msg: String},
  components: {},
  data() {
    return {
      user: {
        avatar: "https://randomuser.me/api/portraits/women/85.jpg",
        userName: "Sandra Adams",
        email: "sandra_a88@gmailcom"
      },
      //TODO 路由数据后端获取
      menusList:[
        {
          title: "home",
          "prepend-icon": "mdi-account-multiple",
          path: "/",
        },
        {
          title: "hello",
          "prepend-icon": "mdi-star",
          path: "/hello",
        },
        {
          title: "test",
          "prepend-icon": "mdi-star",
          path: "/test",
        }],
    }
  },
  methods: {},
}
</script>
<template>
  <div>
    { {msg} }
    <v-card>
      <v-layout>
        <v-navigation-drawer
            expand-on-hover
            rail
        >
          <v-list>
            <v-list-item
                :prepend-avatar= 'user.avatar'
                :title= 'user.userName'
                :subtitle= 'user.email'
            ></v-list-item>
          </v-list>

          <v-divider></v-divider>

          <!-- 路由 -->
          <v-list density="compact" v-for="menus in menusList" :key="menus.id">
            <v-list-item :prepend-icon='menus["prepend-icon"]' :title='menus.title' :to="menus.path"></v-list-item>
          </v-list>
        </v-navigation-drawer>

        <div>
          <router-view/>
        </div>
      </v-layout>
    </v-card>
  </div>
</template>

<style scoped>

</style>