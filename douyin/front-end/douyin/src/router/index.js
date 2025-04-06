import { createRouter, createWebHashHistory} from "vue-router";

const routes = [
    {
        path: "/user",
        component: () => import("../views/user/index.vue"),
        // redirect: "/user/home",
        children: [
            {
                path: "home",
                component: () => import("../views/user/home/index.vue"),
              },
              {
                path: "classify",
                component: () => import("../views/user/classify/index.vue"),
              },
              {
                path: "video",
                component: () => import("../views/user/myVideo/index.vue"),
              },
              {
                path: "favorites",
                component: () => import("../views/user/favorites/index.vue"),
              },
              {
                path: "like",
                component: () => import("../views/user/like/index.vue")
              },
              {
                path: "history",
                component: () => import("../views/user/history/index.vue")
              }
        ]
    },
    

]
const router = createRouter({
    history: createWebHashHistory(),
    routes: routes,
})
export default router;