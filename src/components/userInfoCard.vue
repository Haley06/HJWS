<template>
  <div class="userHead infoItem" :style="'height:' + cardHeight + 'px'">
    <span class="left">{{ leftText }}</span>
    <div class="right">
      <slot class="right_slot"></slot>
      <van-icon name="arrow" class="icon" />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    leftText: {
      type: String,
      required: true,
    },
    cardHeight: {
      type: String,
      required: false,
      default: "50",
    },
  },
};
</script>

<style scoped lang="less">
.infoItem {
  width: 100vw;
  margin-bottom: 5px;
}
.userHead {
  height: 100px;
  background-color: #fff;
  .flex_init(row, space-between, center);
  .userHeadImg {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
  }
}
.left {
  width: 20vw;
  font-size: 18px;
  text-align: center;
}
.right {
  width: 80vw;
  display: flex;
  align-items: center;
  margin-right: 20px;
  justify-content: flex-end;
  .icon {
    margin-left: 10px;
    color: gainsboro;
  }
  &&:nth-child(0) {
    width: 60vw;
  }
}
</style>
