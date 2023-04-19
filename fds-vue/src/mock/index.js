import Mocks from "mockjs";
import {faultCounts, faultDegreeCounts, faultByType, faultCountsByEquipmentType} from "@/mock/recentFaults";

import {models} from "@/mock/models";

import {
    equipments,
    equipmentStates,
    proxyRandLoad,
    proxyAllEquipments,
    getEquipmentRunningStates
} from "@/mock/equipments";
import {maintenanceTypes, maintenanceProcessStates, logs, addLog} from "@/mock/maintenance";


Mocks.setup({timeout: '100-1000'});

const baseUrl = 'http://localhost:8080/mock'

// =============================================== 属性 ==========================================================


let today = new Date();

let oneDay = 1000 * 60 * 60 * 24;

let dates = [];
// =============================================== 日期(近7天) ==========================================================
for (let i = 0; i < 7; i++) {
    let day = new Date(+today - (oneDay * i));
    dates.splice(0, 0, `${day.getFullYear()}-${day.getMonth() + 1}-${day.getDate()}`)
}

// =============================================== 故障计数列表 =========================================================


// =============================================== 设备列表 =============================================================


// ============================================ 故障严重程度计数 =========================================================


// ================================================ Mocks ==============================================================
// ===================== 故障计数 ======================
Mocks.mock(baseUrl + '/faultCounts', () => {
    return [dates, faultCounts];
})

Mocks.mock(baseUrl + '/faultCountsByEquipmentType', () => {
    return faultCountsByEquipmentType;
})

Mocks.mock(baseUrl + '/faultCountsByFaultType', () => {
    return faultByType;
});

Mocks.mock(baseUrl + '/faultDegreeCounts', () => {
    return faultDegreeCounts;
});


Mocks.mock(baseUrl + '/getEquipmentStates', () => {
    return getEquipmentRunningStates();
});

// ===================== 维保 ======================
Mocks.mock(baseUrl + '/getMaintenanceTypes', () => {
    return maintenanceTypes;
});


Mocks.mock(baseUrl + '/getMaintenanceProcessStates', () => {
    return maintenanceProcessStates;
});

Mocks.mock(baseUrl + '/getMaintenanceLogs', () => {
    return logs;
});

Mocks.mock(baseUrl + '/addLog', 'post', (config) => {
    const body = JSON.parse(config.body);
    addLog(body);
    return {code: 200,};
})

Mocks.mock(baseUrl + '/getDevices', () => {
    return equipments;
})

Mocks.mock(baseUrl + '/getModels', () => {
    return models;
})
