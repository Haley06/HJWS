<template>
  <div class="addFriend">
    <nav-vue>添加好友</nav-vue>
    <van-search
      placeholder="请输入搜索关键词"
      v-model="searchText"
      show-action
      @search="getUserByPhone"
    >
      <template #action>
        <div @click="getUserByPhone">搜索</div>
      </template>
    </van-search>
    <van-empty
      image="https://fastly.jsdelivr.net/npm/@vant/assets/custom-empty-image.png"
      image-size="80"
      description="还未进行好友搜索"
      class="empty"
      v-if="!isSearched"
    />
    <div class="friendList" v-else>
      <user-card
        v-for="(item, index) in friendList"
        :key="index"
        :username="item.username"
        :img="item.avatarUrl"
        :describe="item.introduction"
      >
        <template>
          <button
            class="followButton"
            @click="addFollow(item.userId)"
            v-if="!item.isFocus"
          >
            关注
          </button>
          <button class="followButton" v-else>已关注</button>
        </template>
      </user-card>
    </div>
  </div>
</template>

<script>
import NavVue from "../../components/Nav.vue";
import userCard from "../friendDrive/userCard.vue";
export default {
  components: {
    NavVue,
    userCard,
  },
  mounted() {
    // console.log(this.$store.state);
  },
  data() {
    return {
      searchText: "",
      friendList: [],
    };
  },
  methods: {
    async getUserByPhone() {
      if (this.searchText === "") {
        return vant.Toast({
          message: "搜索内容不能为空",
        });
      }
      let reg = /^1(3\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\d|9[0-35-9])\d{8}$/;
      if (!reg.test(this.searchText)) {
        return vant.Toast({
          message: "请填写正确的手机号格式",
        });
      }
      const data = await this.$axios.post(this.$url.getUserByPhone.path, {
        phone: this.searchText,
      });
      if (data.code !== 0) {
        vant.Toast({
          message: data.msg,
        });
      }
      let list = data.list;
      // 如果头像有null 就给予默认头像
      list.forEach((item) => {
        item.avatarUrl =
          item.avatarUrl === null
            ? "https://tva3.sinaimg.cn/large/006xVoHRly8h52o7vrqswj30j60j60sq.jpg"
            : item.avatarUrl;
      });
      this.friendList = list;
      console.log(this.friendList[0].isFocus);
    },
    async addFollow(id) {
      const data = await this.$axios.post(this.$url.addFollow.path, {
        monitoredId: id,
      });
      this.$store.commit("userAddFriends", {
        id: id,
        monitoreId: data.monitoreId,
      });
    },
  },
  computed: {
    isSearched() {
      if (this.friendList.length === 0) {
        return false;
      } else {
        return true;
      }
    },
  },
};
</script>

<style lang="less" scoped>
.empty {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .van-empty__description {
    padding: 0px;
  }
}
.addFriend {
  height: 100%;
  background-color: #f0f0f0;
  .friendList {
    margin: 3vh auto 0;
    width: 95%;
  }
  .card {
    margin-bottom: 1.5vh;
  }
  .followButton {
    border: none;
    background-color: #fff;
    font-weight: 600;
  }
}
</style>
