<template>
    <nav class="navbar navbar-expand-lg border-bottom px-3 d-flex justify-content-between">
        <router-link to="/" class="navbar-brand">홈</router-link>
        <div class="d-flex align-items-center gap-2">
            <router-link to="/post/list" class="nav-link header-link rounded p-2">게시물 목록</router-link>
            <router-link v-if="roles.includes(ADMIN_ROLE)" to="/member/list" class="nav-link header-link rounded p-2">회원 목록</router-link>
            <template v-if="!id">
                <router-link to="/member/login" class="nav-link header-link rounded p-2">로그인</router-link>
                <router-link to="/member/register" class="nav-link header-link rounded p-2">회원가입</router-link>
            </template>
            <template v-if="id">
                <router-link :to="`/member/detail/${id}`" v-text="`${id}`" class="nav-link header-link rounded p-2"></router-link>
                <button @click="logout" class="nav-link header-link rounded p-2">로그아웃</button>
            </template>
        </div>
    </nav>
</template>

<script setup>
    import { server } from '@/config/server';
    import axios from 'axios';
    import { inject } from 'vue';
    import { useRouter } from 'vue-router';

    const ADMIN_ROLE = 'ROLE_ADMIN';

    const router = useRouter();

    const id = inject('id');
    const roles = inject('roles');
    const { cleanMe } = inject('actions');

    // TODO: 반응형으로 collapse 구현
    async function logout() {
        await axios.post(server.route.auth.logout);
        cleanMe();
        router.push({ name: 'home' });
    }
</script>

<style scoped>
  .header-link:hover {
    background-color: var(--bs-secondary);
  }
</style>
