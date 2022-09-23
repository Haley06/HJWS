<template>
  <div class="chatList">
    <Nav><b>聊天消息</b></Nav>
    <div class="list">
      <div v-for="(item, i) in lists" :key="i">
        <div @click="more(item.chatUserId, item)">
          <messageList :item="item"></messageList>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import messageList from "../../components/messageList.vue";
import Nav from "../../components/Nav.vue";

export default {
  data() {
    return {
      lists: [],
    };
  },
  mounted() {
    this.getAll();
  },
  methods: {
    async getAll() {
      let { code, results } = await this.$axios.get("/api1/message/form");
      if (code == 0) {
        this.lists = results;
      }
    },
    more(id, item) {
      this.$router.push({
        path: "/chat",
        query: {
          Id: id,
          item: item,
        },
      });
    },
    goBack() {
      this.$router.go(-1);
    },
  },
  components: { messageList, Nav },
};
</script>
<style lang="less" scoped>
.chatList {
  width: 100vw;
  height: 100%;
  background-color: #f3f4f4;
}
.list {
  margin-top: 5px;
}
</style>
