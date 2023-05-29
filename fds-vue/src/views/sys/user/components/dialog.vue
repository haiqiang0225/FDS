<template>

  <el-dialog
      v-model="props.dialogVisible"
      :title="dialogTitle"
      width="30%"
      @close="handleClose"
  >
    <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
    >
      <el-form-item label="唯一标识" prop="username" :rows="4">
        <el-input v-model="form.username"/>
      </el-form-item>

      <el-form-item label="昵称" prop="nickname" :rows="4">
        <el-input v-model="form.nickname"/>
      </el-form-item>


      <el-form-item label="密码" prop="password" v-if="dialogTitle === '用户添加'">
        <el-input type="password" show-password v-model="form.password"/>
      </el-form-item>

      <el-form-item label="密码" v-if="dialogTitle === '用户修改'">
        <el-input type="password" show-password v-model="form.password"/>
      </el-form-item>


      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phoneNumber"/>
      </el-form-item>

      <el-form-item label="Email" prop="email">
        <el-input v-model="form.email"/>
      </el-form-item>

      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="form.gender">
          <el-radio label="男" value="0"/>
          <el-radio label="女" value="1"/>
          <el-radio label="保密" value="2"/>
        </el-radio-group>

      </el-form-item>

      <el-form-item label="账号状态" prop="status">
        <el-select v-model="form.status" class="m-2" placeholder="Select" size="small">
          <el-option
              v-for="item in userStates"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleConfirm">确认</el-button>
        <el-button @click="handleClose"
        >取消</el-button
        >
      </span>
    </template>
  </el-dialog>

</template>

<script setup>
import {defineEmits, defineProps, ref, watch} from "vue"
import requestUtil, {getRequestBaseUrl} from "@/utils/request";
import {ElMessage} from 'element-plus'
import qs from "qs";
import * as userApi from "@/api/user"

const props = defineProps(
    {
      id: {
        type: Number,
        default: -1,
        required: false
      },
      dialogTitle: {
        type: String,
        default: '',
        required: true
      },
      dialogVisible: {
        type: Boolean,
        default: false,
        required: true
      },
      user: {
        type: Object,
        default: undefined,
        required: true
      }
    }
)


const userStates = [
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

const form = ref({
  userId: null,
  username: null,
  nickname: null,
  password: null,
  phoneNumber: null,
  email: null,
  gender: 0,
  status: 0,
})


const rules = ref({
  username: [
    {required: true, message: '请输入唯一标识'}
  ],
  nickname: [
    {required: true, message: '请输入昵称'}
  ],
  password: [
    {required: true, message: '请输入密码'}
  ],
})

const formRef = ref(null)


watch(
    () => props.dialogVisible,
    () => {
      form.value = props.user;
    }
)

const emits = defineEmits(['update:modelValue', 'initUserList'])

const handleClose = () => {
  emits('update:modelValue', false)
}

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      // let formData = new FormData();
      // formData.set("sysUser", props.user);
      let formData = qs.stringify(form.value);
      console.log(formData)

      let result = await userApi.updateUser(formData);
      let data = result.data;
      if (data.code === 200) {
        ElMessage.success("执行成功！")
        formRef.value.resetFields();
        emits("initRoleList")
        handleClose();
      } else {
        ElMessage.error(data.msg);
      }
    } else {
      console.log("fail")
    }
  })
}

</script>

<style lang="scss" scoped>

</style>
