<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <el-avatar shape="square" :size="40" :src="avatarUrl"/>
      &nbsp;&nbsp; {{ user.username }}
      <el-icon class="el-icon--right">
        <arrow-down/>
      </el-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item>
          <router-link :to="{
            name: 'userCenter'
          }">个人中心
          </router-link>
        </el-dropdown-item>
        <el-dropdown-item @click="logout ">退出登陆</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>

</template>

<script setup>
import request, {appendUrls, getRequestBaseUrl} from "@/utils/request";
import {ArrowDown} from '@element-plus/icons-vue'
import {useUserStore} from "@/store/user";
import {ref} from "vue";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useTabsStore} from "@/store/tabs";
import {useMenuStore} from "@/store/menu";

//==========================================================================================
//                                        属性
//==========================================================================================
const userStore = useUserStore();
const tabsStore = useTabsStore();
const menuStore = useMenuStore();

const user = userStore.getUser;

let uri = user ? user.avatarUri : "default.jpg";
let avatarUrl = ref(appendUrls(getRequestBaseUrl(), "api", uri));


//==========================================================================================
//                                        方法
//==========================================================================================

// 登出
let logout = async function () {
  let url = "/api/logout";
  let result = await request.get(url);
  if (result.data.msg) {
    if (result.data.code && result.data.code === 200) {
      ElMessage.success(result.data.msg);
    } else {
      ElMessage.info(result.data.msg);
    }
  }

  // 状态清除
  await window.sessionStorage.clear();
  await localStorage.removeItem("token");
  await sessionStorage.clear();

  // VueX/Pinia 状态清除
  await menuStore.setIsRouterAlreadyBound(false);
  await tabsStore.resetTabs();

  // 重定向到登录页
  await router.replace("/login")
}

</script>

<style lang="scss" scoped>
.el-dropdown-link {
  cursor: pointer;
  color: red;
  display: flex;
  align-items: center;
  text-align: center;
}

</style>
