<template>
  <div class="login-box-container glass">

    <!--登陆框-->
    <div class="login-box">
      <h2 class="box-title">FDS 故障诊断系统</h2>
      <h3 class="login-box-item" style="margin: 0 0 0 .9rem">用户登录</h3>


      <!--    用户名    -->
      <el-input
          class="login-box-item"
          placeholder="请输入用户名/手机号/邮箱"
          v-model="username"
          maxlength="20"
          clearable
      >
        <template #prepend>
          <font-awesome-icon icon="fa-solid fa-user"/>
        </template>
      </el-input>

      <!--    密码    -->
      <el-input class="login-box-item"
                placeholder="请输入新密码"
                v-model="password"
                maxlength="32"
                type="password"
                show-password
                clearable
      >
        <template #prepend>
          <font-awesome-icon icon="fa-solid fa-lock"/>
        </template>
      </el-input>

      <!--    密码    -->
      <el-input class="login-box-item"
                placeholder="请重复输入新密码"
                v-model="password"
                maxlength="32"
                type="password"
                show-password
                clearable
      >
        <template #prepend>
          <font-awesome-icon icon="fa-solid fa-lock"/>
        </template>
      </el-input>


      <!--验证码-->
      <el-row class="login-box-item">
        <el-input class="login-input"
                  placeholder="请输入验证码"
                  v-model="verifyCode"
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

      <!--保持登陆/记住密码/注册/找回-->
      <el-row class="login-box-item" justify="end">
        <el-col :span="10" :pull="5">
          <el-checkbox v-model="rememberMe">保持登陆</el-checkbox>
          <!--            <el-checkbox>记住密码</el-checkbox>-->
        </el-col>

        <el-col :span="5" style="line-height: 1.4">
            <span style="font-size: 1.5rem">点击<router-link to="/login">返回登录</router-link>
            </span>
        </el-col>
        <el-col :span="4" style="line-height: 1.4">
          <span class="linker"><router-link to="/forget">忘记密码?</router-link></span>
        </el-col>
      </el-row>

      <!--登陆-->
      <el-row class="login-box-item" justify="center">
        <el-button @click="doLogin" type="warning" style="width: 67%;">重置密码</el-button>
      </el-row>


      <!--Copyright-->
      <el-row class="login-box-footer" style="font-size: 1.5rem" justify="center">
        <el-col :span="7">
          <font-awesome-icon icon="fa-regular fa-copyright"/>
          <span>&nbsp {{ new Date().getFullYear() }} NEU FDS</span>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import {useLoginStore} from "@/store/login";
import {storeToRefs} from "pinia"

const store = useLoginStore()
// store
const {sendVerifyCondeCollingTime, rememberMe, verifyCode, username} = storeToRefs(store)
</script>

<style scoped>

</style>
