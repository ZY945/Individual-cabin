import {createApp} from 'vue'
import App from "@/App.vue";
import router from "@/router/index";
import vuetify from "@/types/vuetify";
//彩色class
import hljsVuePlugin from '@highlightjs/vue-plugin'
import axios from "axios";
import hljs from "highlight.js";
import loginHome from "@/components/oauth2/login-home.vue";
import registerAccount from "@/components/oauth2/register-account.vue";
import bindAccount from "@/components/oauth2/bind-account.vue";
import cabinMenus from "@/components/cabin-menus.vue";
import Linux from "@/components/monitor/monitor-linux.vue";
import uploadFile from "@/components/util/upload-file.vue";
import vuetifyTable from "@/types/vuetifyTable";

const app = createApp(App);
app.use(router);
app.use(hljsVuePlugin)
app.use(hljs)
// 引入vuetify
app.use(vuetify);
//TODO 引入vuetifyTable--因为components会冲突,所以暂时分开引入
// https://vuetifyjs.com/en/components/data-tables/basics/#v-data-table
app.use(vuetifyTable);
app.config.globalProperties.$axios = axios
// 全局注册组件
app.component("ChatLogin", loginHome)
app.component("RegisterAccount", registerAccount)
app.component("BindAccount", bindAccount)
app.component("CabinMenus", cabinMenus)
app.component("monitorLinux", Linux)
app.component("uploadFile", uploadFile)
app.mount('#app');


