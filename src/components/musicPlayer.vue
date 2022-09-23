<template>
  <div class="musicPlayer">
    <audio
      ref="audio"
      :src="questionAudio"
      :name="12"
      @ended="audioEnd"
      controls
      class="audio"
      @play="emitOuter('startMusic')"
      @pause="emitOuter('stopMusic')"
    ></audio>
  </div>
</template>
<script>
export default {
  name: "musicPlayer",
  props: ["questionAudio"],
  data() {
    return {};
  },
  methods: {
    handleplay() {
      setTimeout(() => {
        this.$refs.audio.play();
      }, 100);
    },
    audioEnd() {
      setTimeout(() => {
        this.$refs.audio.pause();
      }, 100);
      this.$emit("stopMusic");
    },
    emitOuter(str) {
      this.$emit(str);
    },
  },
};
</script>
<style lang="less" scoped>
.musicPlayer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
