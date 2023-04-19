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


      <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
                stripe
                default-expand-all
                row-key="id"
                style="width: 100%; "
                @selection-change="handleSelectionChange"
                :tree-props="{ children: 'childrenList' }"
      >

        <el-table-column type="selection"/>
        <el-table-column prop="name" label="设备名称" align="center"/>

        <el-table-column prop="type" label="设备类型" align="center"/>

        <el-table-column prop="communicationProtocol" label="通信协议" align="center"/>

        <el-table-column prop="ipv4" label="ipv4" align="center"/>

        <el-table-column prop="ipv6" label="ipv6" align="center"/>

        <el-table-column prop="communicationPath" label="其它协议通讯地址" align="center"/>

        <el-table-column prop="manufacturer" label="生产厂商" align="center"/>

        <el-table-column prop="location" label="设备位置" align="center"/>

        <el-table-column prop="state" label="设备运行状态" align="center">

          <template #default="scope">
            <el-tag
                :type="calcTagType(scope.row.state)"
                disable-transitions
            >{{ scope.row.state }}
            </el-tag
            >
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

            <el-popconfirm v-if="showDeleteButton(scope.row.username)" title="您确定要删除这条记录吗？"
                           @confirm="handleDelete(scope.row.roleId)">
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
          :user="user"
          @initUserList="initDeviceList"/>

  <UserDialog v-model="menuDialogVisible" :menuDialogVisible="menuDialogVisible" :user="user"
              @initUserList="initDeviceList"></UserDialog>
</template>

<script setup>
import {ref} from 'vue';
import requestUtil, {getRequestBaseUrl} from "@/utils/request";
import {Search, Delete, DocumentAdd, Edit, Tools, RefreshRight} from '@element-plus/icons-vue'
import Dialog from './components/dialog'
import {ElMessage, ElMessageBox} from 'element-plus'
import UserDialog from './components/DeviceDialog.vue'
import qs from "qs"
import mockApi from "@/utils/mockApi";
import {equipments} from "@/mock/equipments";

const tableData = ref([])

const total = ref(0)

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})

const currentPage = ref(1);
const pageSize = ref(10);

const deviceList = ref([]);

let user = ref({});

const dialogVisible = ref(false)

const dialogTitle = ref("")

const delBtnStatus = ref(true)

const multipleSelection = ref([])

const sysRoleList = ref([])

const menuDialogVisible = ref(false)

const handleSelectionChange = (selection) => {
  console.log("勾选了")
  console.log(selection)
  multipleSelection.value = selection;
  delBtnStatus.value = selection.length === 0;
}

const handleMenuDialogValue = (prop) => {
  user.value = prop;

  menuDialogVisible.value = true
}

const initDeviceList = async () => {
  mockApi.getDeviceList()
      .then(res => {
        // console.log(res.data);
        tableData.value = res.data;
        total.value = tableData.value.length;
      })
  // const res = await requestUtil.get("/api/sys/menu/treeList?" + qs.stringify(queryForm.value));
  // tableData.value = res.data.menuTree;
  // console.log(res.data.menuTree)
  // total.value = res.data.total;
  // console.log(total.value)
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
    user.value = {
      userId: row.userId,
      username: row.username,
      nickname: row.nickname,
      phoneNumber: row.phoneNumber,
      email: row.email,
      gender: row.gender,
      status: row.status,
    };

    dialogTitle.value = "用户修改"
  } else {
    user.value = {
      userId: null,
      username: null,
      nickname: null,
      password: null,
      phoneNumber: null,
      email: null,
      gender: 0,
      status: 0,
    };
    dialogTitle.value = "用户添加"
  }
  dialogVisible.value = true
}

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

const handleResetPassword = async (id) => {
  const res = await requestUtil.get("/api/sys/user/resetPassword/" + id)
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

const statusChangeHandle = async (row) => {
  let res = await requestUtil.get("/api/sys/user/updateStatus/" + row.id + "/status/" + row.status);
  if (res.data.code === 200) {
    ElMessage({
      type: 'success',
      message: '执行成功!'
    })
  } else {
    ElMessage({
      type: 'error',
      message: res.data.msg,
    })
    await initDeviceList();
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
