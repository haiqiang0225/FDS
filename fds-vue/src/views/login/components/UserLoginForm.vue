<template>
  <div class="login-box-container glass">

    <!--登陆框-->
    <div class="login-box">
      <h2 class="box-title">FDS 故障诊断系统</h2>
      <h3 class="login-box-item" style="margin: 0 0 0 .9rem">用户登录</h3>


      <el-form ref="loginRef" :model="loginForm" :rules="loginRules">
        <!--    用户名    -->
        <el-form-item prop="username">
          <el-input
              class="login-box-item"
              placeholder="请输入用户名/手机号/邮箱"
              v-model="loginForm.username"
              maxlength="20"
              clearable
          >
            <template #prepend>
              <font-awesome-icon icon="fa-solid fa-user"/>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password" v-show="!verifyCodeMode">
          <!--    密码    -->
          <el-input class="login-box-item"
                    placeholder="请输入密码"
                    v-model="loginForm.password"
                    maxlength="64"
                    type="password"
                    show-password
                    clearable
          >
            <template #prepend>
              <font-awesome-icon icon="fa-solid fa-lock"/>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="verifyCode" v-show="verifyCodeMode">
          <!--验证码-->
          <el-row class="login-box-item">
            <el-input class="login-input"
                      placeholder="请输入验证码"
                      v-model="loginForm.verifyCode"
                      maxlength="6"
            >
              <template #prepend>
                <font-awesome-icon icon="fa-solid fa-shield"/>
              </template>
              <template #append>
                <el-button size="large" plain @click="sendVerifyCode" :disabled="sendVerifyCondeCollingTime !== 0">
                  {{ sendVerifyCondeCollingTime === 0 ? "发送验证码" : sendVerifyCondeCollingTime + "秒" }}
                </el-button>
              </template>
            </el-input>
          </el-row>
        </el-form-item>

        <!--保持登陆/记住密码/注册/找回-->
        <el-row class="login-box-item" justify="end">
          <el-col :span="10" :pull="2">
            <el-checkbox v-model="loginForm.rememberMe">保持登陆</el-checkbox>
            <!--            <el-checkbox>记住密码</el-checkbox>-->
          </el-col>


          <el-col :span="8" style="line-height: 1.4">
            <span class="tips">还没有注册?点击<router-link to="/register">注册</router-link>
            </span>
          </el-col>
          <el-col :span="4" style="line-height: 1.4">
            <span class="linker tips"><router-link to="/forget">忘记密码?</router-link></span>
          </el-col>

        </el-row>

        <!--登陆-->
        <el-row class="login-box-item" justify="center">
          <el-button @click.prevent="doLogin" type="primary" style="width: 33%;">登陆</el-button>
          <el-button @click.prevent="changeMode" type="success" style="width: 33%;">
            <span v-show="!verifyCodeMode">
              验证码登陆
            </span>
            <span v-show="verifyCodeMode">
              账号密码登陆
            </span>
          </el-button>
        </el-row>

        <!-- 其它方式登录 -->
        <h3 class="login-box-item">其他方式登录:</h3>
        <el-row class="login-box-item" justify="center">
          <el-button type="success" circle>
            <font-awesome-icon icon="fa-brands fa-weixin"/>
          </el-button>
        </el-row>

        <!--Copyright-->
        <el-row class="login-box-footer" style="font-size: 1.5rem" justify="center">
          <el-col :span="7">
            <font-awesome-icon icon="fa-regular fa-copyright"/>
            <span class="tips">&nbsp {{ new Date().getFullYear() }} NEU FDS</span>
          </el-col>
        </el-row>
      </el-form>
    </div>

  </div>
</template>

<script setup>
import {ref} from 'vue'
import {ElMessage} from "element-plus";

import {useLoginStore} from "@/store/login";
import {useMenuStore} from "@/store/menu";
import {useUserStore} from "@/store/user";
import {storeToRefs} from "pinia"

import qs from "qs"
import router from "@/router";
import * as userApi from "@/api/user"


const loginStore = useLoginStore();
const menuStore = useMenuStore();
const userStore = useUserStore();

// store
const {sendVerifyCondeCollingTime, username, verifyCode, rememberMe} = storeToRefs(loginStore);


