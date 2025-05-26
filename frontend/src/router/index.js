import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const MemberListView = () => import('@/views/member/MemberListView.vue')
const MemberDetailView = () => import('@/views/member/MemberDetailView.vue')
const MemberUpdateView = () => import('@/views/member/MemberUpdateView.vue')
const MemberRegisterView = () => import('@/views/member/MemberRegisterView.vue')

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
    },
    {
      path: '/member/detail/:id',
      name: 'memberDetail',
      component: MemberDetailView
    },
    {
      path: '/member/update/:id',
      name: 'memberUpdate',
      component: MemberUpdateView
    },
    {
      path: '/member/register',
      name: 'memberRegister',
      component: MemberRegisterView
    }
  ],
})

export default router
