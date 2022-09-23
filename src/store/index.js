import Vue from "vue";
import Vuex from "vuex";
import user from "./user.js";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    mobile: "",
    addFriendsList:[],
  },
  mutations: {
    setMobile(state, val) {
      state.mobile = val;
    },
    userAddFriends(state, payload) {
      state.addFriendsList.push(payload)
    },
  },
  actions: {},
  modules: {
    user: user,
  },
});
