<template>
  <div class="replycon">
    <div class="top">
      <div class="Navleft">
        <el-avatar :size="30" :src="item.avatarUrl"> </el-avatar>
        <span class="name">{{ item.nickname }}</span>
      </div>
      <div class="Navright">
        {{ item.commentTime ? item.commentTime.substring(5, 16) : "" }}
      </div>
    </div>
    <div class="comments">
      <div class="text">{{ item.commentBody }}</div>
      <div class="relative">
        <van-icon
          name="good-job-o"
          size="25"
          @click="comReplyFavor(item.commentId)"
          v-if="!item.isFavorite"
        />
        <van-icon
          size="25"
          name="good-job"
          @click="comReplyDel(item.commentId)"
          v-else
        />
        <span class="count">{{ item.favoriteNumber }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: {},
  },
  methods: {
    async comReplyFavor(id) {
      let { code } = await this.$axios.post("/api1/favorite/add", {
        typeId: 1,
        remark: id,
      });
      if (code == 0) {
        this.item.isFavorite = 1;
        this.item.favoriteNumber++;
      }
    },
    async comReplyDel(id) {
      let { code } = await this.$axios.post("/api1/favorite/delete", {
        typeId: 1,
        remark: id,
      });
      if (code == 0) {
        this.item.isFavorite = 0;
        this.item.favoriteNumber--;
      }
    },
  },
};
</script>

<style lang="less" scoped>
.top {
  display: flex;
  justify-content: space-between;
  padding: 10px 10px 0 10px;
}
.Navleft {
  display: flex;
  .name {
    display: inline-block;
    line-height: 34px;
    margin-left: 10px;
  }
}
.Navright {
  line-height: 34px;
  font-size: 16px;
  color: #919498;
}
.comments {
  display: flex;
  align-items: center;
  .text {
    margin-left: 50px;
    padding-bottom: 5px;
    flex: 5;
  }

  .relative {
    flex: 1;
    display: flex;
    align-items: center;
    padding-right: 15px;

    .count {
      margin-left: 5px;
    }
  }
}
</style>