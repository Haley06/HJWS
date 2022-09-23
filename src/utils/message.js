import Vue from "vue";
Vue.prototype.$Message = function(msg, status = "cross") {
  vant.Toast({
    message: msg,
    icon: status,
  });
};
