<template>

  <el-menu
      active-text-color="@include font-color('color-primary')"
      background-color="@include background-color('color-primary')"
      text-color="@include font-color('color-title')"

      :default-active="editableTabsValue"
      :default-openeds="opends"
      class="menu-container el-menu-vertical-demo el-menu-vertical"
      :collapse="isCollapse"
      router
  >
    <div class="logo-container">
      <router-link to="/index">

        <span class="logo-warp-span">
          <el-icon>
            <svg-icon icon="fault" class="svg-icon"></svg-icon>
          </el-icon>
        </span>

        <span class="title">FDS故障诊断系统</span>
      </router-link>
    </div>

    <ul v-for="menu in menuList">
      <!-- 如果有子集-->
      <el-sub-menu class="sub-menu" :index="menu.path" v-if="menu.childrenList && !menu.hidden">
        <template #title>
          <span class="icon-warp-span">
            <el-icon>
              <svg-icon :icon="menu.icon || 'default'" class="svg-icon" :style="editableTabsValue === menu.path? 'fill: #FFAF34 !important;': ''"></svg-icon>
            </el-icon>
          </span>
          <span class="item-title" :style="editableTabsValue === menu.path? 'color: #FFAF34 !important;': ''" v-show="!isCollapse">{{ menu.nameZh }}</span>
        </template>

        <el-menu-item v-if="!menu.hidden" :index="item.path" v-for="item in menu.childrenList" @click="openTab(item)">
          <span class="icon-warp-span">
            <el-icon>
              <svg-icon :icon="item.icon || 'default'" class="svg-icon" :style="editableTabsValue === item.path? 'fill: #FFAF34 !important;': ''"></svg-icon>
            </el-icon>
          </span>
          <span class="item-title" :style="editableTabsValue === item.path? 'color: #FFAF34 !important;': ''">{{ item.nameZh }}</span>
        </el-menu-item>
      </el-sub-menu>

      <!-- 如果没有子集 -->
      <el-menu-item :index="menu.path" v-if="!menu.childrenList && !menu.hidden" @click="openTab(menu)">
        <span class="icon-warp-span">
          <el-icon>
            <svg-icon  :icon="menu.icon || 'default'" class="svg-icon" :style="editableTabsValue === menu.path? 'fill: #FFAF34 !important;': ''"></svg-icon>
          </el-icon>
        </span>
        <template #title>
          <span class="item-title" :style="editableTabsValue === menu.path? 'color: #FFAF34 !important;': ''">{{ menu.nameZh }}
          </span>
        </template>
      </el-menu-item>
    </ul>

  </el-menu>
</template>

<script setup>
import {ref} from 'vue'

import {useMenuStore} from "@/store/menu";
import {storeToRefs} from "pinia";
import {useTabsStore} from "@/store/tabs";
import {
  HomeFilled,
  User,
  Tickets,
  Goods,
  DocumentAdd,
  Management,
  Setting,
  Edit,
  SwitchButton,
  Promotion
} from '@element-plus/icons-vue'
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
    font-weight: 700;
    font-family: Kai, serif;
    padding: 14px;

    .logo-warp-span {
      display: inline-block;
      margin-top: 7px;
      margin-left: 5px;
      overflow: hidden;
      font-size: 20px;
      line-height: 60px;
      //color: #fff;
      text-overflow: ellipsis;
      white-space: nowrap;
      vertical-align: middle;
      fill: currentColor;

      .svg-icon {
        @include svg-color("color-primary-font");
        stroke: #fff;
      }

    }

    .title {
      @include font-color("color-primary-font");

      @include mac13 {
        margin-left: 5px;
        font-size: 26px;
      }

      @include lg27 {
        margin-left: 5px;
        font-size: 26px;

      }

      display: inline-block;
      overflow: hidden;
      line-height: 55px;

      text-overflow: ellipsis;
      white-space: nowrap;
      vertical-align: middle;
      max-width: calc(var(--el-left-menu-width) - 60px);
    }
  }

  .icon-warp-span {

    .svg-icon {
      @include svg-color("color-primary-font");
    }
  }

  .item-title {
    //@include font-color("color-primary-font");
    color: #FFFFFF;
  }

  .sub-menu {
    @include font-color("color-primary-font");
  }
}


:deep(.el-menu-item .is-active > .item-title) {
  @include font-color('color-primary');
}

.el-menu-item:hover {
  @include background-color('color-active-menu');
}

.el-sub-menu__title:hover {
  @include background-color('color-active-menu');
}
</style>
