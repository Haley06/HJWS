<template>
  <div class="person-info">
    <van-uploader
      :after-read="afterRead"
      multiple
      ref="uploader"
      :max-count="1"
    >
      <user-info-card-vue leftText="头像" cardHeight="100">
        <img :src="userInfoCopy.avatarUrl" alt="" class="userHeadImg"
      /></user-info-card-vue>
    </van-uploader>
    <div @click="choiceShow.username = true">
      <user-info-card-vue leftText="昵称">
        <div class="userInfo">{{ userInfoCopy.username }}</div>
      </user-info-card-vue>
    </div>
    <div @click="choiceShow.sex = true">
      <user-info-card-vue leftText="性别">
        <div class="userInfo">{{ userInfoCopy.sex ? "男" : "女" }}</div>
      </user-info-card-vue>
    </div>
    <div>
      <user-info-card-vue leftText="身份">
        <div class="userInfo">{{ userInfoCopy.role ? "VIP" : "普通用户" }}</div>
      </user-info-card-vue>
    </div>
    <div @click="choiceShow.introduction = true">
      <user-info-card-vue leftText="简介">
        <div class="userInfo van-ellipsis">{{ userInfoCopy.introduction }}</div>
      </user-info-card-vue>
    </div>

    <div class="preserve">
      <button
        :class="[isPreserve ? 'isPreserve' : 'noPreserve']"
        @click="changeUserInfo"
      >
        保存
      </button>
    </div>

    <!-- 弹出层 -->
    <!-- 用户名 -->
    <van-popup
      v-model="choiceShow.username"
      position="bottom"
      :style="{ height: '30%' }"
      round
      closeable
      @open="openPopup"
      @close="closePopup"
    >
      <van-field
        v-model="userInfoCopy.username"
        rows="5"
        autosize
        type="textarea"
      />
    </van-popup>
    <!-- 性别 -->
    <van-popup
      v-model="choiceShow.sex"
      position="bottom"
      :style="{ height: '30%' }"
      round
      closeable
      @click="closePopup"
    >
      <van-radio-group v-model="userInfoCopy.sex">
        <van-cell-group>
          <van-cell title="男" clickable @click="userInfoCopy.sex = 1">
            <template #right-icon>
              <van-radio :name="1" />
            </template>
          </van-cell>
          <van-cell title="女" clickable @click="userInfoCopy.sex = 0">
            <template #right-icon>
              <van-radio :name="0" />
            </template>
          </van-cell>
        </van-cell-group>
      </van-radio-group>
    </van-popup>
    <!-- 简介 -->
    <van-popup
      v-model="choiceShow.introduction"
      position="bottom"
      :style="{ height: '30%' }"
      round
      closeable
      @close="closePopup"
    >
      <van-field
        v-model="userInfoCopy.introduction"
        rows="5"
        autosize
        type="textarea"
      />
    </van-popup>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import isObjectValueEqual from "../../../utils/isObjectValueEqual";
import userInfoCardVue from "../../../components/userInfoCard.vue";
export default {
  name: "PersonInfo",
  components: {
    userInfoCardVue,
  },
  computed: { ...mapState("user", ["userInfo"]) },
  data() {
    return {
      // 判断个人信息是否被更改
      isPreserve: true,
      userInfoCopy: {},
      userInfoPopup: {},
      choiceShow: {
        username: false,
        sex: false,
        introduction: false,
      },
      file: new FormData(),
    };
  },
  methods: {
    ...mapMutations("user", ["changeUserInfo"]),
    // 比较两个是否
    isObjectValueEqual,
    async changeUserInfo() {
      const data = await this.$axios.post(
        this.$url.changeUserInfo.path,
        this.userInfoCopy
      );
      const response = await fetch("api/app/photo/upload", {
        method: "POST",
        headers: {
          token: localStorage.getItem("token"),
        },
        body: this.file,
      }).catch((err) => {
        console.log(err);
      });
      console.log(response);
    },
    async afterRead(file) {
      this.file.append("file", file.file);
      var url = window.URL || window.webkitURL;
      var imgUrl = url.createObjectURL(file.file);
      this.userInfoCopy.avatarUrl = imgUrl;
    },
    openPopup() {
      // 为后期优化
      this.userInfoPopup = Object.assign({}, this.userInfoCopy);
    },
    closePopup() {},
  },
  created() {
    this.userInfoCopy = this.userInfo;
  },
};
</script>

<style scoped lang="less">
.person-info {
  width: 100%;
  height: calc(100vh - 60px);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  background-color: #f5f5f5;
}
.userInfo {
  color: #aab7c0;
  font-weight: 500;
  width: 70vw;
  text-align: right;
}
.van-uploader {
  margin-top: 10px;
  margin-bottom: 20px;
}
.preserve {
  margin-top: 40px;
  width: 80%;
  height: 40px;
  button {
    border: none;
    width: 100%;
    height: 100%;
    letter-spacing: 1px;
    font-size: 18px;
    padding: 5px 0px;
    font-weight: 500;
  }
  .noPreserve {
    color: rgba(153, 129, 184, 0.9);
    background-color: #fff;
  }
  .isPreserve {
    color: #fff;
    background-color: rgba(153, 129, 184, 0.9);
  }
}
/deep/ .van-field__control {
  background-color: #bbbfc2;
  padding-left: 10px;
}
.van-field {
  margin-top: 40px;
}

.van-radio-group {
  margin-top: 40px;
}
</style>
