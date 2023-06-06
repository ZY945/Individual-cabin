import {createApp} from 'vue'
import App from "@/App.vue";
import router from "@/router/index";
import vuetify from "@/types/vuetify";

import '@mdi/font/css/materialdesignicons.css'
import 'highlight.js/styles/atom-one-dark.css'
import 'highlight.js/lib/common'
import hljsVuePlugin from '@highlightjs/vue-plugin'
import axios from "axios";

const app = createApp(App);

app.use(router);
app.use(hljsVuePlugin)
app.use(vuetify);
app.config.globalProperties.$axios = axios
app.mount('#app');


