<template>
  <v-chart class="chart" :option="option" autoresize/>
</template>

<script setup>
import {use} from 'echarts/core';
import {CanvasRenderer} from 'echarts/renderers';
import {PieChart} from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components';
import VChart, {THEME_KEY} from 'vue-echarts';
import {ref, provide} from 'vue';
import * as statisticsApi from "@/api/statistics"

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
]);

provide(THEME_KEY, 'light');


let labels = ref([]);
let values = ref([]);


statisticsApi.getFaultCountByFaultDegree().then(res => {
  let data = res.data;
  labels.value = data.labels;
  values.value = data.values;
})

const option = ref({
  title: {
    text: '设备故障严重程度百分比',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: (params) => {
      let name = params.name;
      let value = params.value;
      let percent = params.percent;
      return `名称: ${name} <br/>发生故障数量: ${value} (${percent}%)`;
    },
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: labels,
  },
  series: [
    {
      name: '设备类型信息',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      label: {
        formatter: '{b}: {c}'
      },
      data: values,
      itemStyle: {
        normal: {
          //这里是循环开始的地方
          color: function (params) {
            let colorList = ['#ff0000', 'rgba(255,113,52,0.7)', '#EDD977', '#5ccfe6', 'red']
            if (params.dataIndex >= colorList.length) {
              params.dataIndex = params.dataIndex - colorList.length
            }
            return colorList[params.dataIndex]
          },
        }
      },
    },
  ],
  emphasis: {
    itemStyle: {
      shadowBlur: 10,
      shadowOffsetX: 0,
      shadowColor: 'rgba(0, 0, 0, 0.5)',
    },
  },
});

</script>

<style lang="scss" scoped>

</style>
