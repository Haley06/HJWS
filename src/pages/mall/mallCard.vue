<template>
  <div class="shopsCard">
    <img :src="goodsImg" alt="" />
    <div class="goodsInfo">
      <div class="goodsName">{{ goodsName }}</div>
      <div>
        <div class="goodsPrice"><span>￥</span>{{ price }}/件</div>
        <div v-show="isHave">
          <span>进入抢购</span> <van-icon name="arrow" size="12" />
        </div>
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
    isHave: {
      type: Boolean,
      defaultd: true,
    },
    goodsName: {
      type: String,
      required: true,
    },
    price: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {};
  },

  methods: {},
  mounted() {},
};
</script>

<style scoped lang="less">
.shopsCard {
  display: inline-block;
  width: 42vw;
  border-radius: 3vw;
  box-shadow: 0px 0px 1px rgba(0, 0, 0, 0.4);
  background-color: #fff;
  img {
    width: 42vw;
    height: 42vw;
    border-bottom: 1px solid rgb(161, 156, 156);
  }
}
.goodsInfo {
  div {
    &:nth-child(2) {
      display: flex;
      justify-content: space-between;
      margin: 0.6vh 2vw;
      align-items: center;
      .van-icon {
      }
      div {
        display: flex;
        align-items: center;
        span {
          font-size: 12px;
          color: #8b8585;
        }
      }
    }
  }
}
.goodsName {
  overflow: hidden;
  white-space: nowrap;
  width: 37vw;
  margin: 0 auto;
  text-overflow: ellipsis;
  font-size: 13px;
  min-width: 0;
}
.goodsPrice {
  span {
    color: red;
    font-size: 12px;
  }
  font-size: 14px;
  color: red;
}
</style>
