<template>
  <div class="Publish">
    <Nav><b>发表文章</b></Nav>
    <el-input
      type="textarea"
      :rows="12"
      placeholder="请输入要发表的内容"
      v-model="textarea"
      class="input"
    >
    </el-input>
    <el-button type="primary" @click="publish">发表</el-button>
  </div>
</template>
<script>
import Nav from "../../../components/Nav.vue";
export default {
  data() {
    return {
      textarea: "",
    };
  },
  components: { Nav },
  methods: {
    publish() {
      this.$axios
        .post(this.$url.Release.path, {
          content: this.textarea,
        })
        .then((res) => {
          if (res.msg === "success") {
            this.textarea = "";
            this.$router.replace("/Moments");
          }
        });
    },
  },
};
</script>
<style lang="less" scoped>
.Publish {
  height: 100vh;
  // width: 100vw;
  background-color: #f3f4f4;
}
.el-button {
  border-radius: 10px;
  background-color: #ad9ac6;
  margin-top: 20px;
  position: absolute;
  left: 75%;
  border: 3px solid #eaf2ff !important;
  letter-spacing: 3px;
  text-indent: 3px;
  font-size: 16px;
}
.input {
  box-sizing: border-box;
  width: 100%;
  display: block;
  margin: 0 auto;
}
/deep/.el-textarea {
  font-size: 17px;
}
</style>