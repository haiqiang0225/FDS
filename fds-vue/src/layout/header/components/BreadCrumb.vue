<template>
  <el-icon style="display: inline-block">
    <HomeFilled/>
  </el-icon>
  &nbsp;&nbsp;
  <el-breadcrumb :separator-icon="ArrowRight" style="display: inline-block">
    <el-breadcrumb-item :index="index" v-for="(item, index) in breadcrumbList">
      {{ item.nameZh }}
    </el-breadcrumb-item>

  </el-breadcrumb>
</template>


<script setup>
//==========================================================================================
//                                        属性
//==========================================================================================
import {ref, watch} from "vue";
import {ArrowRight, HomeFilled} from '@element-plus/icons-vue'
import {useRoute} from "vue-router";
import {useMenuStore} from "@/store/menu";

const route = useRoute();
const menuStore = useMenuStore();


const breadcrumbList = ref([{nameZh: '首页', path: '/index'}]);


let matched = ref(resolveMatched(route.matched));

//==========================================================================================
//                                        方法
//==========================================================================================
const refreshBreadcrumbList = () => {
  resolveCurrentPathToList(menuStore.getMenuList);
};

function resolveCurrentPathToList(menuList) {
  menuList.forEach(e => {
    doResolveMenuTreeToList(e);
  });
}

function doResolveMenuTreeToList(menu) {
  // 如果当前的就是要找的, 则完毕
  if (menu.path === matched.value) {
    if (menu.path === '/index') {
      return true;
    }
    breadcrumbList.value.splice(1, 0, menu);
    return true;
  } else if (menu.childrenList) { // 如果有子列表,则递归
    let resolved = false;
    menu.childrenList.forEach(e => {
      resolved ||= doResolveMenuTreeToList(e);
    });
    // 说明在此路径找到了目标节点
    if (resolved) {
      breadcrumbList.value.splice(1, 0, menu);
    }
    return true;
  }
  // 不满足上面两种情况,说明不用处理,直接跳过
  return false;
}

function resolveMatched(matched) {
  let res = undefined;
  for (let i = matched.length - 1; i >= 0; i--) {
    if (matched[i].path !== '/index') {
      res = matched[i];
      break;
    }
  }

  return res ? res.path : matched[0].path;
}

watch(route, () => {
  matched.value = resolveMatched(route.matched);
  breadcrumbList.value = [{nameZh: '首页', path: '/index'}];
  refreshBreadcrumbList();
}, {deep: true, immediate: true});


</script>

<style lang="scss" scoped>
.el-breadcrumb__item {
  .el-breadcrumb__inner .is-link {
    color: white !important;
  }
}

</style>
