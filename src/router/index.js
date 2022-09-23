import PersonInfo from "../pages/profile/Item/PersonInfo";
import Vue from "vue";
import Emergency from "../pages/profile/Item/Emergency";
import Moments from "../pages/Moments/Moments";
Vue.use(VueRouter);
const routes = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../pages/login/Login"),
  },
  {
    path: "/register",
    name: "Register",
    component: () => import("../pages/register/Register"),
  },
  {
    path: "/profile",
    name: "Profile",
    component: () => import("../pages/profile/Profile"),
  },
  {
    path: "/moreFunction",
    component: () => import("../pages/moreFunction/moreFunction.vue"),
  },
  {
    path: "/friendDrive",
    name: "FriendDrive",
    component: () => import("../pages/friendDrive/index.vue"),
  },
  {
    path: "/addFriend",
    name: "AddFriend",
    component: () => import("../pages/addFriend/index.vue"),
  },
  {
    path: "/friendDriveState",
    component: () => import("../pages/friendDriveState/index.vue"),
  },
  {
    path: "/settings",
    name: "Settings",
    component: () => import("../pages/profile/Item/index.vue"),
    children: [
      {
        path: "info",
        name: "PersonInfo",
        component: () => import("../pages/profile/Item/PersonInfo"),
      },
      {
        path: "emergency",
        name: "Emergency",
        component: Emergency,
      },
      {
        path: "aboutUs",
        name: "AboutUs",
        component: () => import("../pages/profile/Item/aboutUs"),
      },
      {
        path: "feedback",
        name: "Feedback",
        component: () => import("../pages/profile/Item/Feedback"),
      },
      {
        path: "useGuidance",
        name: "useGuidance",
        component: () => import("../pages/profile/Item/useGuidance.vue"),
      },
    ],
  },
  {
    path: "/dataChart",
    name: "DaraChart",
    component: () => import("../pages/DataChart/DataChart.vue"),
  },
  {
    path: "/Publish",
    name: "Publish",
    component: () => import("../pages/Moments/Item/Publish"),
  },
  {
    path: "/Detail",
    name: "Detail",
    component: () => import("../pages/Moments/Item/Detail"),
  },
  {
    path: "/Moments",
    name: "Moments",
    component: Moments,
  },
  {
    path: "/AllText",
    name: "AllText",
    component: () => import("../pages/Moments/Item/AllText"),
  },
  {
    path: "/Detail",
    name: "Detail",
    component: () => import("../pages/Moments/Item/Detail"),
  },
  {
    path: "/chat",
    name: "chat",
    component: () => import("../pages/chatList/chat/chat"),
  },
  {
    path: "/chatList",
    name: "chatList",
    component: () => import("../pages/chatList/chatList"),
  },
  {
    path: "/mall",
    name: "mall",
    component: () => import("../pages/mall/mall.vue"),
  },
  {
    path: "/mallDetails",
    component: () => import("../pages/mall/mallDetail/mallDetails.vue"),
  },
  {
    path: "/cart",
    component: () => import("../pages/mall/cart/cart.vue"),
  },
  {
    path: "/orderFrom",
    component: () => import("../pages/mall/orderFrom/orderFrom.vue"),
  },
  {
    path: "/address",
    component: () => import("../pages/mall/address/address.vue"),
  },
  {
    path: "/addAddress",
    component: () => import("../pages/mall/address/addAddress/addAddress.vue"),
  },
  {
    path: "/categoryPage",
    component: () => import("../pages/mall/category/category.vue"),
  },
  {
    path: "/editAddress",
    component: () =>
      import("../pages/mall/address/editAddress/editAddress.vue"),
  },
  {
    path: "/music",
    name: "music",
    component: () => import("../pages/music/Music.vue"),
  },
  {
    path: "/musicList",
    name: "musicList",
    component: () => import("../pages/music/MusicList"),
  },
  {
    path: "/deleteCar",
    name: "/deleteCar",
    component: () => import("../pages/Moments/Item/DeleteCar.vue"),
  },
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  next();
});

export default router;
