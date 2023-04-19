<template>
  <v-chart style="height: 100%" class="chart" :option="option" autoresize/>
</template>

<script setup>
import {use} from 'echarts/core';
import {CanvasRenderer} from 'echarts/renderers';
import {LineChart} from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
} from 'echarts/components';
import VChart, {THEME_KEY} from 'vue-echarts';
import {ref, provide} from 'vue';

use([
  TitleComponent,
  TooltipComponent,
  LineChart,
  CanvasRenderer
]);

provide(THEME_KEY, 'light');

const props = defineProps(['deviceId', 'title', 'chartLineColor']);



const option = ref({
  title: {
    text: props.title,
    left: 'left',
  },
  tooltip: {
    trigger: 'axis',
    formatter: (params) => {
      let name = params[0].name;
      let value = params[0].value;
      return `时间: ${name} <br/>发生故障数量: ${value}`;
    },
  },
  xAxis: {
    type: 'category',
    data: ['2023-4-10', '2023-4-11', '2023-4-12', '2023-4-13', '2023-4-14', '2023-4-15', '2023-4-16'],
    axisTick: {
      alignWithLabel: true
    },
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '故障计数',
      type: 'line',
      data: [120, 100, 150, 120, 200, 150, 100],
      itemStyle: {
        // Styles for normal state.
        normal: {
          color: props.chartLineColor,
        },
        // Styles for emphasis state.
        emphasis: {
          color: 'red'
        }
      },
      lineStyle: {      // 阴影部分
        shadowOffsetX: 0, // 折线的X偏移
        shadowOffsetY: 2,// 折线的Y偏移
        shadowBlur: 8,  // 折线模糊
        shadowColor: "rgba(145, 132, 132, 1)", //折线颜色
      },
      smooth: true,
      areaStyle: {
        normal: {
          color: {
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [{
              offset: 0,
              color: props.chartLineColor // 0% 处的颜色
            }, {
              offset: 0.7,
              color: "rgba(231,201,130,0)" // 100% 处的颜色
            }],
            globalCoord: false // 缺省为 false
          }
        }
      },
    }
  ]
});
</script>

<style lang="scss" scoped>

</style>
