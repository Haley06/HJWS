<template>
  <div class="apply">
    <span>{{ content }}</span>
    <span>
      <span style="display: inline-block" v-if="state == 2">
        <el-button
          class="btnclick"
          size="mini"
          type="success"
          plain
          round
          @click="agree(0)"
          >同意</el-button
        >
        <el-button
          class="btnclick"
          size="mini"
          type="danger"
          plain
          round
          @click="agree(1)"
          >忽略</el-button
        >
      </span>
      <span v-else>
        <el-button
          class="hadclick"
          type="primary"
          size="mini"
          plain
          disabled
          v-if="state == 0"
          >已接受</el-button
        >
        <el-button
          class="hadclick"
          type="primary"
          size="mini"
          plain
          disabled
          v-else-if="state == 1"
          >已忽略</el-button
        >
      </span>
    </span>
  </div>
</template>
<script>
export default {
  name: "chatApply",
  props: ["content", "id", "monitoredId"],
  data() {
    return {
      info: "",
      state: 0,
    };
  },
  async mounted() {
    await this.judgeState();
  },
  methods: {
    async agree(e) {
      if (e == 0) {
        vant.Dialog.confirm({
          message: "是否将驾驶状态展示给好友?",
        })
          .then(async () => {
            // on confirm
            this.AllowMonitor(0, e);
          })
          .catch(() => {
            this.AllowMonitor(1, e);
          });
      } else {
        this.clickAccept(e);
      }
    },
    async AllowMonitor(val, e) {
      let { code, msg } = await this.$axios.post("/api1/monitored/isAllowed", {
        id: this.monitoredId,
        isAllowed: val,
      });
      if (code !== 0) {
        vant.Toast({
          message: msg,
          icon: "user-circle-o",
        });
      } else {
        this.clickAccept(e);
      }
    },
    async clickAccept(e) {
      let { code } = await this.$axios.post("/api1/monitored/isAccepted", {
        id: this.monitoredId,
        isAccepted: e,
      });
      // console.log(code, e);
      if (code == 0) {
        if (e == 0) {
          this.info = "已接受";
          this.state = 0;
        }
        if (e == 1) {
          this.info = "已忽略";
          this.state = 1;
        }
      }
    },
    async judgeState() {
      let { isAccepted } = await this.$axios.post(
        "/api1/monitored/userAccepted",
        {
          monitoredId: this.monitoredId,
        }
      );
      this.state = isAccepted;
    },
  },
};
</script>
<style scoped lang="less">
.apply {
  border-radius: 0 48px 48px 48px;
  background-color: #dff7f9;
  max-width: 50%;
  margin: 25px 0 0 7px;
  word-wrap: break-word;
  padding: 10px 15px;
  font-size: 14px;
  .btn {
    display: inline-block;
    margin-left: 20px;
  }
  .hadclick {
    background-color: #dff7f9;
    border: none;
    padding: 0 5px 0 5px;
    font-size: 12px;
    color: #668afb;
  }
  .btnclick {
    margin: 10px 0 0 15px;
    padding: 5px 15px;
    font-size: 12px;
  }
}
</style>
