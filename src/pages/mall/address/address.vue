<template>
  <div class="address">
    <nav-vue>地址管理</nav-vue>
    <van-empty
      description="暂无地址"
      image="https://img01.yzcdn.cn/vant/custom-empty-image.png"
      image-size="80"
      class="empty"
      v-show="!isHaveAddress"
    >
      <van-button round type="danger" class="bottom-button" @click="onAdd"
        >新增地址</van-button
      >
    </van-empty>
    <div class="list" v-show="isHaveAddress">
      <van-address-list
        v-model="chosenAddressId"
        :list="list"
        @add="onAdd"
        @edit="onEdit"
      >
        <template slot="item-bottom">
          <van-icon name="delete-o" size="20" @click="deleteAddress()" />
        </template>
      </van-address-list>
    </div>
  </div>
</template>

<script>
import NavVue from "../../../components/Nav.vue";
import { Dialog } from "vant";
export default {
  data() {
    return {
      chosenAddressId: "0",
      list: [],
    };
  },
  components: {
    NavVue,
  },
  methods: {
    onAdd() {
      this.$router.push("addAddress");
      console.log("新增地址");
    },
    onEdit(item, index) {
      this.$router.push({
        path: "/editAddress",
        query: {
          item,
        },
      });
      console.log(item);
      console.log("编辑地址:" + index);
    },
    async getAllAddress() {
      const data = await this.$axios.get("/api1/address/getAll");
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.list = data.address || [];
      let addressList = data.address || [];
      addressList.forEach((item) => {
        item.name = item.consignee;
        item.tel = item.phoneNumber;
        item.address = item.address + item.detailedAddress;
      });
    },
    deleteAddress() {
      Dialog.confirm({
        title: "地址删除",
        message: "确认删除此地址吗,删除后不可恢复。",
      })
        .then(async () => {
          const data = await this.$axios.post("/address/remove", {
            id: this.chosenAddressId,
          });
          if (data.code !== 0) {
            return this.$Message(data.msg);
          }
          this.$Message("删除成功", "successs");
          console.log(data);
          this.getAllAddress();
        })
        .catch(() => {});
    },
  },
  created() {
    this.getAllAddress();
  },
  computed: {
    isHaveAddress() {
      if (this.list.length !== 0) {
        return true;
      } else {
        return false;
      }
    },
  },
  watch: {},
};
</script>

<style scoped lang="less">
.address {
  height: 100%;
  background-color: #f7f8fa;
}
.bottom-button {
  width: 160px;
  height: 40px;
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
.van-address-item {
  display: flex;
  align-items: center;
}
::v-deep .van-radio__icon--round {
  display: none;
}
</style>
