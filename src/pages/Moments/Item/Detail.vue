<template>
  <div class="detail">
    <Nav><b>文章详情</b></Nav>
    <div class="main">
      <div class="textAvatar">
        <div class="left">
          <van-image round width="45" height="45" :src="result.avatarUrl" />
          <div class="name">{{ result.username }}</div>
        </div>
        <div class="time">{{ turntime }}</div>
      </div>
      <div class="message">
        {{ result.description }}
      </div>
      <div class="relative">
        <van-icon
          name="good-job-o"
          size="25"
          @click="favor(1)"
          v-if="!result.isFavorite"
        />
        <van-icon size="25" name="good-job" @click="favor(0)" v-else />
        <span class="count">{{ result.favoriteNumber }}</span>
        <van-icon name="share" size="25" @click="popShare" />
      </div>
    </div>
    <div class="all-talk" v-if="comments.length">
      <div class="title">全部评论</div>
      <div v-for="(item, index) in comments" :key="index">
        <review
          :key="item.commentId"
          :item="item"
          @sendLike="sendLike"
          ref="item"
          :index="index"
          :postid="item.recordId"
          @getComments="getComments"
        ></review>
      </div>
    </div>
    <div class="no-talk" v-else>
      <div class="title">全部评论</div>
      <div class="no-comment">暂 无 评 论 ~</div>
    </div>
    <div class="bottom">
      <el-input v-model="input" placeholder="请输入内容" class="input">
      </el-input>
      <van-button
        type="primary"
        style="font-size: 16px; height: 37px; border-radius: 5px"
        size="small"
        @click="sendMessage"
        >发送</van-button
      >
    </div>
    <van-popup v-model="show" @close="close" position="bottom">
      <div class="share-wrap">
        <div class="title">
          <van-icon class="icon" size="20" name="share"></van-icon>
          <div style="padding-left: 10rpx">分享</div>
        </div>
        <div class="share-list">
          <div>
            <van-image
              :src="require('../../../static/svg/wx.svg')"
              width="40"
              height="40"
            ></van-image>
            <span>微信</span>
          </div>
          <div>
            <van-image
              :src="require('../../../static/svg/QQ.svg')"
              width="40"
              height="40"
            ></van-image>
            <span>QQ</span>
          </div>
          <div>
            <van-image
              :src="require('../../../static/svg/wb.svg')"
              width="40"
              height="40"
            ></van-image>
            <span>微博</span>
          </div>
        </div>
      </div>
    </van-popup>
  </div>
