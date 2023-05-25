import {createRouter, createWebHashHistory} from 'vue-router'
import cabinMenus from "@/components/cabin-menus.vue";

const routes = [
    {
        path: '/',
        component: cabinMenus,
        // redirect: 'hello',
        children: [
            // {
            //     path: '/code',
            //     name: 'CodeUI',
            //     component: () => import('@/components/util/Code-Block.vue')
            // },
            {
                path: '/hello',
                name: 'hello',
                component: () => import('@/components/hello-world.vue')
            },
            {
                path: '/home',
                name: 'home',
                component: () => import('@/components/home.vue')
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

