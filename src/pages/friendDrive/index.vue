<template>
  <div class="friendDrive">
    <nav-vue><b>好友驾驶</b></nav-vue>
    <!-- 后期改成点击跳转 -->
    <div class="search" @click="$router.push('addFriend')">
      <div class="container">
        <div class="searchIcon">
          <van-icon name="search" />
        </div>
        <div class="content">输入手机号关注/搜索好友</div>
      </div>
    </div>
    <van-empty
      image="https://fastly.jsdelivr.net/npm/@vant/assets/custom-empty-image.png"
      image-size="80"
      description="还未添加任何好友"
      class="empty"
      v-if="!isHavaFriend"
    />
    <div class="userList" v-else>
      <user-card
        v-for="(item, index) in userInfoList"
        :key="index"
        :username="item.username"
        :describe="item.isAbnormal"
        :img="item.avatarUrl"
        @click.native="toFriendDriveState(item)"
      >
        <template>
          <button
            :class="[
              item.status === 0
                ? 'primary'
                : item.status === 1
                ? 'success'
                : 'warning',
              'clean_button_style',
            ]"
          >
            {{ changeStatus(item.status) }}
          </button>
        </template>
      </user-card>
    </div>
  </div>
</template>

<script>
import NavVue from "../../components/Nav.vue";
import userCard from "./userCard.vue";
export default {
  components: {
    NavVue,
    userCard,
  },
  data() {
    return {
      isHavaFriend: true,
      userInfoList: [],
    };
  },
  methods: {
    // 根据传输的数据进行类别判断
    changeStatus(state) {
      let stateString = "";
      switch (state) {
        case 0: {
          stateString = "未驾驶";
          break;
        }
        case 1: {
          stateString = "正常驾驶";
          break;
        }
        case 2: {
          stateString = "危险驾驶";
          break;
        }
        default: {
          stateString = "程序错误";
        }
      }
      return stateString;
    },
    async getAllFollowUser() {
      const data = await this.$axios.get(this.$url.getAllFollowUser.path);
      if (data.code !== 0) {
        vant.Toast({
          message: data.msg,
        });
      }
      let list = data.drivenLists;
      // 如果头像有null 就给予默认头像
      list.forEach((item) => {
        item.avatarUrl =
          item.avatarUrl === null || item.avatarUrl === undefined
            ? "https://tva3.sinaimg.cn/large/006xVoHRly8h52o7vrqswj30j60j60sq.jpg"
            : item.avatarUrl;
      });
      this.userInfoList = list;
    },
    toFriendDriveState(item) {
      if (item.status == 0) {
        vant.Toast({
          message: "当前驾驶员未驾驶汽车",
        });
      } else {
        this.$router.push({
          path: "/friendDriveState",
          query: {
            item,
          },
        });
      }
      console.log(item);
    },
  },
  created() {
    this.getAllFollowUser();
  },
};
</script>

<style lang="less" scoped>
.friendDrive {
  background-color: #f0f0f0;
  height: 100%;
}
.empty {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .van-empty__description {
    padding: 0px;
  }
}
.userList {
  width: 95vw;
  margin: 0 auto;
  margin-top: 3vh;
}
.card {
  margin-bottom: 1.5vh;
}
.search {
  background-color: #fff;
  height: 5.5vh;
  .container {
    display: flex;
    width: 90%;
    margin: 0 auto;
    background-color: #f0f0f0;
    height: 4.2vh;
    align-items: center;
    .content {
      flex: 1;
      text-align: center;
      font-size: 14px;
      color: #8c8c8c;
    }
    .searchIcon {
      margin-left: 3vw;
    }
  }
}
</style>
