<template>
  <div class="app-container">

    <div style="border: 1px solid #dadada; border-radius: 7px;padding: 3px;">
      <el-row :gutter="20" class="header">
        <el-col :span="4">
          <el-input placeholder="请输入角色名..." v-model="queryForm.query" clearable></el-input>
        </el-col>
        <el-button type="primary" :icon="Search" @click="initRoleList">搜索</el-button>
        <el-button type="success" :icon="DocumentAdd" @click="handleDialogValue(undefined)">新增</el-button>
        <el-popconfirm title="您确定批量删除这些记录吗？" @confirm="handleDelete(null)">
          <template #reference>
            <el-button type="danger" :disabled="delBtnStatus" :icon="Delete">批量删除</el-button>
          </template>
        </el-popconfirm>
      </el-row>


      <el-table :data="tableData"
                stripe
                style="width: 100%; "
                row-key="roleName"
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="roleNameZh" label="角色名" width="180" align="center"/>
        <el-table-column prop="roleName" label="角色标识符" width="200" align="center"/>
        <el-table-column prop="createTime" label="创建时间" width="200" align="center"/>

        <el-table-column prop="remark" label="备注"/>

        <el-table-column prop="action" label="操作" width="300" fixed="right" align="center">
          <template v-slot="scope">

            <el-button type="primary" :icon="Tools"
                       :style="{'visibility' :scope.row.roleName === 'root' ? 'hidden': 'unset'}"
                       @click="handleMenuDialogValue(scope.row.roleId, scope.row.roleName)">分配权限
            </el-button>

            <el-button v-if="showEditButton(scope.row.roleName)" type="primary" :icon="Edit"
                       @click="handleDialogValue(scope.row.roleId)"/>

            <el-popconfirm v-if="showDeleteButton(scope.row.roleName)" title="您确定要删除这条记录吗？"
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

  <Dialog v-model="dialogVisible" :dialogVisible="dialogVisible" :id="roleId" :dialogTitle="dialogTitle"
          @initRoleList="initRoleList"/>

  <MenuDialog v-model="menuDialogVisible" :menuDialogVisible="menuDialogVisible" :roleId="roleId" :role-name="roleName"
              @initRoleList="initRoleList"></MenuDialog>
</template>

<script setup>
import {ref} from 'vue';
import requestUtil, {getRequestBaseUrl} from "@/utils/request";
import {Search, Delete, DocumentAdd, Edit, Tools, RefreshRight} from '@element-plus/icons-vue'
import Dialog from './components/dialog'
import {ElMessage, ElMessageBox} from 'element-plus'
import MenuDialog from './components/RoleDialog.vue'
import qs from "qs"
import * as roleApi from "@/api/role"

const tableData = ref([])

const total = ref(0)

const queryForm = ref({
  query: '',
  pageNum: 1,
  pageSize: 10
})

const dialogVisible = ref(false)

const dialogTitle = ref("")

const roleId = ref(-1)

const roleName = ref('');

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

const handleMenuDialogValue = (id, name) => {
  // console.log("roleId=" + id, ", roleName = " + name);
  roleId.value = id;
  roleName.value = name;
  menuDialogVisible.value = true
}

const initRoleList = async () => {
  const res = await roleApi.queryRoleList(qs.stringify(queryForm.value));
  tableData.value = res.data.roleList;
  total.value = res.data.total;
}

initRoleList();

const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1;
  queryForm.value.pageSize = pageSize;
  initRoleList();
}

const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum;
  initRoleList();
}

const handleDialogValue = (id) => {
  if (id) {
    roleId.value = id;
    dialogTitle.value = "角色修改"
  } else {
    roleId.value = -1;
    dialogTitle.value = "角色添加"
  }
  dialogVisible.value = true
}

const handleDelete = async (id) => {

  let ids = []
  if (id) {
    ids.push(id)
  } else {
    // multipleSelection.value.forEach(row => {
    //   ids.push(row.roleId)
    // })
    //
    ElMessage.error("不允许批量删除")
    return;
  }
  let formData = new FormData();
  formData.set("ids", ids.join(','));
  const res = await roleApi.deleteRole(formData);

  if (res.data.code === 200) {
    ElMessage({
      type: 'success',
      message: '执行成功!'
    })
    await initRoleList();
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
