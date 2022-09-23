<template>
  <div class="feedback">
    <div class="feedback-type-box">
      <div class="type-title title">请选择反馈类型</div>
      <div class="type-item-box">
        <div
          :class="{
            'type-items': true,
            'type-items-active': isClickType[index],
          }"
          v-for="(item, index) in types"
          :key="item"
          @click="onClickType(index)"
        >
          <div class="items-icon" v-if="isClickType[index]">
            <svg
              t="1628734536826"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="2220"
              width="200"
              height="200"
            >
              <path
                d="M486.4 630.4c-19.2 19.2-48 19.2-67.2 3.2l-137.6-131.2-32 35.2 137.6 131.2c38.4 35.2 96 35.2 134.4-3.2l281.6-297.6-35.2-32L486.4 630.4z"
                p-id="2221"
                fill="#ffffff"
              ></path>
              <path
                d="M512 51.2c-252.8 0-460.8 204.8-460.8 460.8s204.8 460.8 460.8 460.8 460.8-204.8 460.8-460.8S764.8 51.2 512 51.2zM512 924.8c-227.2 0-412.8-185.6-412.8-412.8s185.6-412.8 412.8-412.8 412.8 185.6 412.8 412.8S739.2 924.8 512 924.8z"
                p-id="2222"
                fill="#ffffff"
              ></path>
            </svg>
          </div>
          <div class="items-icon" v-else>
            <svg
              t="1628734536826"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="2220"
              width="200"
              height="200"
            >
              <path
                d="M486.4 630.4c-19.2 19.2-48 19.2-67.2 3.2l-137.6-131.2-32 35.2 137.6 131.2c38.4 35.2 96 35.2 134.4-3.2l281.6-297.6-35.2-32L486.4 630.4z"
                p-id="2221"
                fill="#8a8a8a"
              ></path>
              <path
                d="M512 51.2c-252.8 0-460.8 204.8-460.8 460.8s204.8 460.8 460.8 460.8 460.8-204.8 460.8-460.8S764.8 51.2 512 51.2zM512 924.8c-227.2 0-412.8-185.6-412.8-412.8s185.6-412.8 412.8-412.8 412.8 185.6 412.8 412.8S739.2 924.8 512 924.8z"
                p-id="2222"
                fill="#8a8a8a"
              ></path>
            </svg>
          </div>
          <div class="items-tag">{{ item }}</div>
        </div>
      </div>
    </div>
    <div class="feedback-content">
      <div class="content-title title">请输入手机号码</div>
      <div class="content-form">
        <input
          type="text"
          v-model="list[clickIndex].mobile"
          class="form-border"
          placeholder="请输入手机号码,我们将您核实反馈"
        />
        <div class="form-border">
          <textarea
            class="form-border"
            v-model="list[clickIndex].content"
            placeholder="在此输入您遇见的问题,很抱歉给您带来不好的体验,我们将尽快与您联系."
          ></textarea>
        </div>
        <button class="form-border" @click="onsubmit()">提交反馈</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Feedback",
  data() {
    return {
      types: [
        "功能问题",
        "页面卡顿",
        "服务问题",
        "导航问题",
        "疲劳识别",
        "其他问题",
      ],
      isClickType: [1, 0, 0, 0, 0, 0],
      clickIndex: "0",
      list: [
        { mobile: "", content: "" },
        { mobile: "", content: "" },
        { mobile: "", content: "" },
        { mobile: "", content: "" },
        { mobile: "", content: "" },
        { mobile: "", content: "" },
        { mobile: "", content: "" },
      ],
    };
  },
  methods: {
    onClickType(index) {
      this.isClickType.forEach((item1, index1) => {
        if (index === index1) {
          this.$set(this.isClickType, index1, 1);
          this.clickIndex = index;
        } else this.$set(this.isClickType, index1, 0);
      });
    },
    onsubmit() {
      let numberValidate = /^1[3|5|7|8]\d{9}$/;
      console.log(
        this.list[this.clickIndex].mobile,
        this.list[this.clickIndex].content !== ""
      );
      if (
        numberValidate.test(this.list[this.clickIndex].mobile) &&
        this.list[this.clickIndex].content !== ""
      ) {
        vant.Notify({
          message: "提交成功",
          type: "success",
        });
        setTimeout(() => {
          this.$router.back(-1);
        }, 100);
      } else {
        vant.Notify({
          message: "请正确填写信息",
          type: "warning",
        });
      }
    },
  },
};
</script>

<style scoped lang="less">
.title {
  font-size: 15px;
  margin: 8px 0;
  letter-spacing: 2px;
  font-weight: 500;
  text-indent: 1px;
}
.feedback {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  .feedback-type-box {
    margin-top: 20px;
    width: 90%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    .type-item-box {
      width: 100%;
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      flex-wrap: wrap;
      .type-items-active {
        background-color: rgba(153, 129, 184, 0.4) !important;
        color: #fff !important;
      }
      .type-items {
        width: 30%;
        height: 30px;
        margin: 10px 10px 10px auto;
        border-radius: 6px;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        color: #afafb1;
        background-color: #f6f5fa;
        .items-icon {
          margin-right: 4px;
          svg {
            position: relative;
            top: 2px;
            width: 20px;
            height: 20px;
          }
        }
        .items-tag {
          margin-left: 4px;
          letter-spacing: 1px;
          text-indent: 1px;
        }
      }
    }
  }
  .feedback-content {
    width: 90%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    .content-form {
      height: 260px;
      width: 100%;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: flex-start;
      .form-border {
        border: none;
        resize: none;
        box-sizing: border-box;
        outline: none;
        border-radius: 6px;
        background-color: #f6f5fa;
      }
      input {
        width: 100%;
        height: 40px;
        padding: 5px 12px;
        //color:#F5F5F5;
      }
      div {
        padding: 8px 12px;
        width: 100%;
        height: 120px;
      }
      button {
        align-self: center;
        position: relative;
        top: 20px;
        width: 100%;
        height: 45px;
        color: #fff;
        letter-spacing: 3px;
        text-indent: 3px;
        background-color: rgba(153, 129, 184, 0.8) !important;
      }
      textarea {
        width: 100%;
        height: 100%;
      }
    }
  }
}
input::-webkit-input-placeholder {
  color: #d5d5d9;
  letter-spacing: 1px;
}
input::-moz-placeholder {
  /* Mozilla Firefox 19+ */
  color: #d6d6d8;
  letter-spacing: 1px;
}
input:-moz-placeholder {
  /* Mozilla Firefox 4 to 18 */
  color: #d6d6d8;
  letter-spacing: 1px;
}
input:-ms-input-placeholder {
  /* Internet Explorer 10-11 */
  color: #d6d6d8;
  letter-spacing: 1px;
}
textarea::-webkit-input-placeholder {
  color: #d6d6d8;
  letter-spacing: 1px;
}
textarea::-moz-placeholder {
  /* Mozilla Firefox 19+ */
  color: #d6d6d8;
  letter-spacing: 1px;
}
textarea:-moz-placeholder {
  /* Mozilla Firefox 4 to 18 */
  color: #d6d6d8;
  letter-spacing: 1px;
}
textarea:-ms-input-placeholder {
  /* Internet Explorer 10-11 */
  color: #d6d6d8;
  letter-spacing: 1px;
}
</style>