<template>
  <el-dialog
      :model-value="menuDialogVisible"
      title="分配权限"
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
          node-key="menuId"
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

const defaultProps = {
  children: 'childrenList',
  label: 'nameZh',
}


const props = defineProps(
    {
      roleId: {
        type: Number,
        default: -1,
        required: true
      },
      menuDialogVisible: {
        type: Boolean,
        default: false,
        required: true
      },
      roleName: {
        type: String,
        required: true,
      }
    }
)

const treeRef = ref(null)

const treeData = ref([])

const form = ref({
  id: -1
})


const formRef = ref(null)

const initFormData = async (id) => {
  const res = await requestUtil.get("/api/sys/menu/treeList");
  treeData.value = res.data.menuTree;

  form.value.id = id;

  const res2 = await requestUtil.get("/api/sys/role/menus/" + id + '/' + props.roleName);
  treeRef.value.setCheckedNodes(res2.data.menuTree);
}


function convert2TreeObj(obj) {
  return {
    id: obj.menuId,
    label: obj.nameZh,
    children: [],
  };
}


watch(
    () => props.menuDialogVisible,
    () => {
      let id = props.roleId;
      console.log("id=" + id)
      if (id !== -1) {
        initFormData(id)
      }
    }
)


const emits = defineEmits(['update:modelValue', 'initRoleList'])

const handleClose = () => {
  emits('update:modelValue', false)
}

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      let menuNodes = treeRef.value.getCheckedNodes();
      console.log(menuNodes);

      // let result = await requestUtil.post("sys/role/updateMenus/" + form.value.id, menuIds);
      // let data = result.data;
      // if (data.code === 200) {
      //   ElMessage.success("执行成功！")
      //
      //   emits("initRoleList")
      //   handleClose();
      // } else {
      //   ElMessage.error(data.msg);
      // }
    } else {
      console.log("fail")
    }
  })
}

</script>

<style scoped>

</style>
