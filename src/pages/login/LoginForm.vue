<template>
  <div class="loginForm">
    <div class="form-title">
      <div @click="changeType" :class="{ check: !type }">登录</div>
      <div @click="changeType" :class="{ check: type }">手机验证</div>
    </div>
    <div class="form">
      <el-input v-model="mobile" placeholder="电话"></el-input>
      <el-input
        v-model="password"
        v-if="!type"
        placeholder="密码"
        :show-password="true"
        key="password"
      ></el-input>
      <div class="code" v-else key="code">
        <el-input v-model="code" placeholder="验证码"> </el-input>
        <button class="get-code" @click="getCode">
          {{ isCodeInterval ? countDownTime + "s" : "验证码" }}
        </button>
      </div>
      <el-button type="primary" @click="onsubmit">登录</el-button>
      <span class="register" @click="goRegister">注册账户</span>
    </div>
  </div>
</template>

<script>
import Nav from "../../components/Nav";

export default {
  name: "LoginForm",
  data() {
    return {
      mobile: "13734174131",
      password: "123456",
      // uuid 是什么？
      uuid: 0,
      code: "",
      type: 0, //0为密码
      isCodeInterval: 0,
      countDownTime: 60,
    };
  },
  methods: {
    onsubmit() {
      if (Boolean(this.type) === false) {
        this.$axios
          .post(this.$url.login.path, {
            mobile: this.mobile,
            password: this.password,
          })
          .then((res) => {
            window.localStorage.setItem("token", res.token);
            let str = this.$dsbrige.call("setToken", res.token);
            // 将手机号保存在vuex中
            this.$store.commit("setMobile", this.mobile);
            this.$router.push("/profile");
          });
      } else {
        this.$axios
          .post(this.$url.postVercode.path, {
            mobile: this.mobile,
            uuid: this.uuid || this.mobile * 2 + 1,
            code: this.code,
          })
          .then((res) => {
            //js_bridge通信
            this.$store.commit("setMobile", this.mobile);
            this.$router.push("/profile");
          });
      }
    },
    // 跳转到注册页面
    goRegister() {
      this.$router.push("/register");
    },
    // 改变登录的方式
    changeType() {
      this.type = !this.type;
      console.log(this.type);
    },
    // 获取验证码
    async getCode() {
      this.uuid = this.mobile * 2 + 1;
      const data = await this.$axios.get(this.$url.getVercode.path, {
        params: {
          mobile: this.mobile,
          uuid: this.uuid,
        },
      });
      console.log(data);
      this.countDown();
    },
    // 倒计时
    countDown() {
      this.isCodeInterval = 1;
      let clock = window.setInterval(() => {
        this.countDownTime--;
        if (this.countDownTime === 0) {
          window.clearInterval(clock);
          this.countDownTime = 60;
          this.isCodeInterval = 0;
        }
      }, 1000);
    },
  },
  components: {
    Nav,
  },
};
</script>

<style scoped lang="less">
.loginForm {
  height: 100vh;
  padding-top: 60px;
  .flex_init(column, flex-start);

  .form-title {
    .letter(#9981b8, 30px, 450, 4px);
    height: 40px;
    align-self: flex-start;
    padding-top: 6vh;
    padding-left: 20px;
    display: flex;
    flex-direction: row;
    align-items: flex-end;

    > div {
      font-size: 25px;
      margin-right: 25px;
      transition: 0.3s ease-in-out font-size;
    }

    > div.check {
      font-size: 30px !important;
      border-bottom: 3.5px solid #9981b8;
      transition: 0.3s ease-in-out font-size;
    }
  }

  .form {
    flex-grow: 1;
    .flex_init(column, flex-start, center);
    width: 100%;
    margin-top: 40px;

    /deep/ .el-input {
      width: 90%;
      margin: 15px auto;
      transition: all ease-in-out 0.3s;

      input {
        border-radius: 12px;
        height: 50px;
        .letter(@color:#828282, @font-size: 20px, @letter-spacing: 2px);
      }
    }

    /deep/ .el-button {
      margin-top: 120px;
      min-width: 50%;
      background-color: #9981b8;
      border-radius: 20px;
      border: none;

      span {
        font-size: 17px;
        letter-spacing: 2px;
        font-weight: 350;
        text-indent: 2px;
      }
    }

    .code {
      width: 100%;
      position: relative;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .get-code {
        width: 80px;
        height: 35px;
        line-height: 35px;
        border-radius: 16px;
        border: none;
        background-color: #b2a4d0;
        color: #f5f5f5;
        letter-spacing: 2px;
        text-indent: 2px;
        position: absolute;
        right: 8%;
        top: 23px;
      }
    }

    .register {
      position: absolute;
      text-align: center;
      bottom: 5vh;
      color: #606266;
      border-bottom: solid 1px gray;
    }

    .register:hover {
      color: #9981b8;
      border-bottom: solid 1px #9981b8;
    }
  }
}
</style>
