import {createRouter, createWebHistory} from 'vue-router'
import {isLogin} from "@/assets/js/utils";

const routes = [
    // {
    //     path: '/',
    //     redirect: {path: '/login'}
    // },
    // {
    //     path: '/login',
    //     name: 'login',
    //     component: () => import('../views/login')
    // },
    // {
    //     path: '/404',
    //     name: '404',
    //     component: () => import('../views/404')
    // },
    {
        path: '/',
        component: () => import('@/components/cabin-menus.vue'),
        redirect: 'home',
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/components/home.vue')
            },
            {
                path: '/gitee',
                name: 'gitee',
                component: () => import('@/components/gitee/gitee-code.vue')
            },
            {
                path: '/jenkins',
                name: 'jenkins',
                component: () => import('@/components/jenkins/jenkins-api.vue')
            },
            {
                path: '/jsonFormat',
                name: 'jsonFormat',
                component: () => import('@/components/json/json-format.vue')
            },

            {
                path: '/chatApps',
                name: 'chatApps',
                component: () => import('@/components/talk/chat-apps.vue')
            },
            {
                path: '/login',
                name: 'login',
                component: () => import('@/components/oauth2/login-home.vue')
            },
            {
                path: '/register',
                name: 'register',
                component: () => import('@/components/oauth2/register-account.vue')
            },
            {
                path: '/bind',
                name: 'bind',
                component: () => import('@/components/oauth2/bind-account.vue'),
                // meta: {requiresAuth: true}, // 需要登录才能访问
            },
            {
                path: '/chatApp',
                name: 'chatApp',
                component: () => import('@/components/talk/chat-app.vue'),
                meta: {requiresAuth: true}, // 需要登录才能访问
            },
            {
                path: '/shortUrl',
                name: 'shortUrl',
                component: () => import('@/components/url/short-url.vue')
            },
            {
                path: '/monitorLinux',
                name: 'monitorLinux',
                component: () => import('@/components/monitor/monitor-linux.vue')
            },

        ],

    },
]

const router = createRouter({
    // history: createWebHashHistory(), //HTML5模式
    history: createWebHistory(), //去掉/#/
    routes,
});

// 创建路由守卫
router.beforeEach((to, from, next) => {
    if (to.meta["requiresAuth"] && !isLogin()) { // 判断是否需要登录
        next("/login"); // 跳转到登录页面
    } else {
        next(); // 继续访问当前页面
    }
});

export default router;

