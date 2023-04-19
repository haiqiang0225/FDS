<template>
  <el-row class="row">

    <el-col :span="4">
      <el-input v-model="deviceID">
        <template #prepend>设备ID:</template>
      </el-input>
    </el-col>

    <el-col :span="2">
      <el-input>
        <template #prepend>名称:</template>
      </el-input>
    </el-col>

    <el-col :span="2">
      <el-select v-model="runningState" placeholder="设备运行状态">
        <el-option
            v-for="item in equipmentStates"
            :key="item"
            :label="item"
            :value="item"
        />
      </el-select>
    </el-col>

    <el-col :span="2" style="margin-left: 7px;">
      <el-button>查找</el-button>
    </el-col>
  </el-row>


  <!--  第1行内容  -->
  <!--  v-for  -->
  <el-row class="row" v-for="(equipment, index) in equipments">
    <el-col :span="24"
            style="margin: 10px 0 10px 0"
            class="col"
    >
      <el-card class="box-card"
               shadow="hover">
        <template #header>
          <div class="card-header">
            <div class="card-title">
              <el-icon class="card-icon">
                <svg-icon icon="line_chart"></svg-icon>
              </el-icon>
              <span>&nbsp; 设备监控 </span>
            </div>
          </div>
        </template>
        <!-- 监控 -->
        <el-row>
          <el-col :span="4" style="border: 1px solid #d5d5d5; padding: 5px; border-radius: 7px">
            <el-row class="row-item">
              设备ID: {{ equipment.id }}
              <el-button size="small"
                         :text="true"
                         style="width: 12px;"
                         @click="copyDeviceId(equipment.id)">
                <el-icon>
                  <svg-icon icon="copy"/>
                </el-icon>
              </el-button>
            </el-row>

            <el-row class="row-item">
              设备名称: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
                equipment.name
              }}
            </el-row>

            <el-row class="row-item">
              设备类型: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
                equipment.type
              }}
            </el-row>

            <el-row class="row-item">
              设备运行状态: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ equipment.state }}
            </el-row>

            <el-row class="row-item">
              设备通信协议:
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ equipment.communicationProtocol }}
            </el-row>

            <el-row class="row-item">
              设备通信地址(ipv4): &nbsp;&nbsp;{{ equipment.ipv4 }}
            </el-row>

            <el-row class="row-item">
              CPU USAGE: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ '12.34%' }}
            </el-row>

            <el-row class="row-item">
              Memory USAGE: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ '12.34%' }}
            </el-row>

            <el-divider />

            <el-row style="height: 240px; margin-top: 30px;">
              <ResourceDashboard :device-id="equipment.id" :resource-name="'cpu'" :height="'300px;'"/>
            </el-row>
          </el-col>

          <!--  实时监控数据  -->
          <el-col :span="10">
            <EquipmentTimeSeriesChart :device-id="equipment.id" :chart-line-color="colors[(2 * index) % colors.length]" :title="'实时数据'"/>
          </el-col>

          <!--  预测数据  -->
          <el-col :span="10">
            <EquipmentTimeSeriesChart :device-id="equipment.id" :chart-line-color="colors[(2 * index + 1) % colors.length]" :title="'预测数据'"/>
          </el-col>

        </el-row>
      </el-card>
    </el-col>

  </el-row>
</template>

<script setup>

import EquipmentTimeSeriesChart
  from "@/views/dashboard/components/EquipmentCard/components/EquipmentTimeSeriesChart.vue";
import ResourceDashboard from "@/views/dashboard/components/EquipmentCard/components/ResourceDashboard.vue";
import getUuid from "@/utils/uuid";
import {communicationProtocols, equipmentStates, equipmentTypes, Proxy} from "@/mock/equipments";
import {ref} from "vue";
import useClipboard from 'vue-clipboard3'
import {ElNotification} from "element-plus";

const {toClipboard} = useClipboard()
// const colors = ['#98FB98', '#79bdbd', '#FFF68F', '#9F79EE'];
const colors = ['#98FB98', '#58ffff'];


// todo: mocks
const equipments = ref([
  {
    id: getUuid(),
    name: equipmentTypes[0],
    type: 'AD104',
    state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
    loadCpu: new Proxy(1),
    loadMemory: new Proxy(1),
    communicationProtocol: communicationProtocols[0],
    ipv4: '127.0.0.1',
    ipv6: '',
  },
  {
    id: getUuid(),
    name: equipmentTypes[0],
    type: 'AD105',
    state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
    loadCpu: new Proxy(1),
    loadMemory: new Proxy(1),
    communicationProtocol: communicationProtocols[0],
    ipv4: '127.0.0.1',
    ipv6: '',
  },
  {
    id: getUuid(),
    name: equipmentTypes[0],
    type: 'AD105',
    state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
    loadCpu: new Proxy(1),
    loadMemory: new Proxy(1),
    communicationProtocol: communicationProtocols[0],
    ipv4: '127.0.0.1',
    ipv6: '',
  },
]);

const runningState = ref('');
const deviceID = ref('');

async function copyDeviceId(id) {
  try {
    await toClipboard(id);
    ElNotification({
      title: '复制成功',
      message: '已将设备ID复制到剪切板',
      type: 'success',
    });
  } catch (e) {
    ElNotification({
      title: '复制失败',
      message: '您的浏览器不支持复制',
      type: 'error',
    })
    console.log(e);
  }
}

</script>

<style lang="scss" scoped>

.card-title {
  font-size: 18px;
  font-weight: 700;
}

.row-item {
  margin: 7px 0 7px 0;
  font-size: 14px;
  font-weight: 700;
  white-space: nowrap;
}
</style>
