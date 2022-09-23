<template>
  <div class="emergency">
    <van-empty v-if="emptyList" description="暂未设置" />
    <div v-else style="width: 100%">
      <div
        class="item"
        v-show="item.name != ''"
        v-for="(item, index) in phoneList"
        :key="index"
      >
        <div class="svg">
          <svg
            t="1626437362913"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="1399"
            width="200"
            height="200"
          >
            <path
              d="M673.216263 657.80476c5.39794 4.623297 12.344143 7.522324 20.016893 7.522324 16.870226 0 30.540573-13.467733 30.540573-30.037107 0.023536-9.957793-4.975315-18.7439-12.622482-24.216541 0.051165-0.112564 0.051165-0.249687 0.051165-0.337691-30.413684-22.503527-62.424748-37.985126-92.715635-48.579415 72.471568-44.010353 121.804137-121.340579 121.804137-212.357526 0-138.034796-114.081245-247.242034-252.853845-247.242034-138.745994 0-252.801656 110.757546-252.801656 248.767783 0 89.49222 47.781236 168.32159 120.252805 210.832799C146.768203 636.465756 128.275014 847.282183 128.275014 850.331635c-1.550309 16.668635 10.770298 31.826869 27.739784 31.826869 16.943904 1.523703 32.362059-10.597359 33.911344-27.314089 0-10.622942 23.115464-241.156432 294.412356-256.325923 6.77224 0 104.459097-3.560082 188.777481 59.328223C673.165098 657.831366 673.190681 657.80476 673.216263 657.80476zM487.43707 537.864064c-104.809067 0-191.125969-83.392292-191.125969-188.06526 0-104.658641 86.317924-186.566116 191.125969-186.566116 104.860233 0 191.152575 83.417875 191.152575 188.079586C678.589644 455.959659 592.297302 537.864064 487.43707 537.864064zM851.847663 641.136126c-5.148253-1.297552-10.497075-2.086521-16.144702-2.086521-32.987299 0-60.126402 27.427676-60.126402 61.376882 0 27.702945 18.142196 51.229778 42.73429 58.790988-18.443048 30.823006-43.809785 57.214073-74.021877 76.282361-9.196453-21.680788-30.438243-36.83493-55.279001-36.83493-33.236985 0-60.151985 27.41335-60.151985 61.388139 0 3.921309 0.249687 7.834432 1.299599 11.506055 5.122671 28.203342 29.164227 49.619094 58.103326 49.883107 16.491603 0 25.589818-4.386914 33.685193-7.312546 87.068008-31.499411 151.593604-105.084337 171.360809-195.397249 0.823761-3.924379 2.548032-12.133342 2.548032-18.306948C895.854946 672.224168 877.137653 648.460951 851.847663 641.136126z"
              p-id="1400"
              fill="#9981B8"
            ></path>
          </svg>
        </div>
        <div class="info">
          <div class="name">{{ item.name }}</div>
          <div class="number">{{ item.mobile }}</div>
        </div>
      </div>
    </div>
    <div class="edit">
      <button @click="showPopup">编辑联系人</button>
      <van-popup position="bottom" v-model="show">
        <van-form>
          <div
            class="phoneList"
            v-for="(item, index) in phoneList"
            :key="index"
          >
            <div class="text">
              联系人{{ index + 1 }}
              <span
                :class="{
                  show: validateArr[index] === 0,
                  hide: validateArr[index] !== 0,
                }"
                >请填写正确的信息</span
              >
            </div>
            <van-field
              v-model="item.name"
              name="姓名"
              label="姓名"
              placeholder="输入联系人名称"
            />
            <van-field
              v-model="item.mobile"
              name="联系方式"
              label="联系方式"
              placeholder="输入号码"
            />
          </div>
          <div style="margin: 16px">
            <van-button
              round
              block
              type="info"
              native-type="submit"
              @click="submit"
              >提交</van-button
            >
          </div>
        </van-form>
      </van-popup>
    </div>
  </div>
</template>

<script>
export default {
  name: "Emergency",
  data() {
    return {
      show: false,
      serverId: "",
      emptyList: true,
      phoneList: [{ name: "李鑫磊", mobile: "18920251111" }],
      validateArr: [1, 1, 1], //-1为未填写,1为正确填写,0为错误填写
    };
  },
  methods: {
    showPopup() {
      this.show = !this.show;
    },
    validate() {
      this.validateArr = [0, 0, 0];
      let isNotCorrect = false; //是否存在未填写项
      this.phoneList.forEach((item, index) => {
        let nameValidate = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
        let numberValidate = /^1[3|5|7|8]\d{9}$/;
        let testText =
          nameValidate.test(item.name) && numberValidate.test(item.mobile);
        let flag1 = item.name !== "" && item.mobile !== ""; //true->当前联系人2个输入栏不为空
        let flag2 = item.name === "" && item.mobile === ""; //true->当前联系人2个输入栏为空
        // console.log(item,testText,nameValidate.test(item.name),numberValidate.test(item.mobile),flag1,flag2)

        if (flag1) {
          //不为空需要验证
          if (!testText) {
            //验证失败
            isNotCorrect = true;
            this.$set(this.validateArr, index, 0); //标识位错误状态
          } else this.$set(this.validateArr, index, 1); //标识位正确状态
        }
        if (flag2) this.$set(this.validateArr, index, -1); //标识未填写状态
      });
      return !isNotCorrect; //返回true代表验证通过
    },
    async submit() {
      if (this.validate()) {
        //进行填写校验
        let { data } = await this.$axios.create().post("/api1/postInfo", {
          mobile: this.serverId,
          info: this.phoneList,
        });
        if (data.hasOwnProperty("state") && data.state === true) {
          this.showPopup();
          vant.Notify({
            message: "提交成功",
            type: "success",
          });
        } else {
          vant.Toast({
            message: "提交失败",
            icon: "user-circle-o",
          });
        }
      }
    },
  },
  async mounted() {
    this.emptyList = Boolean(!this.phoneList.length);
    this.serverId = (await this.$axios.get("/api1/userInfo")).user.mobile;
    let { data } = await axios.create().get("/api1/getInfo", {
      params: {
        mobile: this.serverId,
      },
    });
    this.phoneList = [...data];
  },
};
</script>

<style scoped lang="less">
.text {
  padding: 15px 16px;
  letter-spacing: 2px;

  .show {
    font-size: 13px;
    color: red;
    margin-left: 28px;
    letter-spacing: 0;
  }

  .hide {
    visibility: hidden;
  }
}

.emergency {
  width: 100%;
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;
  justify-self: flex-start;
  align-items: center;
  background-color: #f5f5f5;

  .item:nth-child(1) {
    margin-top: 10px;
  }

  .item {
    width: 100%;
    height: 90px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    background-color: #fff;

    .svg {
      width: 80px;
      overflow: hidden;
      margin-left: 20px;

      svg {
        position: relative;
        top: 1px;
        right: 60px;
        height: 50px;
      }
    }

    .info {
      margin-left: 35px;
      height: 50px;
      display: flex;
      flex-direction: column;
      justify-content: space-around;

      .name {
        color: #212020;
        letter-spacing: 2px;
      }

      .number {
        color: rgba(155, 155, 155, 0.84);
      }
    }
  }

  .edit {
    margin-top: 120px;

    button {
      background-color: #fff;
      border: 1px solid rgba(153, 129, 184, 0.95);
      color: rgba(153, 129, 184, 0.95);
      padding: 8px 25px;
      border-radius: 20px;
      letter-spacing: 2px;
      text-indent: 2px;
      outline: none;
    }

    > button {
      width: 60vw;
    }
  }
}
</style>
