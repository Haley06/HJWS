<template>
  <div class="categoryPage">
    <nav-vue>商品分类</nav-vue>
    <div class="content">
      <div class="left">
        <div class="categoryList">
          <div
            v-for="(item, index) in categoryList"
            :key="index"
            :class="[item.categoryName === activeName ? 'active' : '']"
            @click="getCategoryAllGoods(item)"
          >
            {{ item.categoryName }}
          </div>
        </div>
      </div>
      <div class="right">
        <div class="goodsList">
          <mall-card
            v-for="(item, index) in goodsList"
            :key="index"
            :goodsImg="item.picture"
            :goodsName="item.name"
            :price="item.price"
            @click.native="goGoodsDetails(item.id)"
          ></mall-card>
        </div>
        <div class="bottom"></div>
      </div>
    </div>
  </div>
</template>

<script>
import NavVue from "../../../components/Nav.vue";
import mallCard from "../mallCard.vue";
export default {
  components: {
    NavVue,
    mallCard,
  },
  data() {
    return {
      categoryId: "",
      categoryList: [],
      activeName: "燃油添加剂",
      pageSize: 10,
      pageNum: 1,
      goodsList: [],
    };
  },
  methods: {
    // 根据商品分类获取商品
    async getCategoryAllGoods(item) {
      this.activeName = item.categoryName;
      const data = await this.$axios.post(
        `/api1/shop/byCategory?pageSize=${this.pageSize}&pageNum=${this.pageNum}`,
        {
          id: item.id,
        }
      );
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.goodsList = data.categoryProducts;
      console.log(this.goodsList);
    },
    // 获取全部分类
    async getCategory() {
      const data = await this.$axios.get(this.$url.getCategories.path);
      if (data.code !== 0) {
        this.$Message(data.msg);
      }
      this.categoryList = data.categories;
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
  },
  mounted() {
    this.categoryId = this.$route.query.categoryId;
    this.activeName = this.$route.query.categoryName;
    console.log(this.$route.query);
    this.getCategoryAllGoods(this.$route.query);
    this.getCategory();
  },
};
</script>

<style scoped lang="less">
.categoryPage {
  background-color: #f5f5f5;
  height: 100%;
  .content {
    display: flex;
    height: calc(100vh - 60px);

    .left {
      width: 25vw;
      height: 100%;
      .categoryList {
        display: flex;
        flex-direction: column;
        justify-content: center;
        height: 100%;
        font-size: 13px;
        div {
          margin: 2vh 2vw;
        }
      }
    }
    .right {
      width: 75vw;
      overflow: scroll;
    }
  }
}
.active {
  color: #9254de;
  font-weight: 600;
  font-size: 15px;
}
.goodsList {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.shopsCard {
  width: 34vw !important;
  margin-top: 2vh;
  ::v-deep img {
    width: 34vw;
  }
  ::v-deep .goodsName {
    width: 32vw;
  }
}
.bottom {
  height: 2vh;
}
</style>
