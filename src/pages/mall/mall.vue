<template>
  <div class="mall">
    <Nav><b>商城</b></Nav>
    <!-- 搜索开始 -->
    <div class="content">
      <van-search
        v-model="searchText"
        show-action
        shape="round"
        placeholder="请输入搜索关键词"
        @search="onSearch"
      >
        <template #action>
          <div @click="onSearch">搜索</div>
        </template>
      </van-search>
    </div>
    <!-- 轮播图开始 -->
    <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
      <van-swipe-item v-for="(item, index) in swipeList" :key="index">
        <img :src="item" alt="" />
      </van-swipe-item>
    </van-swipe>
    <!-- 个人功能 -->
    <div class="personFunc">
      <div class="title">个人功能</div>
      <div class="funcList">
        <div
          class="funcCard"
          v-for="(item, index) in funcList"
          :key="index"
          @click="$router.push(item.urlPath)"
        >
          <div class="icon">
            <img :src="item.path" alt="" />
          </div>
          <div class="funcName">{{ item.name }}</div>
        </div>
      </div>
    </div>
    <!-- 分类开始 -->
    <div class="category">
      <div class="title">商品分类</div>
      <div>
        <!-- <div class="moreCategory">
          <span>
            更多
          </span>

          <div class="moreCategory">
            <span> 更多 </span>
    
            <van-icon name="arrow" size="14" />
          </div> -->
        <div class="categoryLists">
          <div
            class="kind"
            v-for="(item, index) in categoryList"
            :key="index"
            @click="goGoodsCategory(item)"
          >
            <img :src="item.icon" alt="" />
            <div class="name">{{ item.categoryName }}</div>
          </div>
        </div>
      </div>
    </div>
    <!-- 商品推荐开始 -->
    <div class="shopsRecommend">
      <div class="title">商品推荐</div>
      <div class="goodsList">
        <mall-card
          v-for="(item, index) in allGoods"
          :key="index"
          :goodsImg="item.picture"
          :goodsName="item.name"
          :price="item.price"
          @click.native="goGoodsDetails(item.id)"
        ></mall-card>
      </div>
    </div>
    <div class="bottomSpace"></div>
  </div>
</template>

<script>
import Nav from "../../components/Nav.vue";
import mallCard from "./mallCard.vue";
export default {
  components: {
    Nav,
    mallCard,
  },
  data() {
    return {
      // 搜索用的关键字
      searchText: "",
      // 轮播图图片数据
      swipeList: [
        "http://img20.360buyimg.com/aotucms/jfs/t1/74936/25/10695/124640/5d80c72aE4bc278ae/abbaa089632044fc.jpg",
        "https://img14.360buyimg.com/aotucms/jfs/t1/47756/40/10984/106706/5d80c8d3E34bba8d2/174160d7de374688.jpg",
        "http://img13.360buyimg.com/aotucms/jfs/t1/60797/5/10671/121824/5d80c417E7868dcbf/2d472acd6601d56f.jpg",
        "https://img10.360buyimg.com/aotucms/jfs/t1/55852/10/10917/104671/5d80c558Eef960c90/4a22d5f030fd0566.jpg",
      ],
      categoryList: [],
      allGoods: [],
      pageData: {
        pageSize: 10,
        pageNum: 1,
      },
      funcList: [
        {
          path: require("@/static/svg/cart.svg"),
          name: "购物车",
          urlPath: "/cart",
        },
        {
          path: require("@/static/svg/orderFrom.svg"),
          name: "订单",
          urlPath: "orderFrom",
        },
        {
          path: require("@/static/svg/address.svg"),
          name: "地址管理",
          urlPath: "address",
        },
      ],
    };
  },
  methods: {
    onSearch() {
      console.log("进行搜索");
    },
    // 获取全部分类
    async getCategory() {
      const data = await this.$axios.get(this.$url.getCategories.path);
      if (data.code !== 0) {
        this.$Message(data.msg);
      }
      this.categoryList = data.categories;
    },
    // 获取推荐的商品
    async getGoodsAll() {
      const data = await this.$axios.get(this.$url.getGoodsAll.path, {
        params: this.pageData,
      });
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.allGoods = data.products;
    },
    // 跳转到商品详情页面
    goGoodsDetails(id) {
      if (id === "") {
        return;
      }
      this.$router.push({
        path: "mallDetails",
        query: {
          id,
        },
      });
    },
    // 跳转到商品分类页面
    goGoodsCategory(item) {
      this.$router.push({
        path: "categoryPage",
        query: item,
      });
      console.log(categoryId);
    },
  },
  created() {
    this.getCategory();
    this.getGoodsAll();
  },
};
</script>

<style lang="less" scoped>
.mall {
  height: 100%;
  overflow: scroll;
  background-color: #f5f5f5;
}

.my-swipe {
  margin-top: 2vh;

  .van-swipe-item {
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 100%;
      object-fit: cover;
    }
  }
}

.category {
  margin-top: 3vh;

  &::nth-child(2) {
    margin-top: 3vh;
  }
}

.title {
  font-size: 14px;
  font-weight: 600;
  border-left: 4px solid #9068c2;
  padding: 5px 10px;
  margin-left: 2vw;
}

.categoryLists {
  width: 90vw;
  padding: 2vw;
  margin: 2vh auto 0;
  background-color: #fff;
  border-radius: 2.2vw;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;

  .kind {
    width: 16vw;
    text-align: center;

    // box-shadow: 0 0 2px rgba(0, 0, 0, 0.4);
    img {
      height: 14vw;
    }

    .name {
      font-size: 12px;
    }
  }
}

.moreCategory {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-right: 4vw;
  margin-bottom: 1vh;

  span {
    font-size: 13px;
  }
}

.shopsRecommend {
  margin-top: 3vh;

  .goodsList {
    width: 94vw;
    margin: 0 auto;

    .shopsCard {
      margin-left: 4vw;
      margin-top: 2vh;
    }
  }
}

.bottomSpace {
  height: 2vh;
}

.personFunc {
  margin-top: 3vh;

  .funcList {
    width: 94vw;
    height: 10vh;
    background-color: #fff;
    margin: 2vh auto 0;
    display: flex;
    align-items: center;
    border-radius: 2vw;

    .funcCard {
      width: 22vw;
      display: flex;
      flex-direction: column;
      align-items: center;

      .icon {
        height: 6vh;
        width: 6vh;
        background-color: #f5f5f5;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
      }

      img {
        height: 4vh;
      }

      .funcName {
        font-size: 12px;
        text-align: center;
      }
    }
  }
}
</style>
