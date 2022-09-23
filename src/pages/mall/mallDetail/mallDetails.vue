<template>
  <div class="goodsDetails">
    <nav-vue>商品详情</nav-vue>
    <div class="detailsCard">
      <div class="img">
        <img :src="goodsDetails.picture" alt="" />
      </div>
      <div class="goodsInfo">
        <div class="goodsName overflowOmit">{{ goodsDetails.name }}</div>
        <div class="goodsPrice">
          ￥<span>{{ goodsDetails.price }}</span
          >.00
        </div>
        <div class="goodsOperate">
          <div class="left">
            <div class="collect">
              <div class="icon">
                <img src="../../../static/svg/collect.svg" alt="" />
              </div>
              <div class="operateName">收藏</div>
            </div>
          </div>
          <div class="right">
            <div class="addToCart" @click="addGoodsInCart">
              <button>加入购物车</button>
            </div>
            <div class="buy">
              <div>立即购买</div>
              <!-- <div>到手价￥{{ goodsDetails.price }}</div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavVue from "../../../components/Nav.vue";
export default {
  components: {
    NavVue,
  },
  data() {
    return {
      goodsId: 0,
      goodsDetails: {},
    };
  },
  methods: {
    async getGoodsDetail() {
      const data = await this.$axios.post("/api1/shop/getById", {
        id: this.goodsId,
      });
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.goodsDetails = data.details;
      console.log(this.goodsDetails);
      console.log(data);
    },
    // 将商品加入购物车
    async addGoodsInCart() {
      const data = await this.$axios.post("/api1/shop/addCart", {
        id: this.goodsId,
      });
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.$Message("已加入购物车", "success");
    },
  },
  created() {
    this.goodsId = this.$route.query.id;
    this.getGoodsDetail();
  },
};
</script>

<style scoped lang="less">
.goodsDetails {
  height: 100%;
  background-color: #333;
  .detailsCard {
    margin: 6vh auto 0;
    width: 80vw;
    height: 65vh;
    border-radius: 1.5vh;
    overflow: hidden;
    background-color: #fff;
    img {
      width: 100%;
    }
  }
}
.goodsInfo {
  margin-left: 4vw;

  .goodsName {
    width: 65vw;
    // margin: 0 auto;
    margin-top: 1vh;
    font-weight: 600;
  }
  .goodsPrice {
    color: red;
    span {
      font-size: 24px;
    }
  }
  .goodsOperate {
    display: flex;
    justify-content: space-around;
    margin-top: 2vh;
    .collect {
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 10vw;
      .icon {
        width: 6vw;
      }
      .operateName {
        font-size: 12px;
        color: #7a7777;
      }
    }

    .right {
      display: flex;
      .addToCart {
        button {
          background-color: #fecc0d;
          border: none;
          padding: 10px 14px;
          color: #fff;
          border-radius: 20px;
        }
      }
      .buy {
        background: linear-gradient(#f92d0b, #fb580d);
        color: #fff;
        padding: 6px 14px;
        border-radius: 30px;
        display: flex;
        align-items: center;
        margin-left: 10px;
      }
    }
  }
}
</style>
