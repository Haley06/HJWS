const userModule = {
  namespaced: true,
  state: () => ({
    userInfo: {},
  }),
  mutations: {
    userInfoChange(state, payload) {
      state.userInfo = payload;
    },
  },
  actions: {},
  getters: {},
};

export default userModule;
