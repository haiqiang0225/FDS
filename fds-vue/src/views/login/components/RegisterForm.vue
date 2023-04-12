<template>
  <div class="login-box-container glass">
    <el-form>
      <!--登陆框-->
      <div class="login-box">
        <h2 class="box-title">FDS 故障诊断系统</h2>
        <h3 class="login-box-item" style="margin: 0 0 0 .9rem">用户注册</h3>


        <!--    用户名    -->
        <el-input
            class="login-box-item"
            placeholder="请输入用户名(必填)"
            v-model="username"
            maxlength="20"
            clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-user"/>
          </template>
        </el-input>


        <!--    手机号    -->
        <el-input
            class="login-box-item"
            placeholder="请输入手机号(必填)"
            v-model="username"
            maxlength="20"
            clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-phone"/>
          </template>
        </el-input>

        <!--    邮箱    -->
        <el-input
            class="login-box-item"
            placeholder="请输入邮箱"
            v-model="username"
            maxlength="20"
            clearable
        >
          <template #prepend>
            <font-awesome-icon icon="fa-solid fa-envelope"/>
          </template>
        </el-input>


        <!--    密码    -->
        <el-input class="login-box-item"
                  placeholder="请输入密码(必填)"
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
                  placeholder="请输入密码(必填)"
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
          <el-col :span="10" :pull="3">
            <el-checkbox v-model="rememberMe">保持登陆</el-checkbox>
            <!--            <el-checkbox>记住密码</el-checkbox>-->
          </el-col>

          <el-col :span="7" style="line-height: 1.4">
            <span style="font-size: 1.5rem">已有账号?点击<router-link to="/login">登陆</router-link>
            </span>
          </el-col>
          <el-col :span="4" style="line-height: 1.4">
            <span class="linker"><router-link to="/forget">忘记密码?</router-link></span>
          </el-col>
        </el-row>

        <!--登陆-->
        <el-row class="login-box-item" justify="center">
          <el-button @click="doLogin" type="success" style="width: 67%;">注册</el-button>
        </el-row>

        <!-- 其它方式登录 -->
        <span class="login-box-item">其他方式注册:</span>
        <el-row class="login-box-item" justify="center">
          <el-button type="success" circle>
            <font-awesome-icon icon="fa-brands fa-weixin"/>
          </el-button>

          <!--          <el-button type="success" circle>-->
          <!--            <font-awesome-icon icon="fa-brands fa-weixin"/>-->
          <!--          </el-button>-->

          <!--          <el-button type="success" circle>-->
          <!--            <font-awesome-icon icon="fa-brands fa-weixin"/>-->
          <!--          </el-button>-->
        </el-row>

        <!--Copyright-->
        <el-row class="login-box-footer" style="font-size: 1.5rem" justify="center">
          <el-col :span="7">
            <font-awesome-icon icon="fa-regular fa-copyright"/>
            <span>&nbsp {{ new Date().getFullYear() }} NEU FDS</span>
          </el-col>
        </el-row>
      </div>
    </el-form>
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

:deep(.el-input) {
  border-radius: 20px;
}

:deep(.el-input__wrapper) {
  background-color: rgba(255, 255, 255, 0) !important;
  -webkit-backdrop-filter: saturate(120%) blur(3px) !important;
  backdrop-filter: saturate(120%) blur(3px) !important;
}

:deep(.el-input-group__prepend ) {
  background-color: rgba(255, 255, 255, 0) !important;
  -webkit-backdrop-filter: saturate(120%) blur(3px) !important;
  backdrop-filter: saturate(120%) blur(3px) !important;
}

:deep(.el-input-group__append) {
  width: 15%;
}


</style>
