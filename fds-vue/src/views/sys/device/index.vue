<template>
  <div class="app-container">

    <div style="border: 1px solid #dadada; border-radius: 7px;padding: 3px;">
      <el-row :gutter="20" class="header">
        <el-col :span="4">
          <el-input placeholder="请输入设备名..." v-model="queryForm.query" clearable></el-input>
        </el-col>
        <el-button type="primary" :icon="Search" @click="initDeviceList">搜索</el-button>
        <el-button type="success" :icon="DocumentAdd" @click="handleDialogValue(undefined)">新增</el-button>
        <el-popconfirm title="您确定批量删除这些记录吗？" @confirm="handleDelete(null)">
          <template #reference>
            <el-button type="danger" :disabled="delBtnStatus" :icon="Delete">批量删除</el-button>
          </template>
        </el-popconfirm>
      </el-row>


      <el-table :data="tableData"
                stripe
                default-expand-all
                row-key="deviceId"
                style="width: 100%; "
                @selection-change="handleSelectionChange"
                :tree-props="{ children: 'childrenList' }"
      >

        <el-table-column type="selection"/>

        <el-table-column prop="deviceId" label="UUID" align="center" show-overflow-tooltip>

          <template #default="scope">
            <el-button size="small"
                       :text="true"
                       style="width: 12px;"
                       @click="copyToClipBoard(scope.row.deviceId)">
              <el-icon>
                <svg-icon icon="copy"/>
              </el-icon>
            </el-button>
            {{ scope.row.deviceId }}

          </template>
        </el-table-column>

        <el-table-column prop="deviceName" label="设备名称" align="center"/>

        <el-table-column prop="deviceType" label="设备类型" align="center">
          <template #default="scope">

            {{ equipmentTypeList[scope.row.deviceType] }}

          </template>
        </el-table-column>

        <el-table-column prop="communicationProtocol" label="通信协议" align="center"/>

        <el-table-column prop="ipv4" label="ipv4" align="center"/>

        <el-table-column prop="ipv6" label="ipv6" align="center"/>

        <el-table-column prop="communicationPath" label="其它协议通讯地址" align="center"/>

        <el-table-column prop="manufacturer" label="生产厂商" align="center">
          <template #default="scope">

            {{ manufacturerList[scope.row.manufacturer] }}

          </template>
        </el-table-column>

        <el-table-column prop="location" label="设备位置" align="center"/>

        <el-table-column prop="deviceStatus" label="设备运行状态" align="center">

          <template #default="scope">
            <el-tag
                :type="calcTagType(scope.row.deviceStatus)"
                disable-transitions>

              {{ equipmentStateList[scope.row.deviceStatus] }}

            </el-tag>
          </template>

        </el-table-column>


        <el-table-column prop="action" label="操作" fixed="right" align="center" width="200">
          <template v-slot="scope">

            <el-button type="success" :icon="DocumentAdd"
                       :style="{'visibility' :scope.row.username === 'root' ? 'hidden': 'unset'}"
                       @click="handleMenuDialogValue(scope.row)">
            </el-button>

            <el-button v-if="showEditButton(scope.row.username)" type="primary" :icon="Edit"
                       @click="handleDialogValue(scope.row)"/>

            <el-popconfirm v-if="showDeleteButton(scope.row.deviceId)" title="您确定要删除这条记录吗？"
                           @confirm="handleDelete(scope.row.deviceId)">
              <template #reference>
                <el-button type="danger" :icon="Delete"/>
              </template>

            </el-popconfirm>

          </template>
        </el-table-column>

      </el-table>


      <el-pagination
          v-model:currentPage="queryForm.pageNum"
          v-model:page-size="queryForm.pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

    </div>
  </div>

  <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :dialogTitle="dialogTitle"
          :device="device"
          @initUserList="initDeviceList"/>

  <UserDialog v-model="menuDialogVisible" :menuDialogVisible="menuDialogVisible" :user="device"
              @initUserList="initDeviceList"></UserDialog>
</template>

<script setup>
import {ref, watch} from 'vue';
import requestUtil from "@/utils/request";
import {Search, Delete, DocumentAdd, Edit, Tools, RefreshRight} from '@element-plus/icons-vue'
import Dialog from './components/dialog'
import {ElMessage} from 'element-plus'
import UserDialog from './components/DeviceDialog.vue'
import * as deviceApi from "@/api/device"
import qs from "qs";
import {copyToClipBoard} from "@/utils/copy";

