import {defineStore, mapStores} from "pinia";


export const useLoginStore = defineStore('login', {
    state: () => {
        return {
            username: "",
            rememberMe: false,
            verifyCode: undefined,
            sendVerifyCondeCollingTime: 0,
            token: "",
        }
    },
    getters: {
        getToken: (state) => state.token || sessionStorage.getItem("token") || localStorage.getItem("token"),

    },
    actions: {
        // 同步表单数据
        syncFormData(form) {
            this.username = form.username;
            this.rememberMe = form.rememberMe;
            this.verifyCode = form.verifyCode;
            // 同步
            let s = form.sendVerifyCondeCollingTime;
            this.sendVerifyCondeCollingTime = s !== undefined ? s : 0;
        },
        setToken(token) {
            if (token) {
                this.token = token;
            }
        }
    },
});


export default {
    computed: {
        ...mapStores(useLoginStore, useLoginStore),
    }
};
