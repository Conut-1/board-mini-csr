<template>
    <button type="button" @click="withdraw" class="btn btn-danger">탈퇴</button>
</template>

<script setup>
    import { server } from '@/config/server';
    import axios from 'axios';
    import { inject } from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const { cleanMe } = inject('actions');

    async function withdraw() {
        if (!confirm("정말 탈퇴하시겠습니까?")) return;

        const resposne = await axios.post(server.route.member.withdraw);

        await cleanMe();
        router.push({ name: 'home' });
    }
</script>
