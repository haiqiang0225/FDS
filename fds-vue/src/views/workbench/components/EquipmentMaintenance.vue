<template>

  <el-row style="height: 420px;">

    <el-col :span="6">
      <el-row style="margin: 10px 0 10px 0">

        <el-col :span="16">
          <el-input v-model="deviceId">
            <template #prepend>设备ID:</template>
          </el-input>
        </el-col>

        <el-col :span="8">
          <el-select v-model="maintenanceType" placeholder="维保类型">
            <el-option
                v-for="item in maintenanceTypes"
                :key="item"
                :label="item"
                :value="item"
            />
          </el-select>
        </el-col>

      </el-row>

      <el-row style="margin: 5px 0 5px 0;">
        <span style="font-size: 16px;">备注:</span>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-input v-model="notes" type="textarea" :rows="1" :resize="'none'" :autosize="{ minRows: 12, maxRows: 12 }">
          </el-input>
        </el-col>
      </el-row>


      <el-row style="margin: 5px 0 5px 0;">
        <el-col :span="2" :push="10">
          <el-button @click="handleSubmit">申请维保</el-button>
        </el-col>
      </el-row>

    </el-col>

    <div style="width: 30px;">
      <div class="split-line" style="">
      </div>
    </div>

    <el-col :span="17" :push="0">
      <el-row>
        <span style="font-size: 16px; font-weight: 400;">维保日志:</span>
      </el-row>

      <el-row style="margin: 10px 0 10px 0">

        <el-table
            :data="logsView.length > 0 ? logsView.slice((currentPage - 1) * pageSize, currentPage * pageSize): logs.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
            style="width: 100%"
            :row-class-name="tableRowClassName"
            @sort-change="sortChange"
            @filter-change="filterChange"
        >

          <el-table-column prop="equipmentId" sortable label="设备ID" width="300"/>
          <el-table-column prop="date" sortable label="日期" width="180"/>
          <el-table-column prop="msg" label="备注信息" width="600"/>
          <el-table-column column-key="maintenanceType" prop="type" label="维护类型" width="180"
                           :filters="maintenanceTypesFilter"/>

          <el-table-column column-key="processState" prop="state" label="处理状态" width="120"
                           :filters="maintenanceProcessStateFilter"/>

          <el-table-column label="操作">
            <template #default="scope">

              <el-button
                  size="small"
                  type="success"
                  @click="handleQueryLogInfo(scope.$index, scope.row)">查看
              </el-button
              >
            </template>
          </el-table-column>

        </el-table>

      </el-row>

      <el-row>

        <el-pagination
            small
            background
            layout="prev, pager, next, jumper"
            :current-page="currentPage"
            :page-size="pageSize"
            :total="logsView.length > 0 ? logsView.length : logs.length"
            class="mt-4"
            @update:current-page="selectPage"
        />
      </el-row>

    </el-col>

  </el-row>


</template>

<script setup>

import {ref} from "vue";
import MockApi from "@/utils/mockApi";
import mockApi from "@/utils/mockApi";
import getUuid from "@/utils/uuid";
import {ElMessage} from "element-plus";

const deviceId = ref('');
const notes = ref('');

const tableRowClassName = ({row, rowIndex}) => {
  let state = row.state.trim();

  //'已申请', '处理中', '维护完毕', '维护失败'
  if (state === '已申请') {
    return 'new-row';
  } else if (state === '处理中') {
    return 'processing-row';
  } else if (state === '维护完毕') {
    return 'success-row';
  } else if (state === '维护失败') {
    return 'warning-row';
  } else {
    return '';
  }

  // let c = rowIndex % 4;
  // if (c === 1) {
  //   return 'warning-row';
  // } else if (c === 2) {
  //   return '';
  // } else if (c === 3) {
  //   return 'success-row';
  // } else {
  //   return '';
  // }
}

const maintenanceType = ref('');

const maintenanceTypes = ref([]);

const maintenanceTypesFilter = ref([]);

MockApi.getMaintenanceTypes()
    .then(res => {
      maintenanceTypes.value = res.data;
      for (let i = 0; i < maintenanceTypes.value.length; i++) {
        maintenanceTypesFilter.value.push({
          text: maintenanceTypes.value[i],
          value: maintenanceTypes.value[i],
        });
      }
    })
    .catch(err => {
      console.log(err);
    })


// 分页
const pageSize = 8;

const currentPage = ref(1);

function selectPage(pageNumber) {
  currentPage.value = pageNumber;
}

const logs = ref([]);

mockApi.getMaintenanceLogs()
    .then(res => {
      logs.value = res.data;
    })
    .catch(err => {
      console.log(err)
    })


const logsView = ref([]);

const maintenanceProcessStates = ref([]);
const maintenanceProcessStateFilter = ref([]);

mockApi.getMaintenanceProcessStates()
    .then(res => {
      maintenanceProcessStates.value = res.data;
      for (let i = 0; i < maintenanceProcessStates.value.length; i++) {
        maintenanceProcessStateFilter.value.push({
          text: maintenanceProcessStates.value[i],
          value: maintenanceProcessStates.value[i],
        });
      }
    })
    .catch(err => {
      console.log(err);
    })


// 排序
function sortChange({prop, order}) {
  console.log(prop, order);
}

// 筛选
function filterChange(filters) {

  if (filters.maintenanceType) {     // 按维护类型筛选
    let res = [];
    for (let i = 0; i < logs.value.length; i++) {
      for (let j = 0; j < filters.maintenanceType.length; j++) {
        if (logs.value[i].type === filters.maintenanceType[j]) {
          console.log(logs.value[i].type)
          res.push(logs.value[i]);
        }
      }
    }
    logsView.value = res;

  } else if (filters.processState) { // 按处理状态筛选
    let res = [];
    for (let i = 0; i < logs.value.length; i++) {
      for (let j = 0; j < filters.processState.length; j++) {
        if (logs.value[i].state === filters.processState[j]) {
          res.push(logs.value[i]);
        }
      }
    }
    logsView.value = res;
  }
}


// 处理查询日志详情
function handleQueryLogInfo(index, row) {
  console.log(index, row);
}

function handleSubmit() {
  if (!deviceId.value || deviceId.value === '') {
    ElMessage.error("请输入设备ID.");
    return;
  }
  if (!maintenanceType.value || maintenanceType.value === '') {
    ElMessage.error("请选择维保类型.")
    return;
  }

  let d = new Date();
  let log = {
    date: `${d.getFullYear()}-${d.getMonth() + 1}-${d.getDate()} ${d.getHours()}:${d.getMinutes()}:${d.getSeconds()}`,
    equipmentId: deviceId.value,
    msg: "这是一条模拟的日志, 它的编号是: " + (logs.value.length + 1) + "。",
    state: '已申请',
    type: maintenanceType.value,
  }

  mockApi.addLog(log);
  logs.value.splice(0, 0, log);
  if (logsView.value.length > 0) {
    logsView.value.splice(0, 0, log);
  }
}

</script>

<style lang="scss" scoped>
.split-line {
  width: 2px;
  height: 100%;
  background-color: #e8e8e8;
  position: relative;
  left: 15px;
  border: unset;
}

:deep(.el-table .warning-row) {
  background: #ffd0c4;
}

:deep(.el-table .success-row) {
  background: #f0f9eb;
}

:deep(.el-table .processing-row) {
  background: #c3f2ff;
}

:deep(.el-table .new-row) {
  background: #fff7d6;
}
</style>
