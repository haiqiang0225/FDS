<template>

  <el-menu
      active-text-color="#5997fa"
      background-color="@include background-color('color-primary')"
      text-color="@include font-color('color-title')"

      :default-active="editableTabsValue"
      :default-openeds="opends"
      class="menu-container el-menu-vertical-demo"
      :collapse="isCollapse"
      router
  >
    <div class="logo-container">
      <router-link to="/index">
        <span class="logo"></span>
        <span class="title">权限管理</span>
      </router-link>
    </div>

    <ul v-for="menu in menuList">
      <!-- 如果有子集-->
      <el-sub-menu :index="menu.path" v-if="menu.childrenList">
        <template #title>
          <el-icon>
            <svg-icon :icon="menu.icon || 'default'"></svg-icon>
          </el-icon>
          <span>{{ menu.nameZh }}</span>
        </template>

        <el-menu-item :index="item.path" v-for="item in menu.childrenList" @click="openTab(item)">
          <el-icon>
            <svg-icon :icon="item.icon || 'default'"></svg-icon>
          </el-icon>
          <span>{{ item.nameZh }}</span>
        </el-menu-item>
      </el-sub-menu>

      <!-- 如果没有子集 -->
      <el-menu-item :index="menu.path" v-if="!menu.childrenList" @click="openTab(menu)">
        <el-icon>
          <svg-icon :icon="menu.icon || 'default'"></svg-icon>
        </el-icon>
        <template #title>{{ menu.nameZh }}</template>
      </el-menu-item>
    </ul>

  </el-menu>
</template>

<script setup>
import {ref} from 'vue'

import {useMenuStore} from "@/store/menu";
import {storeToRefs} from "pinia";
import {useTabsStore} from "@/store/tabs";
//==========================================================================================
//                                        属性
//==========================================================================================
const menuStore = useMenuStore();
const tabsStore = useTabsStore();

const {menuList} = storeToRefs(menuStore);


const isCollapse = ref(false);

const {editableTabsValue} = storeToRefs(tabsStore);
//==========================================================================================
//                                        方法
//==========================================================================================
const openTab = function (menu) {
  tabsStore.addTab({
        title: menu.nameZh,
        name: menu.path,
      }
  );
}

const opends = ref([editableTabsValue.value]);


</script>

<style lang="scss">
.menu-container {
  height: 100%;

  .logo-container {
    position: relative;
    overflow: hidden;
    background: transparent;
    height: 60px;
    line-height: 60px;
    text-align: center;

    .logo {
      display: inline-block;
      margin-left: 5px;
      overflow: hidden;
      font-size: 20px;
      line-height: 55px;
      color: #fff;
      text-overflow: ellipsis;
      white-space: nowrap;
      vertical-align: middle;
    }

    .title {
      display: inline-block;
      margin-left: 5px;
      overflow: hidden;
      font-size: 20px;
      line-height: 55px;
      //color: #0c0c0c;
      @include font-color("color-title");
      text-overflow: ellipsis;
      white-space: nowrap;
      vertical-align: middle;
      max-width: calc(var(--el-left-menu-width) - 60px);
    }
  }

}

.el-menu-item .is-active {
  color: #8AF6AB;
}

.el-menu-item:hover {
  @include background-color('color-active-menu');
}

.el-sub-menu__title:hover {
  @include background-color('color-active-menu');
}
</style>
