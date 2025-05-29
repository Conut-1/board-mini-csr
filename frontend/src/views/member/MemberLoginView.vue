<template>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 w-100 mw-360px">
            <form @submit.prevent="login">
                <div class="mb-3">
                    <input type="text" v-model="member.id" class="form-control" placeholder="아이디">
                </div>
                <div class="mb-3">
                    <input type="password" v-model="member.password" class="form-control" placeholder="비밀번호">
                </div>
                <div class="d-grid mb-3">
                    <input type="submit" class="btn text-white btn-primary" value="로그인"></input>
                </div>
            </form>
            <router-link :to="{ name: 'memberRegister' }" class="btn btn-light text-secondary">회원가입</router-link>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { inject, reactive } from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const { fetchMe } = inject('actions');

    const member = reactive({ id: '', password: '' });

    async function login() {
        // TODO: focus 추가
        if (!(8 <= member.id.length && member.id.length <= 20)) {
            alert("아이디는 8자 이상 20자 이하로 입력하세요.");
            return;
        }

        // const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$/;
        // if (!passwordPattern.test(member.password)) {
        if (member.password.length === 0) {
            alert("비밀번호가 유효하지 않습니다.");
            return;
        }

        try {
            await axios.post('/api/auth/login', member);
            await fetchMe();
            router.push({ name: 'home' });
        } catch (e) {
            alert(e.response.data.message);
        }
    }
</script>
