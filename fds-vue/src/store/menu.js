import {defineStore} from "pinia";


export const useMenuStore = defineStore('menu', {
    state: () => {
        return {
            menuList: JSON.parse(sessionStorage.getItem("menuList")) || JSON.parse(localStorage.getItem("menuList")),
            isRouterAlreadyBound: false,
        }
    },
    getters: {
        getMenuList() {
            return this.menuList || JSON.parse(sessionStorage.getItem("menuList")) || localStorage.getItem("menuList");
        },
    },
    actions: {
        // 存储
        setMenuList(menuList) {
            this.menuList = menuList || this.menuList;
            sessionStorage.setItem("menuList", JSON.stringify(this.menuList));
            localStorage.setItem("menuList", JSON.stringify(this.menuList));
        },
        setIsRouterAlreadyBound(newVal) {
            this.isRouterAlreadyBound = newVal;
        }
    }
});
