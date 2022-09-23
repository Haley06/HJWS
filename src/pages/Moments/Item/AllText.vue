<template>
  <div class="allText">
    <Nav><b>我的文章 </b> </Nav>
    <div class="middle">
      <div class="MiddleLeft">
        <el-avatar icon="el-icon-user-solid" :src="avatarUrl" :size="55">
        </el-avatar>
        <span
          ><b class="name">{{ username }}</b></span
        >
      </div>
      <div class="MiddleRight" @click="goChatList()">
        <img src="../../../static/车友圈-好友聊天.svg" alt="arrow" />
      </div>
    </div>
    <ul v-for="(item, i) in texts" :key="i" class="main">
      <li @click="more(item)">
        <Moment
          :text="item.description"
          :time="item.createTime.substring(5, 16)"
          :name="item.username"
          :avatarUrl="item.avatarUrl"
        ></Moment>
        <div class="delete" @click.stop="deleteCon(item.id, i)">
          <van-icon name="delete" size="23" color="red" />
        </div>
        <van-dialog id="van-dialog" />
      </li>
    </ul>
  </div>
</template>
<script>
import Moment from "../../../components/Moment.vue";
import Nav from "../../../components/Nav.vue";
export default {
  data() {
    return {
      texts: [],
      pageNum: 1,
      totalPage: 0,
      avatarUrl: "",
      username: "",
    };
  },
  mounted() {
    this.getAll();
  },
  methods: {
    getAll() {
      this.$axios
        .get("/api1/text/byUser", {
          params: {
            pageNum: this.pageNum,
            pageSize: 100,
          },
        })
        .then((res) => {
          console.log(res);
          if (res.msg === "success") {
            this.texts.push(...res.byUser);
            this.totalPage = res.totalPage;
            this.avatarUrl = this.texts[0].avatarUrl;
            this.username = this.texts[0].username;
          }
        });
    },
    goPublish() {
      this.$router.push("/Publish");
    },
    goChatList() {
      this.$router.push("/chatList");
    },
    async deleteCon(id, index) {
      vant.Dialog.confirm({
        message: "确定要删除发表的文章吗",
      }).then(async () => {
        // on confirm
        let { code } = await this.$axios.get(
          this.$url.deleteContent.path + "?id=" + id
        );
        if (code == "0") {
          this.texts.splice(index, 1);
          vant.Toast({
            message: "删除成功",
          });
        }
      });
    },
    load() {
      if (this.pageNum >= this.totalPage) return;
      else {
        this.pageNum++;
        setTimeout(this.getAll(), 2000);
      }
    },
    more(item) {
      this.$router.push({
        path: "/Detail",
        query: {
          item: item,
        },
      });
    },
    goBack() {
      this.$router.go(-1);
    },
  },
  components: { Moment, Nav },
};
</script>
<style lang="less" scoped>
.allText {
  overflow: scroll;
  height: 100%;
  background-color: #f3f4f4;
  padding-bottom: 30px;
}
.middle {
  box-sizing: border-box;
  width: 90%;
  display: flex;
  justify-content: space-between;
  padding: 0 10px;
  .name {
    margin-left: 20px;
  }
}
.MiddleLeft {
  height: 80px;
  font-size: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.MiddleRight {
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  img {
    width: 45px;
    height: 45px;
  }
}
.main {
  margin-top: 10px;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.delete {
  float: right;
  margin-top: -30px;
  margin-right: 10px;
  padding: 0 0 10px 20px;
  width: 30px;
}
</style>
