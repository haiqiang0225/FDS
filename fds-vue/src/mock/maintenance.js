import getUuid from "@/utils/uuid";

export const maintenanceTypes = ['预防性维护', '故障修复', '设备更换', '升级性维护', '设备定期保养'];

export const maintenanceProcessStates = ['已申请', '处理中', '维护完毕', '维护失败'];

export let logs = JSON.parse(localStorage.getItem("logs"));


// 添加模拟的日志
let today = new Date();


if (!logs) {
    logs = [];
    for (let i = 0; i < 100; i++) {
        let d = new Date(+today + i * 1000)
        logs.push({
            id: i,
            equipmentId: getUuid(),
            date: `${d.getFullYear()}-${d.getMonth() + 1}-${d.getDate()} ${d.getHours()}:${d.getMinutes()}:${d.getSeconds()}`,
            msg: "这是一条模拟的日志, 它的编号是: " + i + "。",
            state: maintenanceProcessStates[i % maintenanceProcessStates.length],
            type: maintenanceTypes[i % maintenanceTypes.length],
        })
    }
}

export function addLog(log) {
    log.id = logs.length + 1;
    logs.splice(0, 0, log);
    localStorage.setItem("logs", JSON.stringify(logs));
}
