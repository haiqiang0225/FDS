<template>
  <el-row style="margin: 10px 0 10px 0">
    <el-col :span="4">
      <el-input v-model="deviceId">
        <template #prepend>设备ID:</template>
      </el-input>
    </el-col>

    <el-col :span="2">
      <el-button>分析</el-button>
    </el-col>

  </el-row>
</template>

<script setup>
import {ref} from "vue";

const deviceId = ref('');
</script>

<style lang="scss" scoped>

</style>
