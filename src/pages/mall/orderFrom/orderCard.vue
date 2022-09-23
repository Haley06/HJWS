<template>
  <div class="orderCard">
    <div class="orderDetail">
      <div class="orderImg">
        <img :src="img" alt="" />
      </div>
      <div class="orderText overflowMore">{{ orderName }}</div>
      <div class="orderPrice">
        <span style="font-size='11px'">￥</span>{{ price }}.00
      </div>
    </div>
    <div class="orderFunc">
      <div class="more">更多</div>
      <div class="againBuy" @click="goGoodsDetails()">
        <button>再次购买</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    img: {
      type: String,
      require: true,
    },
    orderName: {
      type: String,
      require: true,
    },
    price: {
      type: Number,
      required: true,
    },
    goodsId: {
      type: Number,
      required: true,
    },
  },
  methods: {
    goGoodsDetails() {
      if (this.goodsId === "") {
        return;
      }
      this.$router.push({
        path: "mallDetails",
        query: {
          id: this.goodsId,
        },
      });
    },
  },
};
</script>

<style scoped lang="less">
.orderCard {
  background-color: #fff;
  height: 18vh;
  border-radius: 2vw;
  border: 1px solid transparent;
  //   padding: 4px;
  .orderDetail {
    display: flex;
    margin-left: 3vw;
    margin-top: 2vw;
    align-items: center;
    justify-content: space-around;
    .orderImg {
      height: 10vh;
      width: 10vh;
      img {
        height: 100%;
        width: 100%;
        border-radius: 2vw;
      }
    }
    .orderText {
      width: 40vw;
      height: 7vh;
      margin-left: 3vw;
    }
    .orderPrice {
      font-size: 18px;
      margin-right: 2vw;
      span {
        font-size: 12px;
      }
    }
  }
  .orderFunc {
    display: flex;
    align-items: center;
    height: 7vh;
    justify-content: space-between;
    .more {
      font-size: 13px;
      font-weight: 600;
      margin-left: 6vw;
    }
    .againBuy {
      margin-right: 4vw;
      button {
        border: 1px solid #f92d0b;
        background-color: #fff;
        border-radius: 20px;
        font-size: 13px;
        padding: 5px 10px;
        color: #f92d0b;
      }
    }
  }
}
</style>
