import {defineStore} from "pinia";


export const useTabsStore = defineStore('tabs', {
    state: () => {
        return {
            editableTabsValue: JSON.parse(sessionStorage.getItem("editableTabsValue")) || '/index',
            editableTabs: JSON.parse(sessionStorage.getItem("editableTabs")) || [
                {
                    title: '首页',
                    name: '/index',
                },
            ]
        }
    },
    getters: {
        getEditableTabsValue() {
            return JSON.parse(sessionStorage.getItem("editableTabsValue")) || this.editableTabsValue;
        },
        getEditableTabs() {
            return this.editableTabs || JSON.parse(sessionStorage.getItem("editableTabs"));
        }
    },
    actions: {
        addTab(tab) {
            if (!this.editableTabs) {
                this.editableTabs = [
                    {
                        title: '首页',
                        name: '/index',
                    },
                ];
            }
            // 如果tab未打开, 则添加到列表中
            if (!JSON.stringify(this.editableTabs).includes(JSON.stringify(tab))) {
                this.editableTabs.push(tab);
            }
            // 将添加的Tab设置为展示Tab
            this.editableTabsValue = tab.name;
            // sessionStorage 存储状态
            sessionStorage.setItem("editableTabs", JSON.stringify(this.editableTabs));
            sessionStorage.setItem("editableTabsValue", JSON.stringify(this.editableTabsValue));
        },
        resetTabs() {
            sessionStorage.removeItem("editableTabs");
            sessionStorage.removeItem("editableTabsValue");

            this.editableTabsValue = '/index';
            this.editableTabs = [
                {
                    title: '首页',
                    name: '/index',
                },
            ];
        },
        setEditableTabsValue(newVal) {
            sessionStorage.setItem("editableTabsValue", JSON.stringify(newVal));
        }
    }
});
