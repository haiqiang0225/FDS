<template>
  <el-row style="width: 100%">
    <el-col :span="16">
      <SystemLoadLineChart/>
    </el-col>
    <el-col :span="8">
      <SummaryInfo/>
    </el-col>
  </el-row>
</template>

<script setup>
import SummaryInfo from "@/views/index/components/SystemInfo/components/SummaryInfo.vue";
import SystemLoadLineChart from "@/views/index/components/SystemInfo/components/SystemLoadLineChart.vue";

</script>

<style lang="scss" scoped>

</style>
