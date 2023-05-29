<template>
  <div class="index-main-container">
    <!--  第1行内容  -->
    <el-row class="row">

      <el-col :span="24">
        <el-card class="box-card"
                 shadow="hover"
                 style="width: 100%;">

          <!-- 用户头像 -->
          <span class="card-item" style="margin-right: 18px; display: inline-block">
            <el-avatar shape="square" :size="80" :src="avatarUrl"/>
          </span>


          <div class="card-item" style="display: inline-block">
            <div
                style="height: 30%;transform: translateY(60%); margin-bottom: 20px; font-size: 18px; font-weight: 700;">
              {{ helloMessage() }}
            </div>
            <span style="font-size: 14px"> 你好, 欢迎使用 NEU FDS故障检测与诊断系统 </span>
          </div>

        </el-card>
      </el-col>

    </el-row>

    <!--  第2行内容  -->
    <el-row class="row">

      <el-col :span="16" style="padding-right: 10px">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="line_chart"></svg-icon>
                </el-icon>
                <span>&nbsp;近期故障事件统计</span>
              </div>
            </div>
          </template>

          <RecentlyFaultSummary/>
        </el-card>
      </el-col>

      <el-col :span="8" style="padding-left: 10px">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="pie_chart"></svg-icon>
                </el-icon>
                <span>&nbsp;不同类型设备故障数量对比</span>
              </div>
              <div class="card-title">
                <div style="display: inline-block; float: right">
                  <div class="circle" style="background-color: red; margin-left: 5px;">
                  </div>
                  <div class="circle" style="background-color: green; margin-left: 5px;">
                  </div>
                  <div class="circle" style="background-color: blue; margin-left: 5px;">
                  </div>
                </div>
              </div>
            </div>
          </template>

          <FaultSummaryByEquipmentType/>
        </el-card>
      </el-col>

    </el-row>

    <!--  第3行内容  -->
    <el-row class="row">

      <el-col :span="24">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="bar_chart"></svg-icon>
                </el-icon>
                <span>&nbsp;故障类型统计</span>
              </div>
            </div>
          </template>

          <SummaryOfFaultType/>
        </el-card>
      </el-col>

    </el-row>


    <!--  第4行内容  -->
    <el-row class="row">

      <!-- 设备总数 -->
      <el-col :span="screenWidth < 2400 ? 12: 6"
              style="padding-right: 10px">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="circle_chart"></svg-icon>
                </el-icon>
                <span>&nbsp;设备统计信息 </span>
              </div>
              <div class="card-title">
                <div style="display: inline-block; float: right">
                  <div class="circle" style="background-color: red; margin-left: 5px;">
                  </div>
                  <div class="circle" style="background-color: green; margin-left: 5px;">
                  </div>
                  <div class="circle" style="background-color: blue; margin-left: 5px;">
                  </div>
                </div>
              </div>
            </div>
          </template>

          <EquipmentsSummary/>
        </el-card>
      </el-col>

      <!-- 故障严重程度统计 -->
      <el-col :span="screenWidth < 2400 ? 12: 6"
              style="padding-right: 10px">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="pie_chart"></svg-icon>
                </el-icon>
                <span>&nbsp;故障严重程度统计</span>
              </div>
              <div class="card-title">
                <div style="display: inline-block; float: right">
                  <div class="circle" style="background-color: red; margin-left: 5px;">
                  </div>
                  <div class="circle" style="background-color: green; margin-left: 5px;">
                  </div>
                  <div class="circle" style="background-color: blue; margin-left: 5px;">
                  </div>
                </div>
              </div>
            </div>
          </template>

          <FaultSeverityDegreeSummary/>
        </el-card>
      </el-col>

      <!-- 系统统计信息 -->
      <el-col :span="screenWidth < 2400 ? 12: 12" :style="screenWidth < 2400 ? 'margin-top: 10px': ''">
        <el-card class="box-card"
                 shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="card-title">
                <el-icon class="card-icon">
                  <svg-icon icon="system_summary"></svg-icon>
                </el-icon>
                <span>&nbsp;系统统计信息 </span>
              </div>

            </div>
          </template>

          <!--  系统统计信息  -->
          <SystemInfo/>

        </el-card>
      </el-col>

    </el-row>

  </div>
</template>

<script setup>
import {useUserStore} from "@/store/user";
import {onMounted, ref} from "vue";
import RecentlyFaultSummary from "@/views/index/components/RecentlyFaultChart.vue";
import FaultSummaryByEquipmentType from "@/views/index/components/FaultSummaryByEquipmentType.vue";
import SummaryOfFaultType from "@/views/index/components/FaultTypeBarChart.vue";
import EquipmentsSummary from "@/views/index/components/EquipmentsStateSummary.vue";
import FaultSeverityDegreeSummary from "@/views/index/components/FaultSeverityDegreeSummary.vue";
import SystemInfo from "@/views/index/components/SystemInfo/index.vue";
import {useThemeStore} from "@/store/theme";
import {storeToRefs} from "pinia";
import * as userApi from "@/api/user"
//==========================================================================================
//                                        属性
//==========================================================================================
const userStore = useUserStore();
const themeStore = useThemeStore()

const {theme} = storeToRefs(themeStore);

const user = userStore.getUser;
let uri = user ? user.avatarUri : "default.jpg";
let avatarUrl = ref(userApi.getAvatar(uri));

const screenWidth = ref(window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth);
//==========================================================================================
//                                        方法
//==========================================================================================

const helloMessage = () => {
  let hour = new Date().getHours();
  let msg = "";
  // 早上-  6:00 ~  8:00
  // 上午-  8:00 ~ 11:00
  // 中午- 11:00 ~ 13:00
  // 下午- 13:00 ~ 18:00
  // 晚上- 18:00 ~ 24:00
  // 凌晨- 24:00 ~ 6:00
  if (6 < hour && hour <= 8) {
    msg = "早上好,";
  } else if (8 < hour && hour <= 11) {
    msg = "上午好,";
  } else if (11 < hour && hour <= 13) {
    msg = "中午好,";
  } else if (13 < hour && hour <= 18) {
    msg = "下午好,";
  } else if (18 < hour && hour <= 24) {
    msg = "晚上好,";
  } else {
    msg = "夜深了,注意休息,";
  }
  return msg + user.nickname + "。";
}

onMounted(() => {
  window.onresize = () => {
    screenWidth.value = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
  }
})

</script>

<style lang="scss" scoped>

.index-main-container {

  .row {

    @include mac13 {
      margin: 10px;
    }

    @include lg27 {
      margin: 20px;
    }

  }


  .box-card {
    overflow: hidden;

    @include mac13 {
    }

    @include lg27 {
      min-width: 480px;
    }

    .el-card__body {
      display: flex;
    }

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .card-title {
      font-size: 18px;
      font-weight: 700;
    }

    .card-icon {
      font-weight: 700;
    }

    .card-item {
      min-height: 80px;
      height: 80px;
      margin: 5px;
      min-width: 80px;
    }
  }


}

</style>
