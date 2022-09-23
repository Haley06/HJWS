<template>
  <div class="chat" ref="chat">
    <Nav class="nickname"
      ><b>{{ sendName }}</b></Nav
    >
    <div class="chat-con">
      <div v-for="(item, i) in result" :key="i">
        <div class="time">{{ item.createTime.substring(5, 16) }}</div>
        <div class="list" v-if="!item.remark">
          <div class="message">{{ item.content }}</div>
          <div class="Avatar">
            <img :src="item.senderAvatar" /><span class="name"></span>
          </div>
        </div>
        <div class="listLeft" v-else>
          <div class="AvatarLeft">
            <img :src="item.senderAvatar" /><span class="nameLeft"></span>
          </div>
          <div class="messageLeft" v-if="!(item.content.search(appear) !== -1)">
            <span class="con">{{ item.content }}</span>
          </div>
          <chat-apply
            v-if="item.content.search(appear) !== -1"
            :content="item.content"
            :monitoredId="item.monitoredId"
          ></chat-apply>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-input v-model="input" placeholder="请输入内容" class="input">
      </el-input>
      <el-button
        style="background-color: #beaee6; border: #ffffff"
        type="success"
        round
        @click="sendMessage"
        >发送</el-button
      >
      <div class="emojiList" v-if="emoMore">
        <span v-for="(item, index) in emojis" :key="index">
          {{ item.text }}
        </span>
      </div>
    </div>
  </div>
</template>
<script>
import chatApply from "../../../components/chatApply.vue";
import Nav from "../../../components/Nav.vue";
import formatDate from "../../../utils/utilsFunc";
export default {
  components: { chatApply, Nav },
  data() {
    return {
      input: "",
      Id: 0,
      result: [],
      emoMore: false,
      appear: "申请关注",
      sendName: "",
      emojis: [
        "😀",
        "😄",
        "😅",
        "🤣",
        "😂",
        "😉",
        "😊",
        "😍",
        "😘",
        "😜",
        "😝",
        "😏",
        "😒",
        "🙄",
        "😔",
        "😴",
        "😷",
        "🤮",
        "🥵",
        "😎",
        "😮",
        "😰",
        "😭",
        "😱",
        "😩",
        "😡",
        "💀",
        "👽",
        "🤓",
        "🥳",
        "😺",
        "😹",
        "😻",
        "🤚",
        "💩",
        "👍",
        "👎",
        "👏",
        "🙏",
        "💪",
      ],
      item: {},
    };
  },
  created() {
    this.getParams();
  },
  async mounted() {
    await this.getAll();
    this.emojis = this.emojis.map((emoji) => ({ text: emoji }));
    // 保证一旦进入页面到页面底部
    this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight;
  },
  methods: {
    formatDate,
    async getAll() {
      let { result } = await this.$axios.post("/api1/message/chat", {
        chatUserId: this.Id,
      });
      this.result = result;
    },
    getParams() {
      this.Id = this.$route.query.Id;
      this.item = this.$route.query.item;
      this.sendName = this.item.nickname
        ? this.item.nickname
        : window.localStorage.getItem("name");
      window.localStorage.setItem("name", this.sendName);
    },
    async sendMessage() {
      var time = new Date();
      time = this.formatDate(time, "yyyy-MM-dd hh:mm:ss");
      let { code } = await this.$axios.post("/api1/message/send", {
        accepterId: this.Id,
        content: this.input,
        createTime: time,
      });
      if (code == 0) {
        this.input = "";
        this.getAll();
        this.$router.go(0);
      }
    },
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>
<style lang="less" scoped>
#app {
  padding-bottom: 50px !important;
}
.chat {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  background-color: #fafbfe;
  overflow-x: hidden;
  overflow-y: auto;
  padding-bottom: 40px;
  .nickname {
    position: fixed;
    top: 0;
  }
}

.footer {
  width: 100%;
  background-color: #ffffff;
  position: fixed;
  z-index: 1000;
  bottom: 0;
  .input {
    width: 70%;
    margin: 5px 10px;
  }
  .clear {
    clear: both;
  }
}
.chat-con {
  padding-top: 60px;
  // padding-bottom: 40px;
  background-color: #fafbfe;
  .list {
    height: 60px;
    margin: 10px 10px 20px 10px;
    border-radius: 10px;
    font-size: 15px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    img {
      border-radius: 30px;
      margin-left: 8px;
    }

    .message {
      border-radius: 40px 0px 40px 40px;
      background-color: #eaf1ff;
      display: inline-block;
      word-wrap: break-word;
      padding: 10px 20px;
      width: 65%;
    }
  }
  .listLeft {
    height: 60px;
    margin: 25px 10px;
    margin-top: 15px;
    border-radius: 10px;
    font-size: 15px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    img {
      border-radius: 30px;
    }
    .messageLeft {
      border-radius: 0 40px 40px 40px;
      background-color: #dff7f9;
      display: inline-block;
      margin: 10px 0 0 7px;
      word-wrap: break-word;
      padding: 10px 30px;
      font-size: 14px;
      width: 55%;
    }
  }
}
.time {
  text-align: center;
  font-size: 10px;
  color: rgb(81, 77, 81);
  padding-top: 10px;
}

.emojiList {
  display: flex;
  flex-wrap: wrap;
  height: 100px;
  justify-content: space-between;
}
.Avatar {
  img {
    width: 40px;
    height: 40px;
  }
}
.AvatarLeft {
  img {
    width: 40px;
    height: 40px;
  }
}
.applyButton {
  width: 50px;
  height: 20px;
  padding: 2px;
  margin: 8px 15px;
}
</style>
