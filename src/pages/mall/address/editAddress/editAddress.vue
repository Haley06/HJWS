<template>
  <div class="addAdress">
    <Nav>修改地址</Nav>
    <van-address-edit
      :area-list="areaList"
      show-search-result
      :addressInfo="data"
      :area-columns-placeholder="['请选择', '请选择', '请选择']"
      @save="onSave"
    />
  </div>
</template>

<script>
import Nav from "../../../../components/Nav.vue";
import { areaList } from "@vant/area-data";
export default {
  components: { Nav },
  data() {
    return {
      areaList,
      searchResult: [],
      data: {},
    };
  },
  methods: {
    async onSave(content) {
      const data = await this.$axios.post("/api1/address/update", {
        id: content.id,
        consignee: content.name || "",
        phoneNumber: content.tel,
        address: content.province + content.city + content.county,
        detailedAddress: content.addressDetail,
        areaCode: content.areaCode,
      });
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.$Message("修改成功", "success");
    },
  },
  created() {
    let address = this.$route.query.item;
    this.data = address;
    this.data.addressDetail = address.detailedAddress;
    let reg =
      /(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)/;
    let addressStr = this.data.address;
    let list = addressStr.match(reg);
    this.data.province = list[1];
    this.data.city = list[2];
    this.data.county = list[3];
    this.data.areaCode = address.areaCode;
    console.log(this.data);
  },
};
</script>

<style scoped lang="less">
.addAdress {
  background-color: #f8f8f8;
  height: 100%;
  .van-address-edit {
    margin-top: 20px;
  }
}
</style>
