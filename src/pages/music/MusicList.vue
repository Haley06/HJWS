<template>
  <div class="musiclist">
    <div>
      <Nav class="first">歌曲列表</Nav>
    </div>
    <van-list
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      class="list"
    >
      <van-cell v-for="(item, index) in tracks" :key="index" class="cell">
        <span class="num" v-text="index < 10 ? '0' + index : index"></span>
        <div class="name" v-if="index == 0">
          <span> {{ item.name }} </span>
        </div>
        <div class="name" v-else>
          <span> {{ item.name }} </span>
          <van-icon
            name="play"
            v-if="!item.isPlay"
            @click="playMusic(item.id, index)"
          />
          <van-icon name="pause" v-else @click="stopMusic(index)" />
        </div>
        <span class="singer"> {{ item.ar[0].name }} </span>
      </van-cell>
    </van-list>
    <footer>
      <music-player
        @stopMusic="closeMusic"
        @startMusic="startMusic"
        ref="music"
        :questionAudio="musicUrl"
      ></music-player>
    </footer>
  </div>
</template>
<script>
import musicPlayer from "../../components/musicPlayer.vue";
import Nav from "../../components/Nav.vue";

export default {
  components: { musicPlayer, Nav },
  data() {
    return {
      loading: false,
      finished: false,
      tracks: [],
      musicUrl: "",
      curIndex: 1234,
      oldIndex: 1234,
    };
  },
  mounted() {
    this.getDetailMusicList();
  },
  methods: {
    async playMusic(musicId, index) {
      // dev env
      // let { data, code } = await axios.get(
      //   `http://localhost:8080/api2/song/url?id=` + musicId
      // );

      // formal env
      let { data, code } = await this.$axios.get(
        `/api2/song/url?id=` + musicId
      );
      this.oldIndex = this.curIndex;
      if (code == 200) {
        this.curIndex = index;
        this.musicUrl = data[0].url;
        this.tracks[this.curIndex].isPlay = true;
        if (this.oldIndex !== 1234 && this.curIndex !== this.oldIndex) {
          this.tracks[this.oldIndex].isPlay = false;
        }
        this.$refs.music.handleplay();
      }
    },
    closeMusic() {
      this.tracks[this.curIndex].isPlay = false;
    },
    startMusic() {
      this.tracks[this.curIndex].isPlay = true;
    },
    stopMusic() {
      this.tracks[this.curIndex].isPlay = false;
      this.$refs.music.audioEnd();
    },
    async getDetailMusicList() {
      let musicListId = this.$route.query.Id;
      // dev env
      // let { code, playlist } = await axios.get(
      //   `/api2/playlist/detail?id=` + musicListId
      // );

      // formal env
      let { code, playlist } = await this.$axios.get(
        `/api2/playlist/detail?id=` + musicListId
      );
      if (code == 200) {
        this.tracks = playlist.tracks;
        this.tracks.unshift({ name: "歌名", ar: [{ name: "歌手" }] });
        this.tracks = this.tracks.map((item) => {
          return { ...item, isPlay: false };
        });
      } else {
        this.$Message("发生错误");
      }
    },
  },
};
</script>
<style lang="less" scoped>
footer {
  background-color: #ffffff;
  position: fixed;
  bottom: 0;
  width: 100%;
  z-index: 99;
}
.musiclist {
  background-color: #fafafa;
  overflow: auto;
}
.van-cell__value {
  display: flex;
  // justify-content: flex-start;

  .num,
  .name,
  .singer {
    margin-right: 20px;
    color: #a692bd;
    flex: 0.6;
  }
  .num {
    padding-left: 10px;
    flex: 0.2;
  }
  .singer {
    flex: 0.5;
  }
}
.cell {
  font-size: 17px;
  background-color: #ffffff;
  padding: 15px;

  .name {
    color: #633b91;
  }
}
.list {
  margin-top: 70px;
}
.first {
  position: fixed;
  top: 0;
  z-index: 100;
}
</style>
