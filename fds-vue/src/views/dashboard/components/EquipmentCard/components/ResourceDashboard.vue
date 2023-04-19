<template>
  <v-chart style="height: 100%;" :option="option" autoresize/>
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
import {ref, provide, reactive} from 'vue';

use([
  TitleComponent,
  TooltipComponent,
  PieChart,
  CanvasRenderer
]);

import "@/mock"
import mockApi from "@/utils/mockApi";


// mockApi.getFaultCounts()
//     .then(res => {
//       console.log(res);
//     })
//     .catch(err => {
//       console.log(err);
//     })


//==========================================================================================
//                                        属性
//==========================================================================================

const props = defineProps(['deviceId', 'resourceName']);

provide(THEME_KEY, 'light');

let data = ref(loadInfoByDeviceId(props.deviceId));

const option = ref({
  title: {
    text: props.resourceName + " load",
    left: 'center',
  },
  series: [
    {
      type: 'gauge',
      axisLine: {
        lineStyle: {
          width: 10,
          color: [
            [0.3, '#47ff00'],
            [0.7, '#37a2da'],
            [1, '#fd666d']
          ]
        }
      },
      pointer: {
        itemStyle: {
          color: 'inherit'
        }
      },
      axisTick: {
        distance: -30,
        length: 8,
        lineStyle: {
          color: '#fff',
          width: 2
        }
      },
      splitLine: {
        distance: -30,
        length: 15,
        lineStyle: {
          color: '#fff',
          width: 4
        }
      },
      axisLabel: {
        color: 'inherit',
        distance: 30,
        fontSize: 12,
      },
      detail: {
        valueAnimation: true,
        formatter: '{value}%',
        color: 'inherit',
        fontSize: 18,
      },
      data: [
        {
          value: data.value.percent
        }
      ]
    }
  ]
});


//==========================================================================================
//                                        方法
//==========================================================================================
function loadInfoByDeviceId(deviceID) {
  return {
    percent: (70 + (Math.random() - 0.5) * 50).toFixed(2),
  };
}
</script>

<style lang="scss" scoped>

</style>
