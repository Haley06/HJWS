<template>
  <div class="cart">
    <Nav>购物车</Nav>
    <van-empty
      description="暂无地址"
      image="https://img01.yzcdn.cn/vant/custom-empty-image.png"
      image-size="80"
      class="empty"
      v-if="isHaveCart"
    >
      <van-button
        round
        type="danger"
        class="bottom-button"
        @click="console.log(1)"
        >去购物</van-button
      >
    </van-empty>
    <div class="cartList" v-if="!isHaveCart">
      <CartCard
        v-for="(item, index) in cartList"
        :key="index"
        :goodsImg="item.picture"
        :goodsName="item.name"
        :goodsAmount="item.amount"
        :goodsPrice="item.price"
        :goodsDesc="item.description"
        @deleteCart="deleteCart(item.id)"
      ></CartCard>
    </div>
    <van-dialog
      v-model="deleteShow"
      title="标题"
      show-cancel-button
      @confirm="confirmCart"
      @cancel="deleteShow = false"
    >
      你确定要从购物车中删除这个商品吗
    </van-dialog>
    <van-submit-bar
      :price="price"
      button-text="提交订单"
      @submit="onSubmit"
      v-if="!isHaveCart"
      safe-area-inset-bottom
    />
  </div>
</template>

<script>
import Nav from "../../../components/Nav.vue";
import CartCard from "./cartCard.vue";

export default {
  components: {
    Nav,
    CartCard,
  },
  data() {
    return {
      cartList: [],
      deleteShow: false,
      cartId: 0,
      price: 0,
    };
  },
  methods: {
    async getAllCart() {
      const data = await this.$axios.get("/api1/shop/getAllCart");
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.cartList = data.carts;
      this.cartList.forEach((item) => {
        this.price += item.price * item.amount;
      });
      this.price = this.price * 100;
    },
    async deleteCart(id) {
      this.deleteShow = true;
      this.cartId = id;
      return;
    },
    async confirmCart() {
      this.deleteShow = false;
      const data = await this.$axios.post("/api1/shop/removeCart", {
        id: this.cartId,
      });
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.getAllCart();
    },
    onSubmit() {},
  },
  mounted() {
    this.getAllCart();
  },
  computed: {
    isHaveCart() {
      if (this.cartList.length === 0) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style scoped lang="less">
.cart {
  height: 100%;
  background-color: #f5f5f5;
}
.empty {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .van-empty__description {
    padding: 0px;
  }
  .bottom-button {
    padding: 0 25px;
  }
}
.cartCard {
  margin-top: 2vh;
}
::v-deep .van-dialog__content {
  height: 10vh;
  line-height: 10vh;
  text-align: center;
}
</style>
