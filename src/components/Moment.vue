<template>
  <div class="Moment">
    <div class="top">
      <div class="Navleft">
        <el-avatar :size="35" :src="avatarUrl"> </el-avatar>
        <span class="name">{{ name }}</span>
      </div>
      <div class="Navright">{{ timeTurn }}</div>
    </div>
    <div class="text">
      <span>
        {{ text }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Moment",
  props: ["text", "time", "name", "avatarUrl"],
  methods: {
    turnTime(time) {
      let timeday = time.substring(0, 5);
      timeday =
        timeday
          .split("")
          .filter((item) => item != "0")
          .join("")
          .replace("-", "月") +
        "日" +
        time.substring(5);

      return timeday;
    },
  },
  created() {
    this.timeTurn = this.turnTime(this.time);
  },
};
</script>

<style scoped lang="less">
.Moment {
  box-sizing: border-box;
  width: 93vw;
  height: 160px;
  padding: 3px;
  background-color: #ffffff;
  border-bottom: #dedbdb 1px solid;
  border-radius: 10px;
}
.top {
  display: flex;
  justify-content: space-between;
  padding: 10px 10px 5px 10px;
}
.Navleft {
  display: flex;
  font-size: 16px;
  .name {
    display: inline-block;
    line-height: 34px;
    margin-left: 13px;
  }
}
.Navright {
  text-align: center;
  line-height: 34px;
  padding-right: 10px;
  font-size: 16px;
  color: #919498;
}
.text {
  box-sizing: border-box;
  padding: 0 10px;
  line-height: 25px;
  color: #000000;
  text-indent: 35px;
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  word-break: break-all;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
}
</style>