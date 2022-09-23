<template>
  <div class="profile-item">
    <Nav>{{ this.tag }}</Nav>
    <div class="router">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Nav from "../../../components/Nav";
export default {
  name: "index",
  components: {
    Nav,
  },
  data() {
    return {
      tag: "",
      path2tag: {
        info: "资料修改",
        emergency: "紧急联系人",
        aboutUs: "关于我们",
        feedback: "问题反馈",
        useGuidance: "使用引导",
      },
    };
  },
  methods: {},
  mounted() {
    this.tag = this.path2tag[this.$route.path.slice(10)];
  },
};
</script>

<style scoped>
.router {
  width: 100%;
  height: calc(100vh - 60px);
}
</style>
