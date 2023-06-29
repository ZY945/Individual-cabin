<script>

import axios from "axios";
import {onMounted, ref} from "vue";

export default {
  setup() {
    let msg = ref('');
    let drawer = ref(true);
    let rail = ref(true);
    let user = ref({
      avatar: "https://cdn.vuetifyjs.com/images/john.jpg",
      userName: "Sandra Adams",
      email: "sandra_a88@gmailcom"
    });
    let menusList = ref([]);
    // const getMenusList = async () => {
    //   axios.get('/cabin/routing/all', {
    //     params: {}
    //   })
    //       .then(response => {
    //         if (response.data.code === 200) {
    //           this.menusList = response.data.data
    //         }
    //       })
    //       .catch(error => {
    //         console.log(error)
    //       })
    // };

    // 异步获取后端数据
    async function fetchMenus() {
      try {
        axios.get('/cabin/routing/all', {
          params: {}
        })
            .then(response => {
              if (response.data.code === 200) {
                const data = response.data.data
                // 将获取的数据赋值给响应式数组
                // 将获取的数据赋值给响应式数组
                menusList.value = data.map(item => ({
                  id: item.id,
                  title: item.title,
                  path: item.path,
                  prependIcon: item['prepend-icon']
                }));
              }
            })
            .catch(error => {
              console.log(error)
            })

      } catch (error) {
        console.error(error);
      }
    }

    // 在组件初始化时调用 fetchMenus()
    onMounted(fetchMenus);
    return {
      msg,
      drawer,
      rail,
      user,
      menusList,
      // getMenusList,
    }
  }
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
        <v-list dense v-for="menu in menusList" :key="menu.id">
          <v-list-item :to="menu.path">
            <template v-slot:default="{ active }">
              <v-list-item-icon>
                <v-icon :color="active ? '#ffffff' : ''">{{ menu.prependIcon }}</v-icon>
                <v-list-item-content>
                  <v-list-item-title>{{ menu.title }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item-icon>
            </template>
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