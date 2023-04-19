<template>
  <div class="app-container">

    <div style="border: 1px solid #dadada; border-radius: 7px;padding: 3px;">
      <el-row :gutter="20" class="header">
        <el-col :span="4">
          <el-input placeholder="请输入用户名..." v-model="queryForm.query" clearable></el-input>
        </el-col>
        <el-button type="primary" :icon="Search" @click="initUserList">搜索</el-button>
        <el-button type="success" :icon="DocumentAdd" @click="handleDialogValue(undefined)">新增</el-button>
        <el-popconfirm title="您确定批量删除这些记录吗？" @confirm="handleDelete(null)">
          <template #reference>
            <el-button type="danger" :disabled="delBtnStatus" :icon="Delete">批量删除</el-button>
          </template>
        </el-popconfirm>
      </el-row>


      <el-table :data="tableData"
                stripe
                row-key="username"
                style="width: 100%; "
                @selection-change="handleSelectionChange">

        <el-table-column type="selection" width="55"/>
        <el-table-column prop="username" label="用户唯一标识" width="150" align="center"/>
        <el-table-column prop="nickname" label="昵称" width="150" align="center"/>
        <el-table-column prop="phoneNumber" label="手机号" width="100" align="center"/>
        <el-table-column prop="registerTime" label="账号创建时间" align="center"/>
        <el-table-column prop="registerIp" label="创建IP地址" width="100" align="center"/>
        <el-table-column prop="creatorUsername" label="创建者" width="100" align="center"/>
        <el-table-column prop="gender" label="性别" width="100" align="center">
          <template #default="scope">
            <el-tag
                :type="calcTagType(genders[scope.row.gender])"
                disable-transitions
            >{{ genders[scope.row.gender] }}
            </el-tag
            >
          </template>
        </el-table-column>

        <el-table-column prop="gender" label="账号状态" width="100">
          <template #default="scope">
            <el-tag
                :type="calcTagType(accountStates[scope.row.status].label)"
                disable-transitions
            >{{ accountStates[scope.row.status].label }}
            </el-tag
            >
          </template>
        </el-table-column>

        <el-table-column prop="action" label="操作" width="300" fixed="right" align="center">
          <template v-slot="scope">

            <el-button type="primary" :icon="Tools"
                       :style="{'visibility' :scope.row.username === 'root' ? 'hidden': 'unset'}"
                       @click="handleMenuDialogValue(scope.row)">分配角色
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
          @initUserList="initUserList"/>

  <UserDialog v-model="menuDialogVisible" :menuDialogVisible="menuDialogVisible" :user="user"
              @initUserList="initUserList"></UserDialog>

  <el-button @click="testSendJson">Test</el-button>
</template>

<script setup>
import {ref} from 'vue';
import requestUtil, {getRequestBaseUrl} from "@/utils/request";
import {Search, Delete, DocumentAdd, Edit, Tools, RefreshRight} from '@element-plus/icons-vue'
import Dialog from './components/dialog'
import {ElMessage, ElMessageBox} from 'element-plus'
import UserDialog from './components/UserDialog.vue'
import qs from "qs"


const tableData = ref([])

const total = ref(0)

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})

let user = ref({});

const genders = ["保密", "男", "女"];

const accountStates = [
  {
    value: 0,
    label: "正常",
  },
  {
    value: 1,
    label: "冻结",
  },
  {
    value: 2,
    label: "注销",
  },
  {
    value: 3,
    label: "暂时封禁",
  },
  {
    value: 4,
    label: "永久封禁",
  },

]

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

const initUserList = async () => {
  const res = await requestUtil.get("/api/sys/user/list?" + qs.stringify(queryForm.value));
  tableData.value = res.data.userList;
  console.log(tableData.value);
  total.value = res.data.total;
  console.log(total.value)
}

initUserList();

const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1;
  queryForm.value.pageSize = pageSize;
  initUserList();
}

const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum;
  initUserList();
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
    await initUserList();
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
    await initUserList();
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
    await initUserList();
  }
}

function calcTagType(label) {
  if (label === "正常") {
    return "success";
  } else if (label === "冻结" || label === "男") {
    return "";
  } else if (label === "注销" || label === "保密") {
    return "info";
  } else if (label === "暂时封禁") {
    return "warning";
  } else if (label === "永久封禁" || label === "女 ") {
    return "danger";
  } else {
    return "";
  }
}


function showEditButton(code) {
  return code !== 'root';
}

function showDeleteButton(code) {
  return code !== 'root';
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
