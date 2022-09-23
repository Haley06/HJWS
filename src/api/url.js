//@param showType { 0为Toast;1为Notify;忽略该属性则不予弹窗 }

const url = {
  login: {
    path: "/api1/login",
    successMsg: "登录成功",
    showType: 0,
  },
  register: {
    path: "/api1/register/old",
    successMsg: "注册成功",
    showType: 0,
  },
  getUserInfo: {
    path: "/api1/info",
    successMsg: "获取成功",
    showType: 0,
  },
  changeUserInfo: {
    path: "/api1/info",
    successMsg: "获取成功",
    showType: 0,
  },
  getVercode: {
    path: "/api1/login/vercode",
    successMsg: "获取成功",
    showType: 1,
  },
  postVercode: {
    path: "/api1/vercode",
    successMsg: "登录成功",
    showType: 0,
  },
  getPhone: {
    path: "/api1/phone",
    successMsg: "获取成功",
  },
  postPhone: {
    path: "/api1/phone",
    successMsg: "提交成功",
    showType: 0,
  },
  getResult: {
    path: "/api1/fatigue/query",
  },
  getUserByPhone: {
    path: "/api1/monitored/selectByPhone",
    successMsg: "获取成功",
    showType: 0,
  },
  addFollow: {
    path: "/api1/monitored/add",
    successMsg: "已发送申请",
    showType: 0,
  },
  getAllFollowUser: {
    path: "/api1/monitored/userDriven",
    successMsg: "获取成功",
    showType: 0,
  },
  getCategories: {
    path: "/api1/shop/getCategory",
  },
  getGoodsAll: {
    path: "/api1/shop/getAll",
  },
  deleteContent: {
    path: "/api1/text/delete",
    successMsg: "删除成功",
    showType: 1,
  },
  Release: {
    path: "/api1/text/add",
    successMsg: "发表成功",
    showType: 0,
  },
  DeleteComment: {
    path: "/api1/comment/delete",
    successMsg: "删除成功",
    showType: 0,
  },
};
export default url;
