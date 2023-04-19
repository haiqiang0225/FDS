<template>
  <el-button
      @click="changeTheme"
      style="border: none; font-size: 16px"
      circle>
    <svg-icon icon="sun"></svg-icon>
  </el-button>
</template>

<script setup>
import {useThemeStore} from "@/store/theme";
import {storeToRefs} from "pinia";


//==========================================================================================
//                                        属性
//==========================================================================================
const themeStore = useThemeStore();
const {theme} = storeToRefs(themeStore);


//==========================================================================================
//                                        方法
//==========================================================================================

const changeTheme = () => {
  if (theme.value === 'light') {
    theme.value = 'dark';
  } else {
    theme.value = 'light';
  }
}
</script>

<style lang="scss" scoped>

</style>
