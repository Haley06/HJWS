import Vue from "vue";
import App from "./App.vue";
import router from "./router/index";
import store from "./store/index";
import api from "./api/api";
import url from "./api/url";
import init from "./config/echarts";
import dsbrige from "dsbridge";
// 全局挂载消息通知
import "./utils/message";
Vue.config.productionTip = false;
// 挂载api， axios
Vue.prototype.$axios = api;
Vue.prototype.$url = url;
Vue.prototype.$store = store;
Vue.prototype.$dsbrige = dsbrige;
Vue.prototype.$init = init;
new Vue({
  render: (h) => h(App),
  router,
  store,
}).$mount("#app");
