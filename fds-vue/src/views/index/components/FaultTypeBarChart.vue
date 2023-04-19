<template>
  <v-chart style="height: 300px" class="chart" :option="option" autoresize/>
</template>

<script setup>
import {use} from 'echarts/core';
import {CanvasRenderer} from 'echarts/renderers';
import {BarChart} from 'echarts/charts';
import {LegendComponent, TitleComponent, TooltipComponent,} from 'echarts/components';
import VChart, {THEME_KEY} from 'vue-echarts';
import {provide, ref} from 'vue';
import mockApi from "@/utils/mockApi";

use([
  CanvasRenderer,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  BarChart,
]);

provide(THEME_KEY, 'light');

const types = ref([]);
const faultCounts = ref([]);

mockApi.getFaultCountsByFaultType()
    .then(res => {
      types.value = res.data[0];
      faultCounts.value = res.data[1];
    })
    .catch(err => {
      console.log(err);
    })

const option = ref({
  title: {
    text: '不同类型的故障计数',
    left: 'center',
  },

  tooltip: {
    trigger: 'item',
    formatter: (params) => {
      let name = params.name;
      let value = params.value;
      return `故障类型名称: ${name} <br/>数量: ${value}`;
    },
  },
  xAxis: {
    data: types
  },
  yAxis: {},
  series: [
    {
      type: 'bar',
      data: faultCounts,
      // 实现数字展示在柱状图
      label: {
        show: true,
        position: 'inside',
        color: 'black'
      },
      itemStyle: {
        barBorderRadius: 5,
        borderWidth: 1,
        borderColor: '#73c0de',
        shadowColor: '#5470c6',
        shadowBlur: 3,
        borderType: 'solid',
        opacity: 0.5,
        normal: {
          //这里是循环开始的地方
          color: function (params) {
            let colorList = ['#C6D0D3', '#92CDBB', '#EDD977', '#91cc75', '#E89F6A', '#ca8622']
            if (params.dataIndex >= colorList.length) {
              params.dataIndex = params.dataIndex - colorList.length
            }
            return colorList[params.dataIndex]
          },
        }
      }
    }
  ]
});


</script>

<style lang="scss" scoped>

</style>
