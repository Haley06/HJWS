<template>
  <div class="review">
    <div class="top">
      <div class="Navleft">
        <el-avatar :size="30" :src="item.avatarUrl"> </el-avatar>
        <span class="name">{{ item.nickname }}</span>
      </div>
      <div class="Navright">
        {{ item.commentTime ? item.commentTime.substring(5, 16) : "" }}
      </div>
    </div>
    <div class="comments-wrap">
      <div class="comments">
        <div class="del-text" v-if="item.commentBody == '此评论已被删除'">
          {{ item.commentBody }}
        </div>
        <div class="text" v-else>{{ item.commentBody }}</div>
        <div class="relative" v-if="!(item.commentBody == '此评论已被删除')">
          <van-icon
            name="good-job-o"
            size="25"
            @click="comfavor(1, item.commentId, index)"
            v-if="!item.isFavorite"
          />
          <van-icon
            size="25"
            name="good-job"
            @click="comfavor(0, item.commentId, index)"
            v-else
          />
          <span class="count">{{ item.favoriteNumber }}</span>
        </div>
      </div>
      <div class="reply-wraper">
        <div class="sec-title">全部回复</div>
        <div class="secondcom" v-if="item.list">
          <review-reply
            v-for="(item, index) in item.list"
            :item="item"
            :key="index"
            :index="index"
            @flush="flush"
          >
          </review-reply>
        </div>
        <div class="nosecondcom" v-else>暂 无 回 复 ~</div>
      </div>
      <div class="reply">
        <el-input v-model="value" placeholder="请输入回复内容" class="input">
        </el-input>
        <van-button
          @click="sendReply(item.commentId, index)"
          style="padding: 0 10px; font-size: 14px"
          plain
          hairline
          type="primary"
          size="mini"
          >发送</van-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import formatDate from "../utils/utilsFunc";
import reviewReply from "./reviewReply.vue";
export default {
  props: {
    item: {},
    index: 0,
    postid: 0,
  },
  data: () => ({
    value: "",
  }),
  methods: {
    async comfavor(e, id, index) {
      e == 1
        ? await this.$emit("sendLike", "add", e, id, index)
        : await this.$emit("sendLike", "delete", e, id, index);
    },
    async updateComments(e) {
      if (e == 1) {
        this.item.favoriteNumber++;
        this.item.isFavorite = 1;
      } else {
        this.item.favoriteNumber--;
        this.item.isFavorite = 0;
      }
    },
    formatDate,
    async sendReply(commentId) {
      var time = new Date();
      time = this.formatDate(time, "yyyy-MM-dd hh:mm:ss");
      let { code } = await this.$axios.post("/api1/comment/add", {
        postId: this.postid,
        commentBody: this.value,
        commentTime: time,
        commentId: commentId,
      });
      if (code == 0) {
        this.$emit("getComments");
        this.value = "";
      }
    },
    flush() {
      this.$emit("getComments");
    },
  },
  components: { reviewReply },
};
</script>
<style lang="less" scoped>
.review {
  border-bottom: 2px solid #f3f4f4;
}
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
  text-align: center;
  line-height: 34px;
  padding-right: 15px;
  font-size: 16px;
  color: #919498;
}
.comments-wrap {
  .comments {
    display: flex;
    align-items: center;
    .text,
    .del-text {
      margin-left: 50px;
      padding-bottom: 5px;
      flex: 5;
    }
    .del-text {
      color: #aeaeae;
      text-decoration: line-through;
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
  .reply-wraper {
    padding: 10px;
    margin: 10px 40px 10px;
    width: 80%;
    background-color: #fafbfe;
    border-radius: 10px;
    .sec-title {
      font-weight: 700;
      font-size: 16px;
    }
    .nosecondcom {
      text-align: center;
      padding-bottom: 10px;
    }
  }
  .reply {
    display: flex;
    box-sizing: border-box;
    width: 100%;
    align-items: center;
    background-color: #fff;
    margin-bottom: 10px;
    .input {
      width: 50%;
      margin: 5px 15px 5px 40px;
    }
    /deep/.el-input__inner {
      height: 27px !important;
    }
  }
}
</style>