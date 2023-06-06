import {createRouter, createWebHashHistory} from 'vue-router'
import cabinMenus from "@/components/cabin-menus.vue";

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
        component: cabinMenus,
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
                component: () => import('@/components/oauth2/login.vue')
            },
            {
                path: '/feishu',
                name: 'feishu',
                component: () => import('@/components/oauth2/feishu-login.vue')
            },
            {
                path: '/chatApp',
                name: 'chatApp',
                component: () => import('@/components/talk/chat-app.vue')
            },
            {
                path: '/shortUrl',
                name: 'shortUrl',
                component: () => import('@/components/url/short-url.vue')
            },

        ],

    },
]

const router = createRouter({
    history: createWebHashHistory(), //HTML5模式
    routes,
});


export default router
//
// const routes = [
//     {
//         path: '/',
//         component: HelloWorld,
//         children: [{ path: '', component: HelloWorld }],
//     },
// ]

