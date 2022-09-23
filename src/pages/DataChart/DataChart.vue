<template>
  <div class="dataChart">
    <Nav :is-show-back-arrow="false">检测历史</Nav>
    <div class="content">
      <el-card class="box-card history">
        <div class="history-title title">最近一周驾驶记录</div>
        <div id="history-chart-1" class="history-chart"></div>
      </el-card>
      <div class="middle">
        <el-card>
          <h5>最近一周酒驾次数</h5>
          <span>{{ periodFatigue }}次</span>
          <h5 class="zoushen">最近一周走神次数</h5>
          <span>{{ periodDistraction }}次</span>
        </el-card>
        <el-card>
          <h5>总酒驾次数</h5>
          <span>{{ allFatigue }}次</span>
          <h5 class="zoushen">总走神次数</h5>
          <span>{{ allDistraction }}次</span>
        </el-card>
      </div>
      <el-card class="box-card history">
        <div class="history-title title">最近一周驾驶心情检测</div>
        <div id="history-chart-2" class="history-chart"></div>
      </el-card>
    </div>
  </div>
</template>

<script>
import Nav from "../../components/Nav";
export default {
  name: "DataChart",
  components: {
    Nav,
  },
  data() {
    return {
      start: 0,
      end: 0,
      periodFatigue: 0,
      allFatigue: 0,
      periodDistraction: 0,
      allDistraction: 0,
    };
  },
  methods: {
    async getResult(option) {
      return new Promise(async (resolve, reject) => {
        let days = new Date();
        this.start = (days.getTime() + 1000 * 60 * 60 * 24 * -6) / 1000;
        this.end = days.getTime() / 1000;
        let data = await this.$axios.post(this.$url.getResult.path, {
          start: this.start,
          end: this.end,
          type: 0,
        });
        option.xAxis[0].data.forEach((item, index, arr) => {
          arr[index] = this.getDay(-(6 - index)).day;
        });
        option.series[0].data = data.count;
        this.getData();
        resolve(option);
      });
    },
    async getResult1(option) {
      let days = new Date();
      const { mood } = await this.$axios.post("/api1/fatigue/period/mood", {
        start: (days.getTime() + -6 * 24 * 3600 * 1000) / 1000,
        end: days.getTime() / 1000,
        type: 0,
      });
      // console.log(data);
      // let mood = {
      //   happy: "90.00", //开心占比
      //   sadness: "0.00", //悲伤占比
      //   angry: "10.00", //生气占比
      //   normal: "0.00", //正常占比
      //   surprise: "0.00", //惊喜占比
      // };
      let moodName = {
        happy: "开心",
        sadness: "悲伤",
        angry: "生气",
        normal: "正常",
        surprise: "惊喜",
      };
      let list = Object.keys(mood);
      let moodList = [];
      list.forEach((item) => {
        moodList.push({
          value: mood[item] - 0,
          name: moodName[item],
        });
      });
      option.series[0].data = moodList;
      return option;
    },
    getDay(day) {
      //Date()返回当日的日期和时间。
      var days = new Date();
      //getTime()返回 1970 年 1 月 1 日至今的毫秒数。
      var gettimes = (days.getTime() + 1000 * 60 * 60 * 24 * day) / 1000;
      //setTime()以毫秒设置 Date 对象。
      days.setTime(gettimes);
      var month = days.getMonth() + 1;
      if (month < 10) {
        month = month;
      }
      var today = days.getDate();
      if (today < 10) {
        today = today;
      }
      return {
        day: month + "/" + today,
        seconds: gettimes,
      };
    },
    async getData() {
      // 一周酒驾和走神次数
      // 酒驾
      const data1 = await this.$axios.post("/api1/fatigue/period/getAlcohol", {
        start: this.start,
        end: this.end,
        type: 0,
      });
      if (data1.code != 0) {
        return this.$Message(data1.msg);
      }
      this.periodFatigue = data1.total;
      // 走神
      const data11 = await this.$axios.post("/api1/fatigue/period/lookAhead", {
        start: this.start,
        end: this.end,
        type: 0,
      });
      if (data11.code != 0) {
        return this.$Message(data11.msg);
      }
      this.periodDistraction = data11.total;

      // 所有的走神和酒驾次数
      // 酒驾
      const data2 = await this.$axios.get("/api1/fatigue/getAlcohol");
      if (data2.code != 0) {
        return this.$Message(data1.msg);
      }
      this.allFatigue = data2.total;
      // 走神
      const data22 = await this.$axios.get("/api1/fatigue/getLook");
      if (data2.code != 0) {
        return this.$Message(data1.msg);
      }
      this.allDistraction = data22.total;
    },
  },
  mounted() {
    this.$init(this.getResult, "baseInfoOption1", "history-chart-1");
    this.$init(this.getResult1, "deviceState", "history-chart-2");
  },
};
</script>

<style scoped lang="less">
.dataChart {
  width: 100vw;
  height: 100vh;
  overflow: scroll;
  background-color: rgba(234, 234, 234, 0.6);
  box-sizing: border-box;
  letter-spacing: 1px;
  .content {
    padding: 0 20px;
    .history {
      .title {
        text-align: center;
        font-size: 17px;
        font-weight: 450;
      }
    }
  }
}
.box-card {
  width: 100%;
  min-height: 200px;
  margin: 20px auto;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
}
.history-chart {
  width: 300px;
  min-height: 150px;
}
#history-chart-2 {
  margin-top: 20px;
  min-height: 300px;
}
.middle {
  display: flex;
  justify-content: space-between;
  .el-card {
    width: 47%;
    span {
      display: block;
      margin-top: 5px;
    }

    .zoushen {
      margin-top: 15px;
    }
  }
}
</style>
