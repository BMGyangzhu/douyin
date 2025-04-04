import './style.css'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './router';
import { createPinia } from "pinia";
import { createApp } from 'vue';

const douyinApp = createApp(App)
douyinApp.use(createPinia())
douyinApp.use(router)
douyinApp.use(vuetify)
douyinApp.mount('#app')