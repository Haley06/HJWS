const path = require("path");
let cdn = {
  css: [
    // element-ui css
    "https://unpkg.com/element-ui/lib/theme-chalk/index.css", // 样式表
    // vant css
    "https://unpkg.com/vant@2.12.48/lib/index.css",
  ],
  js: [
    // vue must at first!
    "https://unpkg.com/vue@2.6.0/dist/vue.js", // vuejs
    // vue-router
    "https://unpkg.com/vue-router@3.5.3/dist/vue-router.js", // 这里做了替换
    // axios
    "https://unpkg.com/axios@0.27.2/dist/axios.js",
    // element-ui js
    "https://unpkg.com/element-ui/lib/index.js", // elementUI
    // echarts
    "https://unpkg.com/echarts@5.1.2/dist/echarts.js",
    // vant
    "https://unpkg.com/vant@2.12.48/lib/vant.js",
    // Vuex
    "https://cdn.bootcss.com/vuex/3.0.1/vuex.js",
  ],
};
let externals = {
  vue: "Vue",
  "vue-router": "VueRouter",
  moment: "moment",
  axios: "axios",
  vuex: "Vuex",
  vant: "vant",
  "element-ui": "ELEMENT",
  echarts: "echarts",
};

module.exports = {
  publicPath: "",
  configureWebpack: {
    externals: externals,
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "./src"),
      },
    },
  },
  chainWebpack(config) {
    config.plugin("html").tap((args) => {
      args[0].cdn = cdn;
      return args;
    });
    const types = ["vue"];
    types.forEach((type) => {
      config.module
        .rule("less")
        .oneOf(type)
        .use("style-resource")
        .loader("style-resources-loader")
        .options({
          patterns: [
            //公用的less变量
            path.resolve(__dirname, "./src/static/variable.less"),
            //公用的less混入
            path.resolve(__dirname, "./src/static/mixin.less"),
          ],
        });
    });
  },
  devServer: {
    hot: true,
    // disableHostCheck: true, //webpack4.0 开启热更新
    proxy: {
      // "/api": {
      //   target: "http://49.232.78.210:8089/",
      //   changeOrigin: true,
      //   pathRewrite: {
      //     "^/api": "",
      //   },
      // },
       "/api1": {
        //需要配置后端项目的url
        target: "SeverUrl",
        changeOrigin: true,
        pathRewrite: {
          "^/api1": "",
        },
      },
      "/api2": {
      //需要配置后端车载音乐部分的url
        target: "SeverMusicUrl",
        changeOrigin: true,
        pathRewrite: {
          "^/api2": "",
        },
      },
    },
  },
};
