<template>
  <div class="profile">
    <div class="profile-head">
      <img :src="userInfo.avatarUrl" alt="headImg" class="userHead" />
      <div class="nameAndStatus">
        <div class="username">{{ userInfo.username }}</div>
        <div class="tags">
          <div :class="['sex', userInfo.sex ? 'man' : 'woman']">
            <img src="../../static/svg/man.svg" alt="man" v-if="userInfo.sex" />
            <img src="../../static/svg/woman.svg" alt="woman" v-else />
          </div>
          <div :class="['status', userInfo.role ? '' : 'noVip']">vip</div>
        </div>
      </div>
      <div class="introduction van-ellipsis">{{ userInfo.introduction }}</div>
      <button class="userInfoEdit" @click="router('/settings/info')">
        编辑
      </button>
    </div>
    <div class="profile-middle">
      <div class="middle-bg"></div>
      <div class="middle-info">
        <span class="middle-info-vip1">开通护驾卫士会员</span>
        <span class="middle-info-vip2">享受高效的监测服务</span>
      </div>
      <button class="middle-grant-vip" @click="router('/settings/info')">
        立即开通
      </button>
    </div>
    <div class="profile-bottom">
      <div
        class="bottom-item"
        v-for="(item, index) in profileItemList"
        :key="index"
        @click="router(item.path)"
      >
        <img :src="item.svgPath" alt="" class="itemSvg" />
        <div class="title">{{ item.title }}</div>
        <img src="../../static/svg/rightArrow.svg" alt="" class="itemSvg" />
      </div>
      <button class="bottom-logout" @click="logOff">退出登录</button>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
export default {
  name: "Profile",
  data() {
    return {
      profileItemList: [
        {
          path: "/settings/emergency",
          svgPath: require("../../static/svg/contacts.svg"),
          title: "紧急联系人",
        },
        {
          path: "/settings/feedback",
          svgPath: require("../../static/svg/problemFeedback.svg"),
          title: "问题反馈",
        },
        {
          path: "/settings/aboutUs",
          svgPath: require("../../static/svg/aboutUs.svg"),
          title: "关于我们",
        },
        {
          path: "/settings/useGuidance",
          svgPath: require("../../static/svg/useGuidance.svg"),
          title: "使用引导",
        },
      ],
      userInfo: {
        avatarUrl:
          "https://pic3.zhimg.com/v2-9f5d9acb0bbad9ab7310b921f75d497e_b.jpg",
        sex: 1,
        introduction: "快来写你的简介,让别人更加了解你",
        username: "用户",
        role: false,
      },
      isMan: true,
    };
  },
  methods: {
    ...mapMutations("user", ["userInfoChange"]),
    router(path) {
      this.$router.push(path);
    },
    logOff() {
      this.$router.push("/login");
    },
    async getUserInfo() {
      const { info: data } = await this.$axios.get(this.$url.getUserInfo.path);
      this.userInfo.avatarUrl = data.avatarUrl
        ? data.avatarUrl
        : "https://pic3.zhimg.com/v2-9f5d9acb0bbad9ab7310b921f75d497e_b.jpg";
      this.userInfo.sex = data.sex ? data.sex : 0;
      this.userInfo.introduction = data.introduction
        ? data.introduction
        : "快来写你的简介,让别人更加了解你";
      this.userInfo.role = data.role == "1000" ? true : false;
      this.userInfo.username = data.username ? data.username : "用户";
      this.userInfoChange(this.userInfo);
      console.log(this.userInfo);
    },
  },

  created() {
    this.getUserInfo();
  },
};
</script>

