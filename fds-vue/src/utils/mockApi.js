import http from "./request.js";
import {maintenanceProcessStates, maintenanceTypes} from "@/mock/maintenance";

export default {

    // 获取故障计数
    getFaultCounts() {
        return http({
            url: '/mock/faultCounts',
            method: "get",
        });
    },

    // 按照设备类型获取故障数量
    getFaultCountsByEquipmentType() {
        return http({
            url: '/mock/faultCountsByEquipmentType',
            method: 'get',
        });
    },

    // 按照设备类型获取故障数量
    getFaultCountsByFaultType() {
        return http({
            url: '/mock/faultCountsByFaultType',
            method: 'get',
        });
    },
    getFaultCountsByDegree() {
        return http({
            url: '/mock/faultDegreeCounts',
            method: 'get',
        })
    },

    getMaintenanceTypes() {
        return http({
            url: '/mock/getMaintenanceTypes',
            method: 'get'
        })
    },

    getMaintenanceProcessStates() {
        return http({
            url: '/mock/getMaintenanceProcessStates',
            method: 'get'
        })
    },

    getMaintenanceLogs() {
        return http({
            url: '/mock/getMaintenanceLogs',
            method: 'get'
        })
    },
    addLog(log) {

        return http({
            url: '/mock/addLog',
            method: 'post',
            data: JSON.stringify(log),
        })
    },
    getDeviceList() {
        return http({
            url : '/mock/getDevices',
            method: 'get',
        })
    },
    getModelList() {
        return http({
            url: "/mock/getModels",
            method: 'get',
        })
    },

}
