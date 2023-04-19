import getUuid from "@/utils/uuid";

// AVAILABLE: 表示模型可用
// Loading：表示模型正在被加载。
// Unloading：表示模型正在被卸载。
// Failed：表示模型加载或卸载时出现了错误。
// VersionDef_Loading：表示模型版本正在被加载。
// VersionDef_Unloading：表示模型版本正在被卸载。
// VersionDef_Failed：表示模型版本加载或卸载时出现了错误。
export const modelStates = ['AVAILABLE', 'Loading', 'Unloading', 'Failed', 'VersionDef_Loading',
    'VersionDef_Unloading', 'VersionDef_Failed'];

export const methodNames = ['tensorflow/serving/predict', 'tensorflow/serving/classify', 'tensorflow/serving/generate'];

export const models = []

for (let i = 1; i <= 3; i++) {
    models.push(
        {
            id: getUuid(),
            name: "ConditionalTimeSeriesGAN-PHM-Condition" + i,
            version: '1.0.0',
            modelDescription: '生成模型, 用于生成PHM2012数据集中条件' + i + "子数据集中的样本",
            state: modelStates[0],
            inputTensorShape: '(Batch Size)',
            outputTensorShape: '(Batch Size, 96,26)',
            methodName: 'tensorflow/serving/generate', // tensorflow/serving/classify
        },
    )
}

for (let i = 1; i <= 2; i++) {
    models.push(
        {
            id: getUuid(),
            name: "ConditionalTimeSeriesGAN-CMAPSS-FD00" + i,
            version: '1.0.0',
            modelDescription: '生成模型, 用于生成CMAPSS数据集中条FD00' + i + "子数据集中的样本",
            state: modelStates[0],
            inputTensorShape: '(Batch Size)',
            outputTensorShape: '(Batch Size, 96,3)',
            methodName: 'tensorflow/serving/generate', // tensorflow/serving/classify
        },
    )
}


for (let i = 1; i <= 2; i++) {
    models.push(
        {
            id: getUuid(),
            name: "AutoFormer-with-FCU-CMAPSS-FD00" + i,
            version: '1.0.0',
            modelDescription: '预测模型, 用于生成CMAPSS数据集中条FD00' + i + "子数据集中的样本",
            state: modelStates[0],
            inputTensorShape: '(Batch Size)',
            outputTensorShape: '(Batch Size, 96,3)',
            methodName: 'tensorflow/serving/predict', // tensorflow/serving/classify
        },
    )
}

for (let i = 1; i <= 2; i++) {
    models.push(
        {
            id: getUuid(),
            name: "Classifier-on-CWRU" + i,
            version: '1.0.0',
            modelDescription: '诊断模型, 用于对轴承数据集CWRU中的样本的诊断',
            state: modelStates[0],
            inputTensorShape: '(Batch Size)',
            outputTensorShape: '(Batch Size, 96,3)',
            methodName: 'tensorflow/serving/classify', // tensorflow/serving/classify
        },
    )
}

for (let i = 3; i <= 4; i++) {
    models.push(
        {
            id: getUuid(),
            name: "ConditionalTimeSeriesGAN-CMAPSS-FD00" + i,
            version: '1.0.0',
            modelDescription: '生成模型, 用于生成CMAPSS数据集中条FD00' + i + "子数据集中的样本",
            state: modelStates[0],
            inputTensorShape: '(Batch Size)',
            outputTensorShape: '(Batch Size, 96,3)',
            methodName: 'tensorflow/serving/generate', // tensorflow/serving/classify
        },
    )
}
// models.push(
//     {
//         id: getUuid(),
//         name: "ConditionalTimeSeriesGAN-PHM-Condition2",
//         version: '1.0.0',
//         modelDescription: '生成模型',
//         state: modelStates[1],
//         inputTensorShape: '(Batch Size)',
//         outputTensorShape: '(Batch Size, 96,26)',
//         methodName: 'tensorflow/serving/generate', // tensorflow/serving/classify
//     }
// )
//
// models.push(
//     {
//         id: getUuid(),
//         name: "ConditionalTimeSeriesGAN-PHM-Condition3",
//         version: '1.0.0',
//         modelDescription: '生成模型',
//         state: modelStates[1],
//         inputTensorShape: '(Batch Size)',
//         outputTensorShape: '(Batch Size, 96,26)',
//         methodName: 'tensorflow/serving/generate', // tensorflow/serving/classify
//     }
// )

// for (let i = 0; i < 20; i++) {
//     models.push(
//         {
//             id: getUuid(),
//             name: "测试模型" + i,
//             version: '1.0.0',
//             modelDescription: '这是一个测试模型',
//             state: modelStates[i % modelStates.length],
//             inputTensorShape: '(None, 1024, 1)',
//             outputTensorShape: '(None, 16)',
//             methodName: methodNames[i % methodNames.length], // tensorflow/serving/classify
//         }
//     )
// }
