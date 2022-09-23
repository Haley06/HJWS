<template>
  <div class="card">
    <div class="userHead">
      <img :src="img" alt="user head" />
    </div>
    <div class="userInfo">
      <!-- <slot name="middleInfo"></slot> -->
      <div class="">
        <div class="username">
          {{ username }}
        </div>
        <div class="status van-ellipsis">
          {{ describe == 1 ? "近期有危险驾驶行为" : "近期无危险驾驶行为" }}
        </div>
      </div>
    </div>
    <div class="operateButton">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    img: {
      type: String,
      required: true,
    },
    username: {
      type: String,
      required: true,
    },
    describe: {
      type: Number,
      required: false,
    },
  },
};
</script>

<style lang="less" scoped>
.card {
  width: 100%;
  height: 10.5vh;
  display: flex;
  align-items: center;
  border-radius: 2vh;
  background-color: #fff;
  .userHead {
    width: 12vh;
    height: 8vh;
    text-align: center;
    img {
      height: 8vh;
      width: 8vh;
      border-radius: 50%;
      box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.4);
    }
  }
}
.userInfo {
  width: 55vw;
  margin-left: 2vw;
  .username {
    font-weight: 600;
    font-size: 20px;
  }
  .status {
    margin-top: 1vw;
    font-size: 14px;
    width: 55vw;
  }
}
.operateButton {
  width: 24vw;
  display: flex;
  justify-content: center;
}
.success {
  background-color: #52c41a;
}
.warning {
  background-color: #ff4d4f;
}
.primary {
  background-color: #bfbfbf;
}
.clean_button_style {
  padding: 5px 5px;
  border-radius: 1.5vw;
  font-size: 14px;
  color: #fff;
  border: none;
}
</style>
