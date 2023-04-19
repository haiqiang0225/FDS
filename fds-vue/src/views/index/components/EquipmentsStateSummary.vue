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
} from 'echarts/components';
import VChart, {THEME_KEY} from 'vue-echarts';
import {ref, provide} from 'vue';
import {useThemeStore} from "@/store/theme";
import {storeToRefs} from "pinia";
const themeStore = useThemeStore()

const {theme} = storeToRefs(themeStore);


use([
  TitleComponent,
  TooltipComponent,
  PieChart,
  CanvasRenderer
]);

provide(THEME_KEY, theme);

let option = ref({
  title: {
    text: '设备运行状态统计信息',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: (params) => {
      let name = params.name;
      let value = params.value;
      let percent = params.percent;
      return `状态: ${name} <br/>数量: ${value} (${percent}%)`;
    },
  },
  series: [
    {
      name: '设备统计信息',
      type: 'pie',
      radius: ['55%', '70%'],
      avoidLabelOverlap: false,
      label: {
        formatter: '{b}: {c}'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '16',
          fontWeight: 'bold'
        }
      },
      itemStyle: {
        borderRadius: 30,
        borderColor: '#fff',
        barBorderRadius: 5,
        borderWidth: 15,

        normal: {
          //这里是循环开始的地方
          color: function (params) {
            let colorList = ['#91cc75', '#c7c7c7', '#EDD977', '#5c88e6', 'red']
            if (params.dataIndex >= colorList.length) {
              params.dataIndex = params.dataIndex - colorList.length
            }
            return colorList[params.dataIndex]
          },
          borderRadius: 30,
          borderColor: '#fff',
          barBorderRadius: 5,
          borderWidth: 15,
        }
      },
      data: [
        {value: 1048, name: '正常运行中'},
        {value: 735, name: '停机'},
        {value: 580, name: '维护中'},
        {value: 484, name: '需要维护'},
        {value: 300, name: '故障'}
      ],

    }
  ]
});
</script>

<style lang="scss">
.chart {
  height: 300px;
}
</style>
