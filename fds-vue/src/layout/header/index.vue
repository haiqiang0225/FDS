<template>
  <div class="navbar-container">

    <div class="navbar-left">
      <BreadCrumb></BreadCrumb>
    </div>

    <div class="navbar-right">
      <Avatar></Avatar>
    </div>

  </div>
</template>

<script setup>
import BreadCrumb from "@/layout/header/components/BreadCrumb.vue";
import Avatar from "@/layout/header/components/Avatar.vue";</script>


<style lang="scss" scoped>
// 导航栏容器
.navbar-container {
  width: 100%;
  height: 60px;
  overflow: hidden;
  @include background-color("color-primary-light");
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  padding: 0 16px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  position: relative;

  // 右侧容器
  .navbar-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: flex-end;

    :deep(.navbar-item) {
      display: inline-block;
      margin-left: 18px;
      font-size: 22px;
      color: #5a5e66;
      box-sizing: border-box;
      cursor: pointer;
    }
  }

}
</style>
