import {createRouter, createWebHistory} from "vue-router";

import RegisterForm from "@/views/login/components/RegisterForm";
import UserInfoForm from "@/views/login/components/UserLoginForm";
import ForgetPassForm from "@/views/login/components/ForgetPassForm";

const routes = [
    // {
    //     path: '',
    //     name: 'Home',
    //     redirect: '/index',
    //     component: () => import("@/layout"),
    // },
    {
        path: '',
        name: 'Index',
        redirect: '/index',
        components: {
            default: () => import("@/layout"),
        },
        meta: {
            title: "首页",
        },
        children: [
            {
                name: 'userCenter',
                path: '/userCenter',
                components: {
                    Redirect: () => import('@/views/userCenter/index.vue'),
                },
                meta: {
                    title: "用户中心",
                }
            }
        ],
    },
    {
        path: '/login',
        name: 'login',
        components: {
            default: () => import("@/views/login"),
        },
        children: [{
            path: '',
            name: 'loginForm',
            components: {
                UserLoginInfoView: UserInfoForm
            },
        }, {
            path: '/register',
            name: 'registerForm',
            components: {
                UserLoginInfoView: RegisterForm
            }
        }, {
            path: '/forget',
            name: 'forgetForm',
            components: {
                UserLoginInfoView: ForgetPassForm
            }
        }
        ]
    },


];
const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (to.hash) {
            return {
                el: to.hash,
                behavior: 'smooth',
            };
        } else {
            // 页面刷新时保存滚动条位置, 切换路由时恢复位置
            return savedPosition ? savedPosition : {top: 0};
        }
    }
});

export default router
