<template>
  <div class="index-main-container">

    <el-row class="row">
      <el-col :span="24">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="equipment"></svg-icon>
                </el-icon>
                <span>&nbsp;设备状态评估</span>
              </div>
            </div>

          </template>

          <EquipmentEvaluator/>

        </el-card>
      </el-col>
    </el-row>

    <el-row class="row">
      <el-col :span="24">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="maintenance"></svg-icon>
                </el-icon>
                <span>&nbsp;维保管理 </span>
              </div>
            </div>

          </template>

          <EquipmentMaintenance/>

        </el-card>
      </el-col>
    </el-row>


    <el-row class="row">
      <el-col :span="24">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="hotspot"></svg-icon>
                </el-icon>
                <span>&nbsp;热点监测 </span>
              </div>
            </div>

          </template>

          <HotspotMonitoring/>
        </el-card>
      </el-col>
    </el-row>

    <el-row class="row">
      <el-col :span="24">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="fault_analysis"></svg-icon>
                </el-icon>
                <span>&nbsp;故障分析 </span>
              </div>
            </div>

          </template>

          <FaultAnalysis/>
        </el-card>
      </el-col>
    </el-row>
    <br/>

  </div>
</template>

<script setup>
import {useUserStore} from "@/store/user";
import {onMounted, ref, watch} from "vue";
import {appendUrls, getRequestBaseUrl} from "@/utils/request";
import {useThemeStore} from "@/store/theme";
import {storeToRefs} from "pinia";
import EquipmentEvaluator from "@/views/workbench/components/EquipmentEvaluator.vue";
import EquipmentMaintenance from "@/views/workbench/components/EquipmentMaintenance.vue";
import HotspotMonitoring from "@/views/workbench/components/HotspotMonitoring.vue";
import FaultAnalysis from "@/views/workbench/components/FaultAnalysis.vue";
//==========================================================================================
//                                        属性
//==========================================================================================
const userStore = useUserStore();
const themeStore = useThemeStore()

const {theme} = storeToRefs(themeStore);

const user = userStore.getUser;
let uri = user ? user.avatarUri : "default.jpg";
let avatarUrl = ref(appendUrls(getRequestBaseUrl(), "api", uri));

const screenWidth = ref(window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth);
//==========================================================================================
//                                        方法
//==========================================================================================


onMounted(() => {
  window.onresize = () => {
    screenWidth.value = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
  }
})

</script>

<style lang="scss" scoped>

.index-main-container {
  padding: 18px;

  .card-header {
    font-size: 18px;
    font-weight: 700;
  }

}

.row {
  margin: 10px 0 10px 0;
}

.circle {
  border-radius: 50%;
  height: 10px;
  width: 10px;
  display: inline-block;
}

.item {
  margin-bottom: 18px;
}


</style>
