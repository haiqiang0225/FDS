<template>

  <el-tabs
      v-model="editableTabsValue"
      type="card"
      class="tabs"
      closable
      @tab-remove="removeTab"
      @tab-click="clickTab"
  >
    <el-tab-pane
        v-for="item in editableTabs"
        :key="item.name"
        :label="item.title"
        :name="item.name"
    >
      {{ item.content }}
    </el-tab-pane>
  </el-tabs>
</template>


<script setup>
import {storeToRefs} from "pinia";
import {useTabsStore} from "@/store/tabs";
import router from "@/router";

//==========================================================================================
//                                        属性
//==========================================================================================
const tabsStore = useTabsStore();

const {editableTabsValue, editableTabs} = storeToRefs(tabsStore);


//==========================================================================================
//                                        方法
//==========================================================================================
editableTabs.value = tabsStore.getEditableTabs;
editableTabsValue.value = tabsStore.getEditableTabsValue;


// 删除tab
const removeTab = (targetName) => {
  const tabs = editableTabs.value
  let activeName = editableTabsValue.value
  // 不允许删除首页
  if (activeName === "/index") {
    return;
  }
  if (activeName === targetName) {
    tabs.forEach((tab, index) => {
      if (tab.name === targetName) {
        const nextTab = tabs[index + 1] || tabs[index - 1]
        if (nextTab) {
          activeName = nextTab.name
        }
      }
    })
  }

  // 删除后置处理, 选中前一个标签
  editableTabsValue.value = activeName
  editableTabs.value = tabs.filter((tab) => tab.name !== targetName)
  router.push(activeName);
}

// 点击tab标签方法
const clickTab = (target) => {
  router.push(target.props.name);
}

</script>


<style lang="scss">


.tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.el-main {
  padding: 0;
}

.el-tabs {
  height: 40px;
  @include background-color('tabs-bar-color');
}

.el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
  background-color: #e7f2fe;
  border-bottom-color:  #e7f2fe;
}
</style>
