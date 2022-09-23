<template>
  <div class="music">
    <div>
      <Nav class="first"><b>车载音乐</b></Nav>
    </div>
    <div class="carousel">
      <el-carousel :interval="5000" arrow="always" style="width: 95vw">
        <el-carousel-item v-for="(item, index) in banners" :key="index">
          <img
            :src="item.imageUrl"
            class="image"
            @click="goMusicList(item.id)"
          />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="title">
      <h2>「享受」Music 伴你出行</h2>
      <h3>标签：车载音乐</h3>
    </div>
    <div class="content">
      <div
        v-for="(item, index) in recommends"
        :key="index"
        class="item"
        @click="goMusicList(item.id)"
      >
        <img :src="item.picUrl" class="image" />
        <div class="con-wrap">
          <span class="con">{{ item.name }}</span>
          <div class="bottom clearfix"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Nav from "../../components/Nav.vue";
export default {
  components: { Nav },
  data() {
    return {
      recommends: [],
      banners: [],
    };
  },
  async mounted() {
    await this.getMusicData();
    await this.getBanners();
  },
  methods: {
    goMusicList(Id) {
      this.$router.push({ path: "/musicList", query: { Id } });
    },
    async getMusicData() {
      // dev env
      // let { code, recommend } = await axios.get(
      //   "http://localhost:8080/api2/recommend/resource"
      // );

      // 正式env
      let { code, recommend } = await this.$axios.get(
        "/api2/recommend/resource"
      );

      if (code == 200) {
        this.recommends = recommend;
      } else {
        this.$Message("发生错误");
      }
    },
    async getBanners() {
      this.banners = [
        {
          imageUrl:
            "https://img1.baidu.com/it/u=2188933799,3530328458&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=819",
          id: 5017809011,
        },
        {
          imageUrl:
            "https://img0.baidu.com/it/u=2048792674,926910609&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          id: 6785387087,
        },
        {
          imageUrl:
            "https://img2.baidu.com/it/u=4134846078,2933381367&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          id: 24677256,
        },
        {
          imageUrl:
            "https://img1.baidu.com/it/u=70971664,2501431871&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=708",
          id: 4907126777,
        },
      ];
    },
  },
};
</script>
<style lang="less" scoped>
.music {
  width: 100vw;
  height: 900px;
  background-color: #f3f4f4;
  overflow: auto;
  padding: 50px 0;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.carousel {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.title {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 10px;
}
.content {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  .item {
    margin: 2px 2px;
    width: 100px;
    height: 150px;
    display: flex;
    flex-direction: column;
    background-color: #fafafa;
    padding: 8px 8px;
    border-radius: 2px;
    .con {
      overflow: hidden;
      overflow-wrap: break-word;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
    }
  }
}
.first {
  position: fixed;
  top: 0;
  z-index: 1000;
}
</style>
