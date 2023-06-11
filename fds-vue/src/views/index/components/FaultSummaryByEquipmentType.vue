<template>
  <v-chart style="height: 300px" class="chart" :option="option" autoresize/>
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

const faults = ref([]);
const labels = ref([])

statisticsApi.getFaultCountByDeviceType().then(res => {
  faults.value = res.data.faultCountsByDeviceType;
})


const option = ref({
  title: {
    text: '设备故障数量百分比',
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
        formatter: '{b}: {c} ({d}%)'
      },
      data: faults,
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