//==========================================
//                  用户信息
//==========================================
const loginForm = ref({
  username: username.value,
  password: undefined,
  verifyCode: verifyCode.value,
  rememberMe: rememberMe.value,
})


//==========================================
//                  页面控制
//==========================================

const verifyCodeMode = ref(false);

const loginRef = ref(null);

let loginRules = {
  username: [{required: true, trigger: "blur", message: "请输入您的账号"}],
  password: [{required: false, trigger: "blur", message: "请输入您的密码"}],
  verifyCode: [{required: false, trigger: "blur", message: "请输入验证码"}]
}

//==========================================
//                  页面方法
//==========================================

const changeMode = () => {
  verifyCodeMode.value = !verifyCodeMode.value;
}
// 用户登录
const doLogin = () => {
  loginRef.value.validate(async (valid) => {
    if (valid) {
      // 同步表单数据
      loginStore.syncFormData(loginForm.value);
      // 如果是验证码模式,不传密码,否则不传验证码
      if (verifyCodeMode.value) {
        loginForm.value.password = undefined;
      } else {
        loginForm.value.verifyCode = undefined;
      }
      let res = await userApi.login(qs.stringify(loginForm.value));
      let data = res.data;

      // 返回成功
      if (data.code === 200 || data.code === "200") {
        let token = data.token;
        // 设计用户信息
        await userStore.setUser(data.user);
        // 设置menuList
        await menuStore.setMenuList(data.menuList);
        // 返回token
        await loginStore.setToken(token);

        // 跳转主页
        await router.push("/")
      }
    } else {
      console.log("未填写完所有项目")
    }
  });
}


// 发送验证码
const sendVerifyCode = () => {
  // 1. 首先禁止再次点击, 设置为60s
  sendVerifyCondeCollingTime.value = 60;
  // 2. 请求后端发送
  //todo:
  ElMessage.success("已发送验证码")
  // 3. 设置定时器,定时器到期后允许再次发送
  let coolingInterval = setInterval(() => {
    // 改变冷却时间
    sendVerifyCondeCollingTime.value--;
    if (sendVerifyCondeCollingTime.value === 0) {
      clearInterval(coolingInterval);
    }
  }, 1000);

}
</script>

<style scoped>
:deep(.el-form-item__error) {
  left: 12%;
}

:deep(.el-input) {
  border-radius: 20px;
}

:deep(.el-input__wrapper) {
  background-color: rgba(255, 255, 255, 0) !important;
  -webkit-backdrop-filter: saturate(120%) blur(3px) !important;
  backdrop-filter: saturate(120%) blur(3px) !important;
}

:deep(.el-input-group__prepend) {
  background-color: rgba(255, 255, 255, 0) !important;
  -webkit-backdrop-filter: saturate(120%) blur(3px) !important;
  backdrop-filter: saturate(120%) blur(3px) !important;
}

:deep(.el-input-group__append) {
  width: 15%;
}
</style>

<style lang="scss">

.linker {
  font-size: 1.5rem;
  color: dodgerblue;
}

.linker:hover {
  cursor: pointer;
}

.login-box-container {
  width: inherit;
  position: absolute;
  border-radius: 7px;
  padding: 5px;
  box-shadow: 0 0 18px #424040;
  /*background-color: black;*/
}

.login-box {
  position: relative;
}


.login-box-item {
  height: 5rem;
  margin: 0.9rem;
  width: 95% !important;
}

.login-box-footer {

}

.box-title {
  text-align: center;
  font-size: 3rem;
  line-height: 3.5;
}

.tips {
  font-size: 1.6rem;
}

h3 {
  font-size: 2.4rem !important;
}

/*2500px +*/
@media all and (min-width: 2500px) {

}

/*1900 ~ 2500*/
@media all and (min-width: 1900px) and (max-width: 2499px) {

  .tips {
    font-size: 1.5rem;
  }
}

/* 993 ~ 1918px */
@media all and (min-width: 769px) and (max-width: 1918px) {
  .box-title {
    font-size: 2.2rem;
  }

  .tips {
    font-size: 1.4rem;
  }
}


/* 横向放置的手机及分辨率更小的设备 */
@media (max-width: 768px) {
  .tips {
    font-size: 1.4rem;
  }
}
</style>
