<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="1"></el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <template v-slot:header>
            <div class="clearfix">
              <span>个人信息</span>
            </div>
          </template>
          <div>
            <div class="text-center">
              <avatar :user="user"/>
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon="user"/>&nbsp;&nbsp;用户名称
                <div class="pull-right">{{ user.username }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon="user"/>&nbsp;&nbsp;昵称
                <div class="pull-right">{{ user.nickname }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon="phone"/>&nbsp;&nbsp;手机号码
                <div class="pull-right">{{ user.phoneNumber }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon="email"/>&nbsp;&nbsp;用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon="date"/>&nbsp;&nbsp;创建日期
                <div class="pull-right">{{ user.registerTime }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card>
          <template v-slot:header>
            <div class="clearfix">
              <span>基本资料</span>
            </div>
          </template>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userinfo :user="user"></userinfo>
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <reset-password :user="user"></reset-password>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script setup>
import Avatar from "@/views/userCenter/avatar.vue";
import ResetPassword from "@/views/userCenter/resetPassword.vue";
import Userinfo from "@/views/userCenter/userinfo.vue";


import {useUserStore} from "@/store/user";
import {ref} from "vue";

const userStore = useUserStore();


let user = userStore.getUser;

const activeTab = ref("userinfo");
</script>

<style lang="scss" scoped>

.app-container {
  overflow-x: hidden;
}

.list-group-striped > .list-group-item {
  border-left: 0;
  border-right: 0;
  border-radius: 0;
  padding-left: 0;
  padding-right: 0;
}

.list-group-item {
  border-bottom: 1px solid #e7eaec;
  border-top: 1px solid #e7eaec;
  margin-bottom: -1px;
  padding: 11px 0;
  font-size: 13px;
}

.pull-right {
  float: right !important;
}

:deep(.el-card__body) {
  min-height: 230px;
}

:deep(.box-card) {
  //height: 450px;
}

</style>
