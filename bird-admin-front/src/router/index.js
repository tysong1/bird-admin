import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/views/layout/Layout';


export default new Router({
    routes: [
        {
            path: '/login',
            component: () => import('@/views/login/index'),
            hidden: true
        },
        {
            path: '/404',
            component: () => import('@/views/errorPage/404'),
            hidden: true
        },
        {
            path: '/401',
            component: () => import('@/views/errorPage/401'),
            hidden: true
        },
        {
            path: '/',
            component: Layout,
            redirect: 'dashboard',
            children: [
                {
                    path: 'dashboard',
                    component: () => import('@/views/dashboard/index'),
                    name: 'Dashboard',
                    meta: {title: 'dashboard', icon: 'dashboard', noCache: true}
                }
            ]
        },
        {
            path: '/systemMarket',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'systemMarket',
            children: [{
                path: 'main',
                component: () => import('@/views/systemMarket/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/systemMarket'],
                }
            },]
        },
        {
            path: '/permission',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'permission',
            children: [{
                path: 'main',
                component: () => import('@/views/permission/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/permission'],
                }
            },]
        },
        {
            path: '/menu',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'menu',
            children: [{
                path: 'main',
                component: () => import('@/views/menu/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                }
            },]
        },
        {
            path: '/user',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'user',
            children: [{
                path: 'main',
                component: () => import('@/views/user/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/user'],
                }
            },]
        },
        {
            path: '/role',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'role',
            children: [{
                path: 'main',
                component: () => import('@/views/role/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/role'],
                }
            },]
        },
        {
            path: '/upload',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'upload',
            children: [{
                path: 'main',
                component: () => import('@/views/upload/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/upload'],
                }
            },]
        },
        {
            path: '/charts',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'charts',
            children: [{
                path: 'main',
                component: () => import('@/views/charts/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/charts'],
                }
            },]
        },
        {
            path: '/processBar',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'processBar',
            children: [{
                path: 'main',
                component: () => import('@/views/processBar/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/processBar'],
                }
            },]
        },
        {
            path: '/tips',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'tips',
            children: [{
                path: 'main',
                component: () => import('@/views/tips/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['/admin/tips'],
                }
            },]
        },
        {
            path: '/editor',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'editor',
            children: [{
                path: 'main',
                component: () => import('@/views/editor/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['admin', 'editor'],
                }
            },]
        },
        {
            path: '/operatorLog',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'operatorLog',
            children: [{
                path: 'main',
                component: () => import('@/views/operatorLog/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['admin', 'editor'],
                }
            },]
        },
        {
            path: '/other',
            component: Layout,
            redirect: 'noredirect',
            alwaysShow: true,
            name: 'other',
            children: [{
                path: 'main',
                component: () => import('@/views/other/index'),
                meta: {
                    title: 'main',
                    icon: 'main',
                    noCache: true,
                    perms: ['admin', 'other'],
                }
            },]
        },
        {path: '*', redirect: '/404', hidden: true}
    ],
    scrollBehavior() {
        return {x: 0, y: 0};
    }
});
