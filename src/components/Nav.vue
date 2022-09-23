<template>
  <div class="nav">
    <van-icon name="arrow-left" @click="goBack" size="25" class="icon" />
    <span class="nav-title">
      <slot name="before"></slot>
      <slot></slot>
      <slot name="after"></slot>
    </span>
    <slot name="right"></slot>
  </div>
</template>

<script>
export default {
  name: "Back",
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped lang="less">
.nav {
  width: 100vw;
  height: 65px;
  overflow: hidden;
  background-color: #fff;
  position: relative;
  .flex_init(row, center, center);
  .nav-title {
    .letter(@letter-spacing: 2px, @font-size: 18px, @font-weight: 450);
  }
}
.icon {
  position: absolute;
  left: 7vw;
  padding: 10px 20px 10px 0px;
}
</style>
