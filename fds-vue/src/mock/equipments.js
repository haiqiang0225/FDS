import getUuid from "@/utils/uuid";
import {HashMap} from "@/utils/collections";

export class Proxy {
    load

    constructor(load) {
        this.load = load
    }
}

// 设备运行状态
export const equipmentStates = ['正常运行中', '停机', '维护中', '需要维护', '故障'];
export const equipmentTypes = ['轴承', '基座'];
export const communicationProtocols = ['tcp/ip'];

// =============================================== 设备统计 ======================================================
export let equipments = [
    {
        id: getUuid(),
        name: '生产设备A',
        type: '整机',
        communicationProtocol: communicationProtocols[0],
        ipv4: '127.0.0.1',
        ipv6: '',
        communicationPath: '',
        state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
        manufacturer: '测试厂商',
        location: '机房A',
        parent_device_name: '',
        loadCpu: new Proxy(1),
        loadMemory: new Proxy(1),
        childrenList: [
            {
                id: getUuid(),
                name: '组件A',
                type: '组件类型A',
                communicationProtocol: communicationProtocols[0],
                ipv4: '127.0.0.1',
                ipv6: '',
                communicationPath: '',
                state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
                manufacturer: '测试厂商',
                location: '机房A',
                parent_device_name: '',
                loadCpu: new Proxy(1),
                loadMemory: new Proxy(1),
                childrenList: [],

            },
            {
                id: getUuid(),
                name: '组件B',
                type: '组件类型B',
                communicationProtocol: communicationProtocols[0],
                ipv4: '127.0.0.1',
                ipv6: '',
                communicationPath: '',
                state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
                manufacturer: '测试厂商',
                location: '机房A',
                parent_device_name: '',
                loadCpu: new Proxy(1),
                loadMemory: new Proxy(1),
                childrenList: [],

            },
            {
                id: getUuid(),
                name: '组件C',
                type: '组件类型C',
                communicationProtocol: communicationProtocols[0],
                ipv4: '127.0.0.1',
                ipv6: '',
                communicationPath: '',
                state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
                manufacturer: '测试厂商',
                location: '机房A',
                parent_device_name: '',
                loadCpu: new Proxy(1),
                loadMemory: new Proxy(1),
                childrenList: [],

            },
        ],

    },
];

export function addEquipment(equipment) {
    equipments.push(equipment);
}

export function proxyRandLoad(proxy) {
    setInterval(() => {
        let curLoad = proxy.load + (Math.random() - 0.45) * 5;
        curLoad = curLoad > 0 ? curLoad : curLoad + Math.random() * 2.7;
        curLoad = curLoad < 95 ? curLoad : curLoad + (Math.random() - 1.05) * 5;
        proxy.load = curLoad;
    }, 1000);
}

export function proxyAllEquipments(equipments) {
    for (let i = 0; i < equipments.length; i++) {
        let equipment = equipments[i];
        proxyRandLoad(equipment.loadMemory);
        proxyRandLoad(equipment.loadCpu);
    }
}

// =============================================== 设备运行状态统计 ======================================================

const runningStateMap = new HashMap();

// 每种运行状态的设备至少保证有一种
for (let i = 0; i < equipmentStates.length + 20; i++) {
    runningStateMap.put(equipmentStates[i], 0);
    addEquipment({
        id: getUuid(),
        name: `生产设备 ${i}`,
        type: equipmentTypes[i % equipmentTypes.length],
        communicationProtocol: communicationProtocols[0],
        ipv4: '127.0.0.1',
        ipv6: '',
        communicationPath: '',
        state: equipmentStates[parseInt(`${Math.random() * (equipmentStates.length)}`)],
        manufacturer: '测试厂商',
        location: '机房A',
        parent_device_name: '',
        loadCpu: new Proxy(1),
        loadMemory: new Proxy(1),
        childrenList: [],
    });
}

// 统计每种状态的设备的数量
for (let i = 0; i < equipments.length; i++) {
    let state = equipments[i].state;
    let c = runningStateMap.get(state);
    runningStateMap.put(state, c + 1);
}

export function getEquipmentRunningStates() {

    let equipmentStatesSummary = [];

    // 转换成可以解析的格式
    for (let i = 0; i < equipmentStates.length; i++) {
        equipmentStatesSummary.push({
            name: equipmentStates[i],
            value: runningStateMap.get(equipmentStates[i]),
        });
    }

    return equipmentStatesSummary;
}

export function changeEquipmentRunningState(equipment, newState) {
    let oldVal = runningStateMap.get(equipment.state);
    runningStateMap.put(equipment.state, oldVal - 1);
    oldVal = runningStateMap.get(newState);
    runningStateMap.put(newState, oldVal + 1);
}


// 代理所有设备
proxyAllEquipments(equipments);
