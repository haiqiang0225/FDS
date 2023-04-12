import {createApp} from 'vue'
import App from './App.vue'

// vuex vueRouter
import router from "@/router/index.js";
import store from '@/store/index';
import "@/router/permission"

// element-ui
import 'element-plus/dist/index.css'
// import 'element-plus/theme-chalk/index.css'
// import 'element-plus/theme-chalk/el-message.css';
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// fontawesome
import {library} from '@fortawesome/fontawesome-svg-core';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';

import {
    faAddressCard,
    faArrowUp,
    faAt,
    faBarChart,
    faCalendarDay,
    faChevronDown,
    faClock,
    faClockRotateLeft,
    faCodeFork,
    faCommentDots,
    faEnvelope,
    faFaceGrin,
    faFileCircleXmark,
    faHome,
    faLock,
    faMagnifyingGlass,
    faMapPin,
    faMessage,
    faMobileScreenButton,
    faPhone,
    faPlus,
    faSearch,
    faShield,
    faSpinner,
    faTag,
    faTags,
    faThLarge,
    faThumbsUp,
    faUser,
    faUserCircle,
} from '@fortawesome/free-solid-svg-icons';

import {faCopyright} from '@fortawesome/free-regular-svg-icons'

import {faGithub, faQq, faWeixin} from "@fortawesome/free-brands-svg-icons";

// css
import "@/assets/css/reset.css";
import "@/assets/css/main.css";
import "@/assets/css/border.css"
import "font-awesome/css/font-awesome.min.css";
import "animate.css";
import ElementPlus, {ElMessage} from 'element-plus'

import SvgIcon from "@/icons";

// fontawesome
library.add(faHome, faSearch, faTags, faTag, faThLarge, faUserCircle,
    faAt, faAddressCard, faCodeFork, faChevronDown, faMapPin,
    faArrowUp, faCalendarDay, faBarChart, faMagnifyingGlass,
    faFileCircleXmark, faSpinner, faClockRotateLeft, faClock,
    faUser, faLock, faEnvelope, faMobileScreenButton, faPlus,
    faGithub, faQq, faWeixin, faFaceGrin, faThumbsUp, faCommentDots,
    faMessage, faShield, faCopyright, faPhone);


const app = createApp(App);
app.use(router);
app.use(store);

SvgIcon(app);

app.component('font-awesome-icon', FontAwesomeIcon);

app.provide('message', ElMessage);

app.use(ElementPlus, {locale: zhCn});

app.mount('#app');
window.vm = app;

