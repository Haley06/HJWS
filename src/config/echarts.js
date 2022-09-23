import * as echarts from "echarts";

let options = {
  baseInfoOption1: {
    color: ["rgba(153, 129, 184, 0.95)"],
    tooltip: {
      trigger: "axis",
      axisPointer: {
        // 坐标轴指示器，坐标轴触发有效
        type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
      },
    },
    grid: {
      bottom: "0",
      top: "20",
      containLabel: true,
    },
    xAxis: [
      {
        type: "category",
        data: ["", "", "", "", "", "", ""],
        axisLine: {
          show: true,
          lineStyle: {
            color: "rgb(158,158,158)",
          },
        },
        axisTick: {
          show: false,
        },
      },
    ],
    yAxis: [
      {
        type: "value",
        axisLine: {
          show: true,
          lineStyle: {
            color: "rgb(125,125,125)",
          },
        },
        splitLine: {
          show: false,
        },
      },
    ],
    series: [
      {
        name: "疲劳监测次数",
        type: "line",
        barWidth: "60%",
        data: [0, 0, 0, 0, 0, 0, 0],
      },
    ],
  },
  deviceState: {
    tooltip: {
      trigger: "item",
    },
    legend: {
      left: "center",
    },
    series: [
      {
        name: "访问来源",
        type: "pie",
        radius: ["40%", "70%"],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 6,
          borderColor: "#fff",
          borderWidth: 2,
        },
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "20",
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: [
          { value: 2, name: "安全驾驶" },
          { value: 1, name: "疲劳驾驶" },
        ],
      },
    ],
  },
};

async function init(request, type, dom) {
  let myOption = options[type];
  let data;
  let myChart = echarts.init(document.getElementById(dom));
  myChart.setOption(myOption);
  if (typeof request == "function") {
    data = await request(myOption);
  }
  myChart.setOption(data);
  setTimeout(() => {
    myChart.resize();
  }, 0);
  window.onresize = function() {
    myChart.resize();
  };
}

export default init;
