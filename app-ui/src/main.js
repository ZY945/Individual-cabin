import {createApp} from 'vue'
import App from "@/App.vue";
import router from "@/router/index";
import vuetify from "@/types/vuetify";

//彩色class
import hljsVuePlugin from '@highlightjs/vue-plugin'
import axios from "axios";
import hljs from "highlight.js";

const app = createApp(App);
app.use(router);
app.use(hljsVuePlugin)
app.use(hljs)
app.use(vuetify);
app.config.globalProperties.$axios = axios
app.mount('#app');


