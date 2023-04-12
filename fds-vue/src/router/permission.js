import router from "@/router/index";
import {useLoginStore} from "@/store/login";
import {useMenuStore} from "@/store/menu";
import {ElMessage} from "element-plus";


router.beforeEach(async (to, from, next) => {
    const loginStore = useLoginStore();
    const menuStore = useMenuStore();

    const whiteList = ["/login", "/forget", "/register"];


    // 尝试获取token
    let token = loginStore.getToken;
    const menuList = menuStore.getMenuList;


    // 有token 或者 在白名单中则放行,否则跳转到登录页
    if (whiteList.includes(to.path) && !token) {
        if (token) {
            ElMessage.info("您已经登录,不需要重复登陆!");
        }
        next();
    } else if (token) {
        if (!menuStore.isRouterAlreadyBound) {
            await initDynamicRouter(menuList);
            menuStore.setIsRouterAlreadyBound(true);
            return next({...to, replace: true});
        }
        next();
    } else {
        next({path: "/login", replace: true});
    }

});

function initDynamicRouter(menuList) {
    let routes = router.options.routes;
    menuList.forEach(menu => {
        if (menu.childrenList) {
            menu.childrenList.forEach(e => {
                addMenuToRouter(e);
            });
        } else {
            addMenuToRouter(menu);
        }
    });
}

function addMenuToRouter(menu) {
    if (!menu.component) {
        return;
    }
    let route = {
        name: menu.menuName,
        path: menu.path,
        components: {
            Redirect: () => import('@/views/' + menu.component + '.vue')
        },
        meta: {
            title: menu.nameZh,
        }
    }
    router.addRoute("Index", route);
}

