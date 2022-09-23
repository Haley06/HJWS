<template>
  <div class="driveState">
    <Nav>好友状态</Nav>
    <div class="content">
      <el-card>
        <el-row>
          <el-col :span="8" class="userInfo">
            <el-avatar :size="36" :src="item.avatarUrl"></el-avatar>
            <span
              ><b>{{ item.username }}</b></span
            >
          </el-col>
          <el-col
            :span="10"
            v-text="
              item.isAbnomal === 0 ? '未检测到疲劳驾驶' : '检测到疲劳驾驶'
            "
            class="isAbnomal"
          >
          </el-col>
          <el-col :span="6">
            <el-button type="danger" size="mini"> 拨打电话 </el-button>
          </el-col>
        </el-row>
      </el-card>
      <el-card class="driveMood">
        <el-row>
          <el-col :span="12">
            <span>心情状态</span>
            <el-button type="success" size="mini"> 开心 </el-button>
          </el-col>
          <el-col :span="12">
            <span>是否酒驾</span>
            <el-button type="success" size="mini"> {{ isAlcohol }} </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <span>心率</span>
            <el-button type="success" size="mini"> 正常 </el-button>
          </el-col>
          <el-col :span="12">
            <span>血氧</span>
            <el-button type="success" size="mini"> 正常 </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <span>是否走神</span>
            <el-button type="success" size="mini">
              {{ isDistraction }}
            </el-button>
          </el-col>
        </el-row>
      </el-card>
      <el-card class="box-card history">
        <div class="history-title title">最近一周驾驶记录</div>
        <div id="history-chart-1" class="history-chart"></div>
      </el-card>
    </div>
  </div>
</template>

<script>
import Nav from "../../components/Nav.vue";
export default {
  data() {
    return {
      userId: 0,
      start: 0,
      end: 0,
      item: {},
      isAlcohol: "否",
      isDistraction: "否",
    };
  },
  methods: {
    async getResult(option) {
      return new Promise(async (resolve, reject) => {
        let days = new Date();
        this.start = (days.getTime() + 1000 * 60 * 60 * 24 * -6) / 1000;
        this.end = days.getTime() / 1000;
        try {
          let data = await this.$axios.post(this.$url.getResult.path, {
            start: this.start,
            end: this.end,
            userId: this.item.monitoredId,
            type: 1,
          });
          option.series[0].data = data.count;
        } catch (error) {
          option.series[0].data = [0, 0, 0, 0, 0, 0, 0];
        }
        option.xAxis[0].data.forEach((item, index, arr) => {
          arr[index] = this.getDay(-(6 - index)).day;
        });
        resolve(option);
      });
    },
    getDay(day) {
      //Date()返回当日的日期和时间。
      var days = new Date();
      //getTime()返回 1970 年 1 月 1 日至今的毫秒数。
      var gettimes = days.getTime() + 1000 * 60 * 60 * 24 * day;
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
  },
  mounted() {
    this.$init(this.getResult, "baseInfoOption1", "history-chart-1");
  },
  created() {
    console.log(this.$route);
    this.userId = this.$route.query.item.userId;
    this.item = this.$route.query.item;
  },
  components: { Nav },
};
</script>

<style scoped lang="less">
.driveState {
  background-color: rgba(234, 234, 234, 0.6);
  height: 100%;
  .content {
    width: 90vw;
    margin: 20px auto 0;
    .userInfo {
      display: flex;
      align-items: center;
      span {
        margin-left: 1vw;
      }
    }
    .isAbnomal {
      display: flex;
      align-items: center;
      font-size: 14px;
      color: #494949;
    }
    .driveMood {
      margin-top: 2vh;
      .el-col {
        display: flex;
        justify-content: space-around;
        margin-top: 10px;
        .el-button {
          width: 56px;
        }
        span {
          width: 64px;
        }
      }
    }
  }
  .el-card__body {
    padding: 10px;
  }
  .el-col {
    display: flex;
    height: 40px;
    align-items: center;
  }
  .history-chart {
    width: 300px;
    min-height: 150px;
  }
  .history {
    margin-top: 2vh;
  }
}
</style>
