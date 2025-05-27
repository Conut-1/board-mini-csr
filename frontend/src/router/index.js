import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const MemberListView = () => import('@/views/member/MemberListView.vue')
const MemberDetailView = () => import('@/views/member/MemberDetailView.vue')
const MemberUpdateView = () => import('@/views/member/MemberUpdateView.vue')
const MemberRegisterView = () => import('@/views/member/MemberRegisterView.vue')
const MemberLoginView = () => import('@/views/member/MemberLoginView.vue')
const PostListView = () => import('@/views/post/PostListView.vue')
const PostDetailView = () => import('@/views/post/PostDetailView.vue')
const PostCreateView = () => import('@/views/post/PostCreateView.vue')

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
    },
    {
      path: '/member/login',
      name: 'memberLogin',
      component: MemberLoginView
    },
    {
      path: '/post/list',
      name: 'postList',
      component: PostListView
    },
    {
      path: '/post/detail/:id',
      name: 'postDetail',
      component: PostDetailView
    },
    {
      path: '/post/create',
      name: 'postCreate',
      component: PostCreateView
    }
  ],
})

export default router
