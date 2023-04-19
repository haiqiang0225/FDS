import {defineStore} from "pinia";


export const useThemeStore = defineStore('theme', {
    state: () => {
        return {
            theme: JSON.parse(localStorage.getItem("theme")) || 'light',
        }
    },
    getters: {
        getTheme: state => {
            return (state.theme || JSON.parse(localStorage.getItem("theme")));
        }
    },
    actions: {
        setTheme(theme) {
            this.theme = theme;
            localStorage.setItem("user", JSON.stringify(theme));
        }
    }
});
