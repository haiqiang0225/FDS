import http from "@/utils/request";

const urlPrefix = "/api/device";

export const equipmentStates = ['正常运行中', '停机', '维护中', '需要维护', '故障'];

const equipmentTypes = ['轴承', '基座'];

const manufacturerList = ['厂商A', '厂商B', '厂商C']

const communicationProtocols = ['tcp/ip'];

/********************************* 设备增删查改 *********************************/
export function getDeviceList(param) {
    return http.get(
        urlPrefix + "/list?" + param
    )
}

export function addDevice(device) {
    const headers = {'Content-Type': 'application/json;charset=utf-8'}
    return http.post(
        urlPrefix + "/add",
        device,
        {headers}
    )
}

/********************************* 查询状态 *********************************/

export function getEquipmentStates() {
    return equipmentStates
}

export function getEquipmentTypes() {
    return equipmentTypes;
}

export function getManufacturerList() {
    return manufacturerList;
}

export function getCommunicationProtocolList() {
    return communicationProtocols;
}