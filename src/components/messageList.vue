<template>
  <div class="moment-wraper">
    <div class="moment">
      <el-avatar :size="50" :src="item.avatar"> </el-avatar>
      <div class="info">
        <div>{{ item.nickname }}</div>
        <div class="text">
          {{ item.latestMessage }}
        </div>
      </div>
    </div>
    <div class="right">
      <div>{{ item.latestTime.substring(6, 16) }}</div>
      <el-badge :value="item.unRead" v-if="item.unRead"></el-badge>
    </div>
  </div>
</template>

<script>
export default {
  name: "Moment",
  props: {
    item: {},
  },
  mounted() {
    // console.log(this.item.unRead);
  },
  methods: {},
};
</script>

<style scoped lang="less">
.moment {
  box-sizing: border-box;
  width: 100%;
  background-color: #ffffff;
  border-bottom: 1px solid #d6d5d5;
  display: flex;
  padding: 10px 15px;
}
.el-avatar {
  margin: 3px 10px 0 0;
}
.info {
  display: flex;
  flex-direction: column;
}
.right {
  float: right;
  margin: -65px 20px 0 0;
  display: flex;
  flex-direction: column;
}
.el-badge {
  margin: 10px 0 0 50px;
}
.text {
  width: 170px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  color: #7f7f7f;
  font-size: 14px;
  margin-top: 13px;
}
</style>