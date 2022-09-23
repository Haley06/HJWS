<template>
  <div class="orderFrom">
    <Nav>订单</Nav>
    <van-empty
      description="暂无订单"
      image="https://img01.yzcdn.cn/vant/custom-empty-image.png"
      image-size="80"
      class="empty"
      v-show="isHaveOrder"
    />
    <div class="orderList">
      <OrderCard
        v-for="(item, index) in orderList"
        :key="index"
        :img="item.picture"
        :orderName="item.name"
        :price="item.price"
      ></OrderCard>
    </div>
  </div>
</template>

<script>
import NavVue from "../../../components/Nav.vue";
import OrderCard from "./orderCard.vue";
import Nav from "../../../components/Nav.vue";

export default {
  data() {
    return {
      orderList: [],
    };
  },
  components() {
    orderCard;
    NavVue;
  },
  methods: {
    async getAllOrder() {
      const data = await this.$axios.get("/api1/order/getAll");
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.orderList = data.order;
      this.$Message("获取成功", "success");
    },
  },
  created() {
    this.getAllOrder();
  },
  components: { OrderCard, Nav },
  computed: {
    isHaveOrder() {
      console.log(this.orderList.length);
      if (this.orderList.length === 0) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style scoped lang="less">
.orderFrom {
  height: 100%;
  background-color: #f0f0f0;
  .orderList {
    padding-top: 10px;
  }
  .orderCard {
    width: 90%;
    margin: 0 auto;
  }
}
.empty {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .van-empty__description {
    padding: 0px;
  }
}
</style>
