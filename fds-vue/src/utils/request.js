import axios from 'axios';
import {ElLoading, ElMessage} from "element-plus";
import router from "@/router";

const baseUrl = process.env.VUE_APP_AXIOS_BASE_URL;

// 创建axios
const service = axios.create({
    // url前缀
    baseURL: baseUrl, // 请求超时时间
    timeout: 10000
});

//post请求头
service.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";


let loadingInstance;
// 添加请求拦截器
service.interceptors.request.use(function (config) {
    if (!config.doNotShowLoading) {
        loadingInstance = ElLoading.service("加载中");
    }
    // 在发送请求之前做些什么
    let token = sessionStorage.getItem("token") || localStorage.getItem("token");
    // let access_token = sessionStorage.getItem("access_token") || localStorage.getItem("access_token");
    if (token) {
        config.headers.token = token;
    }
    return config;
}, function (error) {
    loadingInstance?.close();
    ElMessage.error("网络异常");
    return Promise.reject(error);
});


// 添加响应拦截器
service.interceptors.response.use(function (response) {
    let status = response.status;
    let token = response.data.token;
    let msg = response.data.msg;

    if (status === 200) {
        if (token) {
            sessionStorage.setItem("token", token);
            localStorage.setItem("token", token);
        }
    } else if (msg) {
        ElMessage.info(msg);
    }
    loadingInstance?.close();
    return response;
}, function (error) {
    console.log(error)
    loadingInstance?.close();
    ElMessage.error("请求失败")

    let errMsg = undefined;
    // 自定义的状态编码
    let errCode = undefined;

    // 尝试解析
    try {
        if (error.data) {
            errMsg = error.data.msg;
        }
        if (error.response && error.response.data) {
            errMsg = error.response.data.msg;
        }
    } catch (e) {
        console.log(e)
    }

    // 对响应错误做点什么
    if (error.code === "ERR_BAD_REQUEST" && error.response.status === 403) {
        ElMessage.error("没有权限!");
    } else if (error.code === 'ECONNABORTED' || error.message.includes("timeout")) {
        ElMessage.error("请求超时,请重试");
    } else if (errMsg) {
        ElMessage.error(errMsg)
    } else {
        ElMessage.error("出错了,请联系系统管理员!");
    }

    // 对于未登录的错误,跳转登录页面
    if (error.response && error.response.status === 401) {
        router.push("/login").then(r => {
        });
    }

    return Promise.reject(error);
});

export function getRequestBaseUrl() {
    return baseUrl;
}

export function appendUrls(urls) {
    let res = arguments[0];
    for (let i = 1; i < arguments.length; i++) {
        // 去除前导/
        let append = arguments[i];
        if (append.startsWith("/")) {
            append = append.slice(1);
        }
        // 如果以 / 结尾
        if (res.endsWith("/")) {
            res = res + append;
        } else {
            res = res + "/" + append;
        }
    }

    return res;
}

export default service;
