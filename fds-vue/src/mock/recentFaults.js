// =============================================== 故障计数列表 =========================================================
export let faultCounts = [5, 7, 9, 6, 10, 15, 8];


// 按照设备类计数
export let faultCountsByEquipmentType = [
    {
        name: '机床',
        value: 10,
    },
    {
        name: '轴承',
        value: 127,
    },
    {
        name: '轴承座',
        value: 19,
    },
    {
        name: '密封件',
        value: 27,
    },
    {
        name: '润滑系统',
        value: 30,
    },
    {
        name: '轴承盖',
        value: 10,
    },
];

export let faultByType = [
    ['疲劳失效', '摩擦和磨损', '过热', '污染', '腐蚀', '电气故障', '机械故障', '液压故障', '气动故障', '控制系统故障'],
    [10, 22, 28, 43, 49, 22, 33, 12, 50, 12,],
];

export let faultDegreeCounts = [
    ['轻微故障', '一般故障', '严重故障', '致命故障'],
    [
        {value: 335, name: '致命故障'},
        {value: 310, name: '严重故障'},
        {value: 234, name: '一般故障'},
        {value: 135, name: '轻微故障'},
    ]
];

