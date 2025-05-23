<template>
    <nav class="navbar navbar-expand-lg border-bottom px-3 d-flex justify-content-between">
        <router-link to="/" class="navbar-brand">홈</router-link>
        <div class="d-flex align-items-center gap-2">
            <router-link to="/post/list" class="nav-link header-link rounded p-2">게시물 목록</router-link>
            <router-link to="/member/list"  class="nav-link header-link rounded p-2">회원 목록</router-link>
            <!-- TODO: 어드민일 때 출력 -->
            <template v-if="true">
                <!-- TODO: 로그인 상태 아닐 때 출력 -->
                <router-link to="/member/login" class="nav-link header-link rounded p-2">로그인</router-link>
                <router-link to="/member/register" class="nav-link header-link rounded p-2">회원가입</router-link>
            </template>
            <template v-if="true">
                <!-- TODO: 로그인 시 출력 -->
                <router-link to="/member/detail/:id" class="nav-link header-link rounded p-2">회원정보</router-link>
                <!-- TODO: 회원 정보 -> 아이디, :id를 실제 id로 변경 -->
                <button class="nav-link header-link rounded p-2">로그아웃</button>
            </template>
        </div>
    </nav>
</template>

<script setup>
    // TODO: 반응형으로 collapse 구현
</script>

<style scoped>
  .header-link:hover {
    background-color: var(--bs-secondary);
  }
</style>
