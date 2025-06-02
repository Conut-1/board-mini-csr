<template>
    <div class="container my-3">
        <div class="card">
            <div class="card-header">게시글 상세</div>
            <div class="card-body">
                <div id="post-meta">
                    <!-- <div class="small">공지사항</div> -->
                    <h5 v-text="post.title"></h5>
                    <div class="d-flex justify-content-between small">
                        <div class="d-flex gap-3">
                            <!-- <span>홍길동</span> -->
                            <span v-text="post.createDate"></span>
                        </div>
                        <div v-text="`조회수: ${post.views}`"></div>
                    </div>
                </div>
                <hr>
                <pre v-text="post.content" class="post-content"></pre>
                <div class="d-flex gap-2">
                    <router-link :to="{ name: 'postUpdate', params: { id: post.postNo }}" class="btn btn-primary">수정</router-link>
                    <button v-if="roles.includes(ADMIN_ROLE)" @click="adminDeletePost" class="btn btn-danger">관리자 삭제</button>
                    <template v-if="!roles.includes(ADMIN_ROLE)">
                        <button @click="deletePost" class="btn btn-danger">삭제</button>
                        <input type="password" v-model="password" class="form-control w-6rem" placeholder="비밀번호">
                    </template>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { server } from '@/config/server';
    import axios from 'axios';
    import { inject, reactive, ref } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const ADMIN_ROLE = 'ROLE_ADMIN';

    const router = useRouter();
    const route = useRoute();

    const roles = inject('roles');

    const post = reactive({});
    const password = ref('');

    fetchPost();

    async function fetchPost() {
        const response = await axios.get(server.route.post.detail(route.params.id));
        Object.assign(post, response.data.post);
        axios.post(server.route.post.views(route.params.id));
    }

    async function adminDeletePost() {
        if (!confirm("게시글을 삭제하시겠습니까?")) return;

        try {
            await axios.post(server.route.post.adminDelete(route.params.id));
            alert("게시글 삭제가 성공했습니다.");
            router.push({ name: 'postList' });
        } catch (e) {
            alert("게시글 삭제가 실패했습니다.");
        }
    }

    async function deletePost() {        
        if (password.value.length === 0) {
            // password.focus();
            return;
        }

        if (!confirm("게시글을 삭제하시겠습니까?")) return;

        try {
            await axios.post(server.route.post.delete(route.params.id), { password: password.value });
            alert("게시글 삭제가 성공했습니다.");
            router.push({ name: 'postList' });
        } catch (e) {
            alert("게시글 삭제가 실패했습니다.");
        }
    }
</script>
