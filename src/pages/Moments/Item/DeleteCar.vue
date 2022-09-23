<template>
  <div class="comment">
    <Nav><b>我的评论</b></Nav>

    <div class="no-comment" v-if="!haveCom">暂无评论</div>
    <div class="comment-wraper" v-else>
      <div class="item" v-for="(item, index) in myComments" :key="index">
        <div class="text">{{ item.commentBody }}</div>
        <div class="content">
          {{ item.describe }}
        </div>
        <div class="delete" @click.stop="deleteCom(item.commentId)">
          <van-icon name="delete" size="23" color="red" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Nav from "../../../components/Nav.vue";
export default {
  data: () => ({
    myComments: [],
    haveCom: true,
  }),
  methods: {
    async getMyCommentes() {
      let { results } = await this.$axios("/api1/comment/user");
      this.myComments = results;
      if (this.myComments.length == 0) {
        this.haveCom = false;
      }
    },
    async deleteCom(id) {
      let { code } = await this.$axios.post(this.$url.DeleteComment.path, {
        commentId: id,
      });
      if (code == 0) {
        this.getMyCommentes();
        vant.Toast({
          message: "删除成功",
        });
      }
    },
  },
  components: {
    Nav,
  },
  mounted() {
    this.getMyCommentes();
  },
};
</script>

<style lang="less" scoped>
.comment {
  overflow: scroll;
  height: 100%;
  background-color: #f3f4f4;
  padding-bottom: 30px;
}
.no-comment {
  position: absolute;
  top: 50%;
  left: 50%;
  font-size: 24px;
  letter-spacing: 5px;
  color: #c1c1c1;
  transform: translate(-50%);
}
.item {
  background-color: #fff;
  margin: 10px 15px;
  padding: 15px 20px 10px 20px;
  border-radius: 10px;
  .text {
    display: block;
    color: #80699e;
    margin-bottom: 10px;
    font-weight: 700;
  }
  .content {
    background-color: #fafafa;
    padding: 6px 10px;
    border-radius: 10px;
    font-size: 14px;
    box-sizing: border-box;
    line-height: 25px;
    color: #000000c3;
    text-overflow: ellipsis;
    overflow: hidden;
    display: -webkit-box;
    word-break: break-all;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
  }
  .delete {
    margin-left: 270px;
    margin-top: 5px;
  }
}
</style>