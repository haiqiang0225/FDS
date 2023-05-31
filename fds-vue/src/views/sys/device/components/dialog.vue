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
        label-width="160px"
    >
      <el-form-item label="设备名称" prop="deviceName" :rows="4">
        <el-input v-model="form.deviceName"/>
      </el-form-item>

      <el-form-item label="设备类型" prop="deviceType" :rows="4">
        <el-select v-model="form.deviceType" class="m-2" placeholder="请选择" size="small">
          <el-option
              v-for="(item, index) in equipmentTypeList"
              :key="index"
              :label="item"
              :value="index"
          />
        </el-select>
      </el-form-item>


      <el-form-item label="通信协议类型" prop="communicationProtocol">
        <el-select v-model="form.communicationProtocol" class="m-2" placeholder="请选择" size="small">
          <el-option
              v-for="(item, index) in communicationProtocolList"
              :key="index"
              :label="item"
              :value="index"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="ipv4">
        <el-input v-model="form.ipv4"/>
      </el-form-item>


      <el-form-item label="ipv6" prop="ipv6">
        <el-input v-model="form.ipv6"/>
      </el-form-item>

      <el-form-item label="其它协议通信地址" prop="communicationPath">
        <el-input v-model="form.communicationPath"/>
      </el-form-item>

      <el-form-item label="设备状态" prop="deviceStatus">

        <el-select v-model="form.deviceStatus" class="m-2" placeholder="请选择" size="small">
          <el-option
              v-for="(item, index) in equipmentStateList"
              :key="index"
              :label="item"
              :value="index"
          />
        </el-select>

      </el-form-item>

      <el-form-item label="生产厂商" prop="manufacturer">
        <el-select v-model="form.manufacturer" class="m-2" placeholder="请选择" size="small">
          <el-option
              v-for="(item, index) in manufacturerList"
              :key="index"
              :label="item"
              :value="index"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="位置" prop="location">
        <el-input v-model="form.location"/>
      </el-form-item>

      <el-form-item label="父设备UUID" prop="parentDeviceId">
        <el-input v-model="form.parentDeviceId"/>
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
import * as deviceApi from "@/api/device"

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
      device: {
        type: Object,
        default: undefined,
        required: true
      }
    }
)

const equipmentStateList = ref([])

const manufacturerList = ref([])

const equipmentTypeList = ref([])

const communicationProtocolList = ref([])

equipmentStateList.value = deviceApi.getEquipmentStates();
manufacturerList.value = deviceApi.getManufacturerList();
equipmentTypeList.value = deviceApi.getEquipmentTypes();
communicationProtocolList.value = deviceApi.getCommunicationProtocolList();

const form = ref({
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
})


const rules = ref({
  deviceName: [
    {required: true, message: '请输入设备名'}
  ],
  deviceType: [
    {required: true, message: '请输入设备类型'}
  ],
})

const formRef = ref(null)


watch(
    () => props.dialogVisible,
    () => {
      form.value = props.device;
    }
)

const emits = defineEmits(['update:modelValue', 'initUserList'])

const handleClose = () => {
  emits('update:modelValue', false)
}

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      let formData = JSON.stringify(form.value);
      console.log(formData)
      let result = await deviceApi.addDevice(formData);
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
