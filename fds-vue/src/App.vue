<template>
  <router-view
      v-slot="{ Component }">
    <transition name="fade">
      <component :is="Component"/>
    </transition>
  </router-view>
</template>

<script setup>

import {onBeforeMount, watch} from "vue";
import {useRoute} from "vue-router";
import {useTabsStore} from "@/store/tabs";


const route = useRoute();
const tabsStore = useTabsStore();

onBeforeMount(() => {
  // 设置主题
  let theme = localStorage.getItem("theme");
  if (theme) {
    window.document.documentElement.setAttribute('data-theme', theme);
  } else {
    window.document.documentElement.setAttribute('data-theme', 'Dark');
  }
});

const whitePath = ["", "/", "/index", "/login", "/forget", "/register"]

watch(route, (to, from) => {
  let matched = route.matched[1] || route.matched[0];
  if (matched) {
    if (matched.path !== '/login')
      tabsStore.setEditableTabsValue(matched.path);
    else if (matched.path !== '/login') {
      tabsStore.setEditableTabsValue('/index');
    }
  }
  if (to.meta.title) {
    tabsStore.addTab({
      title: to.meta.title,
      name: matched.path,
    });
  }
}, {deep: true, immediate: true});


</script>

<style lang="scss">
html, body, #app {
  height: 100%;
}


//
.root-container {
  min-height: 100vh;
  min-width: 100vw;
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(#26294d, #25446c);
}

</style>
