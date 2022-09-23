<template>
  <div class="addAdress">
    <Nav>添加地址</Nav>
    <van-address-edit
      :area-list="areaList"
      show-search-result
      :search-result="searchResult"
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
    };
  },
  methods: {
    async onSave(content) {
      console.log(content);
      const data = await this.$axios.post("/api1/address/add", {
        consignee: content.name || "",
        phoneNumber: content.tel,
        address: content.province + content.city + content.county,
        detailedAddress: content.addressDetail,
        areaCode: content.areaCode,
      });
      if (data.code !== 0) {
        return this.$Message(data.msg);
      }
      this.$Message("上传成功", "success");
      this.$router.push("address");
    },
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
