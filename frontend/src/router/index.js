import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const MemberListView = () => import('@/views/member/MemberListView.vue')

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/member/list',
      name: 'memberList',
      component: MemberListView
    }
  ],
})

export default router
