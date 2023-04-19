<template>
  <div class="app-container">

    <div style="border: 1px solid #dadada; border-radius: 7px;padding: 3px;">
      <el-row :gutter="20" class="header">
        <el-col :span="4">
          <el-input placeholder="请输入权限名..." v-model="queryForm.query" clearable></el-input>
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
                default-expand-all
                row-key="path"
                style="width: 100%; "
                @selection-change="handleSelectionChange"
                :tree-props="{ children: 'childrenList' }"
      >

        <el-table-column type="selection" width="55"/>
        <el-table-column prop="nameZh" label="权限名称" width="180" align="center"/>

        <el-table-column prop="menuName" label="权限标识符" align="center"/>

        <el-table-column prop="perms" label="权限代码" width="200" align="center"/>

        <el-table-column prop="path" label="路径" align="center"/>

        <el-table-column prop="component" label="组件" align="center"/>

        <el-table-column prop="icon" label="icon" width="50" align="center">

          <template #default="scope">
            <el-icon v-if="scope.row.icon">
              <svg-icon style="height: 100%; width: 100%; fill: black !important;color: black;"
                        :icon="scope.row.icon"/>
            </el-icon>
          </template>
        </el-table-column>

        <el-table-column prop="hidden" label="是否在页面上显示" width="160" align="center">
          <template #default="scope">
            <el-tag
                :type="scope.row.hidden ? 'danger': ''"
                disable-transitions
            >{{ scope.row.hidden ? '否' : '是' }}
            </el-tag
            >
          </template>
        </el-table-column>

        <el-table-column prop="enable" label="是否可用" width="80">
          <template #default="scope">
            <el-tag
                :type="scope.row.enable ? 'success': 'danger'"
                disable-transitions
            >{{ scope.row.enable ? '是' : '否' }}
            </el-tag
            >
          </template>
        </el-table-column>

        <el-table-column prop="action" label="操作" width="200" fixed="right" align="center">
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


    </div>
  </div>

  <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :dialogTitle="dialogTitle"
          :user="user"
          @initUserList="initUserList"/>

  <UserDialog v-model="menuDialogVisible" :menuDialogVisible="menuDialogVisible" :user="user"
              @initUserList="initUserList"></UserDialog>
</template>

<script setup>
import {ref} from 'vue';
import requestUtil, {getRequestBaseUrl} from "@/utils/request";
import {Search, Delete, DocumentAdd, Edit, Tools, RefreshRight} from '@element-plus/icons-vue'
import Dialog from './components/dialog'
import {ElMessage, ElMessageBox} from 'element-plus'
import UserDialog from './components/MenuDialog.vue'
import qs from "qs"

const tableData = ref([])

const total = ref(0)

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})

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

const initUserList = async () => {
  const res = await requestUtil.get("/api/sys/menu/treeList?" + qs.stringify(queryForm.value));
  tableData.value = res.data.menuTree;
  console.log(res.data.menuTree)
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


function showEditButton(code) {
  return code !== 'root';
}

function showDeleteButton(code) {
  return code !== 'root';
}

function calcTagType(label) {

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
