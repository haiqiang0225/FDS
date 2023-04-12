import {defineStore} from "pinia";


export const useUserStore = defineStore('user', {
    state: () => {
        return {
            user: JSON.parse(localStorage.getItem("user")),
        }
    },
    getters: {
        getUser: state => {
            return (state.user || JSON.parse(localStorage.getItem("user")));
        }
    },
    actions: {
        setUser(user) {
            this.user = user;
            localStorage.setItem("user", JSON.stringify(user));
        }
    }
});