const tableData = ref([])

const total = ref(0)

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})

const currentPage = ref(1);
const pageSize = ref(10);


let device = ref({});

const dialogVisible = ref(false)

const dialogTitle = ref("")

const delBtnStatus = ref(true)

const multipleSelection = ref([])

const menuDialogVisible = ref(false)

const equipmentStateList = ref([])

const equipmentTypeList = ref([])

const manufacturerList = ref([])

const handleSelectionChange = (selection) => {
  multipleSelection.value = selection;
  delBtnStatus.value = selection.length === 0;
}

const handleMenuDialogValue = (prop) => {
  device.value = prop;
  menuDialogVisible.value = true
}

const initDeviceList = async () => {
  equipmentStateList.value = deviceApi.getEquipmentStates();
  equipmentTypeList.value = deviceApi.getEquipmentTypes();
  manufacturerList.value = deviceApi.getManufacturerList();
  let res = await deviceApi.getDeviceList(qs.stringify({
    "start": (queryForm.value.pageNum - 1) * pageSize.value,
    "count": pageSize.value,
    "keyword": undefined
  }))
  tableData.value = res.data.deviceList;
  total.value = res.data.count;
}

initDeviceList();

const handleSizeChange = (size) => {
  queryForm.value.pageNum = 1;
  queryForm.value.pageSize = size;
  pageSize.value = size;
  initDeviceList();
}

const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum;
  currentPage.value = pageNum;
  initDeviceList();
}

const handleDialogValue = (row) => {
  if (row) {
    device.value = {
      deviceId: row.deviceId,
      deviceName: row.deviceName,
      deviceType: row.deviceType,
      communicationProtocol: row.communicationProtocol,
      ipv4: row.ipv4,
      ipv6: row.ipv6,
      communicationPath: row.communicationPath,
      deviceStatus: row.deviceStatus,
      manufacturer: row.manufacturer,
      location: row.location,
      parentDeviceId: row.parentDeviceId,
    };

    dialogTitle.value = "修改信息"
  } else {
    device.value = {
      deviceId: null,
      deviceName: null,
      deviceType: 0,
      communicationProtocol: "tcp/ip",
      ipv4: "localhost",
      ipv6: "::1",
      communicationPath: null,
      deviceStatus: 0,
      manufacturer: 0,
      location: null,
      parentDeviceId: null,
    };
    dialogTitle.value = "添加设备"
  }
  dialogVisible.value = true
}

watch(
    () => dialogVisible.value,
    (value, oldValue, onCleanup) => {
      if (value === false && oldValue === true) {
        initDeviceList();
      }
    }
)

const handleDelete = async (id) => {

  let ids = []
  if (id) {
    ids.push(id)
  } else {
    ElMessage.error("不允许批量删除")
    return;
  }
  let formData = new FormData();
  formData.set("ids", ids.join(','));
  // todo:
  const res = await requestUtil.post("/api/sys/user/delete", formData);

  if (res.data.code === 200) {
    ElMessage({
      type: 'success',
      message: '执行成功!'
    })
    await initDeviceList();
  } else {
    ElMessage({
      type: 'error',
      message: res.data.msg,
    })
  }
}

function showEditButton(code) {
  return code !== 'root';
}

function showDeleteButton(code) {
  return code !== 'root';
}

function calcTagType(label) {
  if (label === '正常运行中') {
    return 'success';
  } else if (label === '故障') {
    return 'danger';
  } else if (label === '停机') {
    return 'info';
  } else if (label === '维护中') {
    return '';
  } else if (label === '需要维护') {
    return 'warning';
  }

  return '';
}

</script>

<style lang="scss" scoped>
.app-container {
  padding: 15px;
}

.header {
  padding-bottom: 16px;
  box-sizing: border-box;
}

.el-pagination {
  float: right;
  padding: 20px;
  box-sizing: border-box;
}

:deep(th.el-table__cell) {
  word-break: break-word;
  background-color: #f8f8f9 !important;
  color: #515a6e;
  height: 40px;
  font-size: 13px;

}

.el-tag--small {
  margin-left: 5px;
}
</style>
