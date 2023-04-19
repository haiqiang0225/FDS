<template>

  <!-- v-for -->
  <el-row>

    <!-- 1.实时监控数据 2.热点状态 -->
    <el-col :span="4" v-for="(item, index) in hotspotDevices"
            style="padding:7px 0 7px 0;  border-bottom: 1px solid #c9c9c9; ">
      <HotspotMonitoringChart :chart-line-color="colors[index % colors.length]"/>
      <span> &nbsp;&nbsp;设备ID: {{ item.deviceId }}</span> <br/>
      <span> &nbsp;&nbsp;设备状态评估: {{ '正常' }} </span>
    </el-col>

  </el-row>
  <el-row style="margin-top: 10px">

    <el-col :span="4">
      <el-input v-model="deviceId">
        <template #prepend>设备ID:</template>
      </el-input>
    </el-col>

    <el-col :span="2">
      <el-button>添加到热点监测</el-button>
    </el-col>

  </el-row>
</template>

<script setup>

import HotspotMonitoringChart from "@/views/workbench/components/HotspotMonitoringChart.vue";
import {ref} from "vue";

// todo: mocks
// const hotspotDevices = ref([1, 2, 3, 4, 5, 6,]);
const hotspotDevices = ref([
  {
    deviceId: "aece9923-8ed1-47bb-bf74-65e307ec11dd",
  },
  {
    deviceId: "b0272426-cf33-4534-abac-d5d2c48025a3",
  },
  {
    deviceId: "b988498e-d848-4c4c-8462-badb1a66cef6",
  },
  {
    deviceId: "a93e7ab5-4135-45ac-8192-4043e8027ca8",
  },
  {
    deviceId: "327e1732-114f-478a-b82c-594049616b9a",
  },
  {
    deviceId: "060a2a6f-713d-4aaf-a118-047cb01efd76",
  },
])


const deviceId = ref('');

const colors = ['#eebe77', '#F56C6C', '#79bbff', '#95d475',
  '#cc33ff', '#FFD700', '#CAFF70', '#48D1CC'];

</script>

<style lang="scss" scoped>

</style>
