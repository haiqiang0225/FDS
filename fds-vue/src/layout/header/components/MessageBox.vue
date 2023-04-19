<template>
  <el-popover
      placement="bottom"
      title="Title"
      :width="300"
      trigger="hover"
  >
    <template #reference>
      <el-button
          style="border: none; font-size: 16px;"
          circle>
        <el-icon>
          <svg-icon class="svg-icon" icon="message_center"/>
        </el-icon>
      </el-button>
    </template>

    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="消息通知" name="first">
        <div class="message">
          消息通知Module

        </div>
      </el-tab-pane>
      <el-tab-pane label="邮件" name="second">
        <div class="email">
          邮件Module
        </div>
      </el-tab-pane>
    </el-tabs>
  </el-popover>
</template>

<script setup>
import {ref} from "vue";

const activeName = ref();


function handleClick() {
}
</script>

<style lang="scss" scoped>
.svg-icon {
  @include svg-color("color-primary");
  @include font-color("color-primary");
}
</style>
