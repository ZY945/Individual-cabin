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

const app = createApp(App);
app.use(router);
app.use(hljsVuePlugin)
app.use(hljs)
app.use(vuetify);
app.config.globalProperties.$axios = axios
// 全局注册组件
app.component("ChatLogin", loginHome)
app.component("RegisterAccount", registerAccount)
app.component("BindAccount", bindAccount)
app.component("CabinMenus", cabinMenus)
app.component("monitorLinux", Linux)
app.mount('#app');