<style scoped lang="less">
.profile {
  height: 100vh;
  width: 100%;
  overflow: scroll;
  padding-bottom: 20px;
  z-index: -30;
  background-color: rgba(234, 234, 234, 0.6);
  .flex_init(column, flex-start, center);

  .profile-head {
    position: relative;
    width: 90%;
    height: 16vh;
    margin-top: 50px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2), 0 0 6px rgba(0, 0, 0, 0.06);
    border-radius: 10px;
    background-color: rgba(153, 129, 184, 0.95);
    .userHead {
      height: 80px;
      width: 80px;
      border-radius: 50%;
      box-shadow: 0 10px 10px rgba(68, 68, 68, 0.1);
      position: absolute;
      top: -40px;
      left: 20px;
    }
    .nameAndStatus {
      display: flex;
      margin-top: 50px;
      margin-left: 20px;
      .username {
        font-size: 4.5vw;
      }
      .tags {
        display: flex;
        margin-left: 10px;
        .sex {
          width: 16px;
          height: 16px;
          border-radius: 50%;
          display: flex;
          justify-content: center;
          align-items: center;
          img {
            height: 80%;
            color: #fff;
            width: 80%;
          }
        }
        .man {
          background-color: #409eff;
        }
        .woman {
          background-color: #f56c6c;
        }
        .status {
          width: 6vw;
          font-size: 12px;
          text-align: center;
          border-radius: 6px;
          color: #fff;
          font-weight: 500;
          background-color: #ebb270;
          height: 2.2vh;
          margin-left: 10px;
        }
        .noVip {
          color: #333;
          background-color: #fff;
        }
      }
    }
    .introduction {
      color: rgb(223, 215, 215);
      font-size: 14px;
      margin: 10px 20px;
    }
    .userInfoEdit {
      position: absolute;
      right: 5%;
      top: 18%;
      border: 1px solid rgba(153, 129, 184, 0.9);
      padding: 4px 12px;
      font-size: 14px;
      border-radius: 5px;
      background-color: #fff;
      color: rgba(153, 129, 184, 0.9);
    }
    .head-info {
      height: 120px;
      overflow: hidden;
      margin-top: -8px;
      position: relative;
      .flex_init(row, flex-start, center);

      .info-details {
        min-width: 120px;
        max-width: 130px;
        position: absolute;
        right: 9vw;
        font-size: 25px;
        letter-spacing: 2px;
        text-indent: 2px;
        color: #373939;
        .ellipsis();

        .info-details-username {
          width: 120px;
        }
      }
    }
  }

  .profile-middle {
    .flex_init(row, space-around, center);
    height: 100px;
    width: 90%;
    margin-top: 20px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2), 0 0 6px rgba(0, 0, 0, 0.06);
    border-radius: 10px;
    background: url("https://tva1.sinaimg.cn/large/008i3skNly1gsaqpbbj1vj60dw04stak02.jpg");
    background-size: cover;
    filter: opacity(0.85);

    .middle-info {
      .flex_init(column, space-around, center);

      .middle-info-vip1 {
        font-size: 20px;
        font-weight: 500;
        letter-spacing: 1px;
        color: #533711;
      }

      .middle-info-vip2 {
        font-size: 15px;
        margin-top: 6px;
        font-weight: 450;
        letter-spacing: 1px;
        color: #b47b3f;
      }
    }

    .middle-grant-vip {
      padding: 5px 10px;
      border-radius: 18px;
      outline: none;
      color: #533711;
      border: 1px solid rgba(139, 118, 93, 0.18);
      background-color: #e2c272;
    }
  }
  .profile-bottom {
    .flex_init(column, space-around, center);
    width: 90%;
    position: relative;
    padding: 5px 0;
    background-color: #fff;
    margin-top: 30px;
    margin-bottom: 80px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.02), 0 0 6px rgba(0, 0, 0, 0.06);
    border-radius: 16px;
    .bottom-item {
      height: 60px;
      width: 95%;
      .flex_init(row, space-around, center);
      div {
        width: 65%;
        padding: 8px 0;
        font-size: 17px;
        letter-spacing: 1px;
        color: rgba(50, 50, 50, 0.95);
        border-bottom: 1px solid rgba(206, 205, 205, 0.5);
      }
      .itemSvg {
        width: 6vw;
        height: 6vw;
      }
    }
    .bottom-logout {
      position: absolute;
      bottom: -70px;
      width: 60%;
      padding: 10px 0;
      border-radius: 20px;
      border: none;
      background-color: rgba(153, 129, 184, 0.9);
      color: #f5f4ff;
      letter-spacing: 3px;
      text-indent: 3px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 0 6px rgba(0, 0, 0, 0.06);
    }
  }
}
</style>
