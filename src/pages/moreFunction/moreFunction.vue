<template>
  <div class="moreFunc">
    <Nav><b>更多功能</b></Nav>
    <div class="functionLists">
      <div
        v-for="(item, index) in functionLists"
        :key="index"
        class="funList"
        @click="$router.push(item.routePath)"
      >
        <div class="left">
          <div class="icon">
            <img :src="item.iconPath" alt="icon" />
          </div>
          <div class="text">{{ item.text }}</div>
        </div>
        <div class="right">
          <img src="../../static/arrow.svg" alt="arrow" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Nav from "../../components/Nav.vue";
export default {
  data: () => {
    return {
      functionLists: [
        {
          iconPath: require("@/static/carCircle.svg"),
          text: "车友圈",
          routePath: "/Moments",
        },
        {
          iconPath: require("@/static/friendDriving.svg"),
          text: "好友驾驶",
          routePath: "/friendDrive",
        },

        {
          iconPath: require("@/static/chatNews.svg"),
          text: "聊天消息",
          routePath: "/chatList",
        },

        {
          iconPath: require("@/static/carMusic.svg"),
          text: "车载音乐",
          routePath: "/music",
        },

        {
          iconPath: require("@/static/detectionHistory.svg"),
          text: "检测记录",
          routePath: "/dataChart",
        },
        {
          iconPath: require("@/static/mall.svg"),
          text: "车载商城",
          routePath: "/mall",
        },
      ],
    };
  },
  components: { Nav },
};
</script>

<style lang="less" scoped>
.moreFunc {
  height: 100%;
  background-color: #f3f4f4;
}
.functionLists {
  width: 90%;
  margin: 20px auto 0px;

  .funList {
    background-color: #fff;
    height: 60px;
    width: 100%;
    display: flex;
    justify-content: space-around;
    margin-bottom: 14px;
    border: 1px solid rgb(172, 166, 166);
    border-radius: 10px;
    align-items: center;
    &:nth-child(2n) {
      margin-bottom: 21px;
    }
    .left {
      width: 70%;
      display: flex;
      align-items: center;
      img {
        width: 40px;
        height: 40px;
      }
      .text {
        margin-left: 20px;
        font-size: 18px;
        font-weight: 600;
        color: #a692bd;
      }
    }
    .right {
      img {
        width: 30px;
        height: 30px;
      }
    }
  }
}
</style>
