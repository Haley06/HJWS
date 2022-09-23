import url from "./url";
// 正式env
// axios.defaults.baseURL = "app/";
// dev env
axios.defaults.baseURL = "";

axios.defaults.timeout = 10000;

function showMessage(url, isConst, constMsg) {
  // 判断url中是否有 showType 属性
  if (url && !url.hasOwnProperty("showType")) return; //无type属性,不需要弹窗
  if (isConst) {
    //处理固定的错误弹窗
    // 轻提示
    vant.Toast({
      message: constMsg,
      icon: "user-circle-o",
    });
  } else {
    //不同业务的成功弹窗
    if (url.showType) {
      //区分两种不同形式弹窗
      vant.Notify({
        message: url.successMsg,
        type: "success",
      });
    } else {
      vant.Toast({
        message: url.successMsg,
        icon: "success",
      });
    }
  }
}

const responseHandle = {
  1: (response) => {
    for (let i in url) {
      if (url[i].path === response.config.url) {
        showMessage(url[i], 0);
      }
    }
    return response.data;
  },
  200: (response) => {
    return response.data;
  },
  401: (response) => {
    showMessage(null, 1, "验证失败");
    clearToken();
  },
  404: (response) => {
    showMessage(null, 1, "404");
  },
  networkFail: () => {
    showMessage(null, 1, "网络错误");
  },
  default: (response) => {
    if (response.data.code == 200) {
      return response.data;
    } else {
      showMessage(null, 1, response.data.msg);
      return Promise.reject(response);
    }
  },
};

function clearToken() {
  this.localStorage.clear();
}

axios.interceptors.request.use(
  (config) => {
    //js_bridge
    if (process.env.NODE_ENV === "development") {
      const token = window.localStorage.getItem("token");
      token && (config.headers.token = token);
    } else {
      try {
        config.headers.token = dsbrige.call("getToken");
      } catch (e) {}
      const token = window.localStorage.getItem("token");
      token && (config.headers.token = token);
    }

    return config;
  },
  (error) => {
    if (!window.navigator.onLine) return responseHandle["networkFail"]();
    responseHandle["default"]();
    return Promise.error(error);
  }
);

axios.interceptors.response.use(
  (response) => {
    //成功{code=1}打印url自定义,失败打印传回的msg
    return Number(response.data.code + 1) === 1
      ? responseHandle[1](response)
      : responseHandle["default"](response);
  },
  (error) => {
    if (!window.navigator.onLine) return responseHandle["networkFail"]();
    return responseHandle["default"](error);
  }
);
/**
 * @param {String} file 读取文件成功的回调信息
 * @param {String} url 图片上传的接口
 *  */
function upLoaderImg(file, url) {
  let params = new FormData();
  params.append("file", file);
  let config = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  };
  return new Promise((resolve, reject) => {
    axios
      .post(url, params, config)
      .then((res) => {
        if (res && res.data && res.data.status === 1) {
          resolve(res.data);
        } else {
          // 否则Toast提示
          vant.Toast({
            message: "请求出错",
            icon: "user-circle-o",
          });
        }
      })
      .catch((err) => {
        vant.Toast({
          message: "系统异常",
        });
        reject(err);
      });
  });
}
axios.upLoaderImg = upLoaderImg;
export default axios;