</template>
<script>
import Moment from "../../../components/Moment.vue";
import Nav from "../../../components/Nav.vue";
import review from "../../../components/review.vue";
import formatDate from "../../../utils/utilsFunc";
export default {
  data() {
    return {
      input: "",
      result: [],
      textFavor: true,
      comments: [],
      commentFavor: true,
      turntime: "",
      show: false,
    };
  },
  components: { Moment, Nav, review },
  created() {},
  mounted() {
    this.prepareData();
  },
  methods: {
    popShare() {
      this.show = true;
    },
    close() {
      this.show = false;
    },
    turnTime(time) {
      let timeday = time.substring(0, 5);
      timeday =
        timeday
          .split("")
          .filter((item) => item != "0")
          .join("")
          .replace("-", "月") +
        "日" +
        time.substring(5);

      return timeday;
    },
    getParams() {},
    async prepareData() {
      this.result = this.$route.query.item;
      this.turntime = this.turnTime(this.result.createTime.substring(5, 16));
      let { results } = await this.$axios.post("/api1/comment/record", {
        recordId: this.result.id,
      });
      this.comments = results;
      // let data = await this.$axios.get(`/text/info?id=${this.result.id}`);
      // console.log(data);
    },
    async getComments() {
      let { results } = await this.$axios.post(`/api1/comment/record`, {
        recordId: this.result.id,
      });
      this.comments = results;
    },
    async changeLike(e, type, index) {
      if (e == 1) {
        if (type == "1") {
          this.result.favoriteNumber++;
          this.result.isFavorite = 1;
        } else {
          await this.$refs.item[index].updateComments(e);
        }
      } else {
        if (type == "1") {
          this.result.favoriteNumber--;
          this.result.isFavorite = 0;
        } else {
          await this.$refs.item[index].updateComments(e);
        }
      }
    },
    async sendLike(state, e, id, index, type = "0") {
      let typeId = type == "0" ? 1 : 0;
      let { code } = await this.$axios.post(`/api1/favorite/${state}`, {
        typeId: typeId,
        remark: id,
      });
      if (typeId == 0) {
        // 内容处理
        code == "0" ? this.changeLike(e, type) : "";
      } else {
        // 评论处理
        code == "0" ? this.changeLike(e, type, index) : "";
      }
    },
    async favor(e) {
      e == 1
        ? await this.sendLike("add", e, this.result.id, 0, "1")
        : await this.sendLike("delete", e, this.result.id, 0, "1");
    },
    commentfavor(id, e) {
      if (e === 1) {
        this.$axios
          .post("/api1/favorite/add", {
            typeId: 1,
            remark: id,
          })
          .then((res) => {
            if (res.msg === "success") {
              this.getComments();
            }
          });
        this.commentFavor = false;
      } else {
        this.$axios
          .post("/api1/favorite/delete", {
            typeId: 1,
            remark: id,
          })
          .then((res) => {
            if (res.msg === "success") {
              this.getComments();
            }
          });
        this.commentFavor = true;
      }
    },
    async sendMessage() {
      var time = new Date();
      time = this.formatDate(time, "yyyy-MM-dd hh:mm:ss");
      let { code } = await this.$axios.post("/api1/comment/add", {
        postId: this.result.id,
        commentBody: this.input,
        commentTime: time,
      });
      code == "0" ? [(this.input = ""), this.getComments()] : "";
    },
    formatDate,
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>
<style lang="less" scoped>
.detail {
  width: 100%;
  height: 100%;
  background-color: #f3f4f4;
  overflow: auto;
  .main {
    margin-top: 10px;
    background-color: #ffffff;
    padding-bottom: 15px;
    .textAvatar {
      box-sizing: border-box;
      width: 100%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px;
      .left {
        display: flex;
        align-items: center;
        margin-left: 10px;
        .name {
          margin-left: 10px;
        }
      }
      .time {
        margin-right: 20px;
      }
    }
    .message {
      box-sizing: border-box;
      background-color: #ffffff;
      width: 100%;
      word-wrap: break-word;
      line-height: 30px;
      padding: 0 20px 10px;
      text-indent: 30px;
    }
    .relative {
      display: flex;
      align-items: center;
      margin-left: 250px;
      .van-icon:last-child {
        margin-left: 30px;
      }
      .count {
        margin-left: 5px;
      }
    }
  }
  .bottom {
    display: flex;
    position: absolute;
    bottom: 0;
    box-sizing: border-box;
    width: 100%;
    align-items: center;
    background-color: #fff;
    .input {
      width: 77%;
      margin: 5px 15px 5px 5px;
    }
  }
  .all-talk,
  .no-talk {
    background-color: #fff;
    margin-top: 20px;
    padding-bottom: 70px;
  }

  .title {
    padding: 10px 0 0 10px;
    font-size: 18px;
    font-weight: 700;
  }
  .no-comment {
    text-align: center;
  }
  .share-wrap {
    height: 125px;
    width: 100%;
    display: flex;
    flex-direction: column;
    .van-icon {
      padding-top: 13px;
    }
    .title {
      display: flex;
      align-items: center;
      font-size: 18px;
      text {
        display: block;
        padding: 10px 0 0 5px;
      }
    }
    .share-list {
      display: flex;
      justify-content: space-evenly;
      div {
        margin: 0 10px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        align-items: center;
      }
    }
  }
}
</style>
