<template>
  <div class="Moments">
    <Nav
      ><b>车友圈 </b>
      <van-icon
        name="manager-o"
        size="18"
        color="#b8b9b9"
        @click="goOtherPage('/Alltext')"
        slot="before"
        class="before-icon"
      />
      <van-icon
        name="chat-o"
        size="22"
        color="#b8b9b9"
        @click="goOtherPage('/deleteCar')"
        slot="after"
        class="after-icon"
      />
      <van-icon
        name="add-o"
        size="25"
        @click="goOtherPage('/Publish')"
        slot="right"
        class="right-icon"
      />
    </Nav>
    <div class="middle">
      <div v-if="focusSet.length">
        <div class="focus">
          <span>已关注</span>
          <img src="../../static/arrow.svg" alt="arrow" />
        </div>
        <div class="box">
          <div
            v-for="(item, i) in focusSet"
            :key="i"
            class="boxItem"
            @click="goOtherPage('/chatList')"
          >
            <el-avatar :src="item.avatarUrl" :size="50"></el-avatar>
            <span>{{ item.username }}</span>
          </div>
        </div>
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
      </li>
    </ul>
  </div>
</template>
<script>
import Moment from "../../components/Moment.vue";
import Nav from "../../components/Nav.vue";
export default {
  data() {
    return {
      texts: [],
      pageNum: 1,
      totalPage: 0,
      focusSet: [],
    };
  },
  mounted() {
    this.getAll();
  },
  methods: {
    async getAll() {
      this.$axios
        .get("/api1/text/all", {
          params: {
            pageNum: this.pageNum,
            pageSize: 100,
          },
        })
        .then((res) => {
          if (res.msg === "success") {
            this.texts.push(...res.allText);
            this.totalPage = res.totalPage;
          }
        });
      // let { code, allText, totalPage } = await this.$axios.get(
      //   `api1/text/all?pageNum=${this.pageNum}&pagSize=100`
      // );
      // console.log(code);
      // if (code == 0) {
      //   this.texts.push(...allText);
      //   this.totalPage = totalPage;
      // }
      this.$axios.get("/api1/monitored/user").then((res) => {
        this.focusSet = res.results;
      });
    },
    goPublish() {
      this.$router.push();
    },
    goOtherPage(pageName) {
      this.$router.push(pageName);
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
  // 页面处理函数--监听页面滚动(not-nvue)
  components: { Moment, Nav },
};
</script>
<style lang="less" scoped>
.Moments {
  overflow: scroll;
  height: 100%;
  background-color: #f3f4f4;
  padding-bottom: 30px;
  .right-icon {
    position: absolute;
    right: 7vw;
  }
  .before-icon {
    position: absolute;
    left: 36%;
    padding-top: 4px;
  }
  .after-icon {
    position: absolute;
    right: 35%;
    padding-top: 3px;
  }
}
.middle {
  box-sizing: border-box;
  width: 95%;
  display: flex;
  justify-content: flex-start;
  border-radius: 10px;
  color: rgb(76, 33, 79);
  .focus {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 20px;
    width: 90px;
    position: relative;
    margin: 10px 8px;
    letter-spacing: 1px;
    span {
      display: inline-block;
      font-size: 16px;
      margin: 2px;
      color: rgb(149, 142, 142);
    }
    img {
      width: 15px;
    }
  }
}
.box {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 13px 0;
  margin-left: 15px;
  .boxItem {
    height: 72px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    margin: 0 5px;
    span {
      font-size: 15px;
    }
  }
}

.main {
  box-sizing: border-box;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}
</style>
