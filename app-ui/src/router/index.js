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
        redirect: 'home ',
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/components/home.vue')
            },
            {
                path: '/hello',
                name: 'hello',
                component: () => import('@/components/hello-world.vue')
            },
            {
                path: '/gitee',
                name: 'gitee',
                component: () => import('@/components/gitee/gitee-code.vue')
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

