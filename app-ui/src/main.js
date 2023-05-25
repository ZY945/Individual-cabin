import {createApp} from 'vue'
import App from "@/App.vue";
import router from "@/router/index";
import vuetify from "@/types/vuetify";


const app = createApp(App);
app.use(router);
app.use(vuetify);
app.mount('#app');


