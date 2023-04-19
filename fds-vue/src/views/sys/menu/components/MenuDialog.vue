<template>
  <el-dialog
      :model-value="menuDialogVisible"
      title="分配角色"
      width="30%"
      @close="handleClose"
  >

    <el-form
        ref="formRef"
        :model="form"
        label-width="100px"
    >

      <el-tree
          ref="treeRef"
          :data="treeData"
          :props="defaultProps"
          show-checkbox
          :default-expand-all=true
          node-key="roleId"
          :check-strictly=true
      />

    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleConfirm">确认</el-button>
        <el-button @click="handleClose">取消</el-button>
      </span>
    </template>

  </el-dialog>
</template>

<script setup>

import {defineEmits, defineProps, ref, watch} from "vue";
import requestUtil from "@/utils/request";
import {ElMessage} from 'element-plus'
import qs from "qs";

const defaultProps = {
  children: 'childrenList',
  label: 'roleNameZh',
}


const props = defineProps(
    {
      user: {
        type: Object,
        default: undefined,
        required: true
      },
      menuDialogVisible: {
        type: Boolean,
        default: false,
        required: true
      }
    }
)

console.log(props.user);
const treeRef = ref(null)

const treeData = ref([])

const form = ref({
  id: -1
})


const formRef = ref(null)

const initFormData = async (param) => {
  console.log("initFormData");
  console.log(param);
  const res = await requestUtil.get("/api/sys/role/list?pageSize=-1");
  treeData.value = res.data.roleList;


  const res2 = await requestUtil.get("/api/sys/user/roles?" + qs.stringify(param));

  treeRef.value.setCheckedNodes(res2.data.roleList);

}


watch(
    () => props.menuDialogVisible,
    () => {
      let user = props.user;
      console.log("user = ");
      console.log(user)
      if (user) {
        initFormData(user);
      }
    }
)


const emits = defineEmits(['update:modelValue', 'initUserList'])

const handleClose = () => {
  emits('update:modelValue', false)
}

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
        if (valid) {
          let checkedRoleNodes = treeRef.value.getCheckedNodes();

          const headers = {'Content-Type': 'application/json;charset=utf-8'}

          let result = await requestUtil.post("/api/sys/user/updateRoles?" + qs.stringify(props.user), checkedRoleNodes,
              {headers});

          let data = result.data;
          if (data.code === 200) {
            ElMessage.success("执行成功！")
            emits("initUserList")
            handleClose();
          } else {
            ElMessage.error(data.msg);
          }
        }
        else {
          console.log("fail")
        }
      }
  )
}

</script>

<style scoped>

</style>
