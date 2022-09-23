<template>
  <div class="cartCard">
    <div class="goodsImg">
      <img :src="goodsImg" alt="" />
    </div>
    <div class="goodsInfo">
      <div class="delete" @click="deleteCart"><van-icon name="cross" /></div>
      <div class="top">
        <div class="goodsName overflowOmit">{{ goodsName }}</div>
        <div class="goodsDesc overflowOmit">{{ goodsDesc }}</div>
      </div>
      <div class="bottom">
        <div class="goodsPrice">
          ￥<span>{{ goodsPrice }}</span
          >.00
        </div>
        <div class="goodsAmount">x{{ goodsAmount }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    goodsImg: {
      type: String,
      required: true,
    },
    goodsName: {
      type: String,
      required: true,
    },
    goodsAmount: {
      type: Number,
      required: true,
    },
    goodsPrice: {
      type: Number,
      required: true,
    },
    goodsDesc: {
      type: String,
      required: true,
    },
  },
  emit: ["deleteCart"],
  methods: {
    deleteCart() {
      this.$emit("deleteCart");
    },
  },
};
</script>

<style scoped lang="less">
.cartCard {
  display: flex;
  height: 20vh;
  align-items: center;
  width: 100vw;
  background-color: #fff;
  .goodsImg {
    width: 30vw;
    display: flex;
    justify-content: center;
    img {
      width: 24vw;
      border-radius: 3vw;
    }
  }
  .goodsInfo {
    width: 70vw;
    height: 100%;
    margin-left: 3vw;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    position: relative;
    overflow: hidden;
    .delete {
      position: absolute;
      top: 0px;
      width: 8vh;
      height: 8vh;
      display: flex;
      justify-content: flex-start;
      align-items: flex-end;
      right: 0px;
      background-color: #f5222d;
      color: #fff;
      border-radius: 50%;
      transform: translate(50%, -50%);
      .van-icon {
        transform: translate(50%, -50%);
      }
    }
    .goodsName {
      font-size: 16px;
      font-weight: 600;
      width: 50vw;
      margin-bottom: 1vh;
    }
    .goodsDesc {
      width: 60vw;
      font-size: 13px;
    }
    .goodsPrice {
      span {
        font-size: 18px;
      }
    }
    .goodsAmount {
      margin-right: 5vw;
      font-size: 14px;
      color: #bfbfbf;
    }
    .bottom {
      display: flex;
      justify-content: space-between;
    }
  }
}

.overflowOmit {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
