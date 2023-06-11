<template>
  <v-chart class="chart" :option="option" autoresize/>
</template>

<script setup>

import {use} from 'echarts/core';
import {CanvasRenderer} from 'echarts/renderers';
import {LineChart} from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components';
import VChart, {THEME_KEY} from 'vue-echarts';
import {ref, provide} from 'vue';
import * as monitorApi from '@/api/monitor'
import {ElMessage} from "element-plus";

use([
  CanvasRenderer,
  LineChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
]);

provide(THEME_KEY, 'light');

let dateBase = new Date();
let today = [dateBase.getFullYear(), dateBase.getMonth(), dateBase.getDay()].join('-')
let base = +dateBase;
let oneSecond = 1000;
let date = ref([]);
let data = ref([0]);
let now = new Date(base);


function addData(shift, pushZero) {
  let nowStr = [now.getHours(), now.getMinutes() + 1, now.getSeconds()].join(':');
  date.value.push(nowStr);

  if (pushZero) {
    data.value.splice(0, 0, 0);
  } else {
    monitorApi.getSysLoad()
        .then(res => {
          let sysLoad = String(res.data.sysLoad);
          let value = sysLoad.substring(0, sysLoad.length - 2);
          let newData = parseFloat(value);
          data.value.push(newData);
        })
        .catch(err => {
          console.log(err);
        })
  }
  if (shift) {
    date.value.shift();
    data.value.shift();
  }
  now = new Date(+now + oneSecond * 2);
}

for (let i = 1; i < 10; i++) {
  addData(false, true);
}

const option = ref({
  animation: true,
  title: {
    text: '系统实时负载(全局)%',
    left: 'left',
  },
  tooltip: {
    trigger: 'axis',
    formatter: (params) => {
      let name = params[0].name;
      let value = params[0].value;
      return `当前时间: ${today} ${name} <br/>系统负载: ${value}`;
    },
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: date,
    axisTick: {
      alignWithLabel: true,
      length: 10,
    },
  },
  yAxis: {
    boundaryGap: [0, '20%'],
    type: 'value',
    // max: 100,
    min: 0,
  },
  series: [
    {
      name: '成交',
      type: 'line',
      smooth: true,
      symbol: 'none',
      stack: 'a',
      itemStyle: {
        // Styles for normal state.
        normal: {
          color: 'rgba(30,138,40,0.3)',
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
      areaStyle: {
        normal: {
          color: {
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [{
              offset: 0,
              color: "#00ff15" // 0% 处的颜色
            }, {
              offset: 0.7,
              color: "rgba(255,255,255,0.2)" // 100% 处的颜色
            }],
            globalCoord: false // 缺省为 false
          }
        }
      },
      data: data,

    }
  ]
});
setInterval(function () {
  addData(true);
}, 2000);

</script>

<style lang="scss" scoped>

</style>
