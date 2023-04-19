<template>
  <div class="info-container">
    <div class="info-item">
      <!--   todo:点击系统版本号跳转到/弹出更新timeline组件   -->
      <span>系统版本号:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" @click="openUpdateTimeLine" style="color:#5ca1e6; cursor: pointer;"> {{ "0.0.1-BETA" }} </a> </span>
    </div>

    <div class="info-item">
      <span>系统运行时间:&nbsp;&nbsp;{{ webRunTime }}</span>
    </div>

    <div class="info-item">
      <!--  todo: 从后端获取实时在线人数    -->
      <span>当前在线人数:&nbsp;&nbsp;{{ "1" }}</span>
    </div>

    <div class="info-item">
      <el-button type="success" @click="checkUpdates"> 检查更新</el-button>
    </div>


  </div>
</template>

<script setup>
import {ElMessage} from "element-plus";
import {onMounted, onUnmounted, ref} from "vue";

//==========================================================================================
//                                        属性
//==========================================================================================

// 网站统计信息
const webRunTime = ref('');
let timeInterval = null;

const webStartTime = new Date('2023/4/13 00:00:00');
const now = new Date();
let dateDiff = Math.abs(now - webStartTime);


//==========================================================================================
//                                        方法
//==========================================================================================
let msToFormatDate = function (ms) {
  if (ms) {
    let day = Math.floor(ms / (24 * 60 * 60 * 1000));
    let hour = Math.floor((ms / (60 * 60 * 1000) - day * 24));
    let min = Math.floor(ms / (60 * 1000) - day * 24 * 60 - hour * 60);
    let sec = Math.floor(ms / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

    return `${day} 天 ${hour} 小时 ${min} 分 ${sec}秒`;
  } else {
    return ""
  }
};

onMounted(() => {
  timeInterval = setInterval(() => {
    dateDiff += 1000;
    webRunTime.value = msToFormatDate(dateDiff);
  }, 1000);
});

onUnmounted(() => {
  // 清除定时器
  timeInterval != null ? clearInterval(timeInterval) : null;
})


const checkUpdates = (currentVersion) => {

  //todo: 检查版本号功能
  let rand = Math.random() * 10;
  if (rand < -10) {
    ElMessage.warning("有新的版本,请及时更新。");
  } else {
    ElMessage.success("已经是最新版本!");
  }
}

const openUpdateTimeLine = () => {
  ElMessage.error("Timeline组件暂不支持")
}

</script>

<style lang="scss" scoped>
.info-container {

  .info-item {
    font-size: 16px;
    padding: 10px;
  }
}
</style>
