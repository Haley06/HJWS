<template>
  <div class="aboutUs">
    <div class="logo">
      <img src="../../../static/logo.png" alt="" />
    </div>
    <div class="team">
      <h1>护驾卫士团队</h1>
    </div>
    <div class="slogan">护驾卫士伴你行, 安全驾驶护身符。</div>
    <div class="relate">Relate:&nbsp;sipc_1234567@163.com</div>
  </div>
</template>

<script>
export default {
  name: "Message",
};
</script>

<style scoped lang="less">
.aboutUs {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  .logo {
    width: 25vh;
    height: 25vh;
    margin-top: 120px;
    img {
      height: 100%;
      border-radius: 30px;
    }
  }
  .slogan {
    margin-top: 15px;
  }
  .team {
    margin-top: 30px;
  }
  .relate {
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    font-size: 16px;
  }
}
</style>
