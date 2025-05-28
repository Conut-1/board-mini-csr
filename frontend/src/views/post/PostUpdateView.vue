<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">게시물 수정</div>
            <div class="card-body">
                <form @submit.prevent="updatePost">
                    <div class="mb-3">
                        <label for="update-post-title" class="form-label">제목</label>
                        <input type="text" v-model="post.title" class="form-control" id="update-post-title" placeholder="제목">
                    </div>
                    <div class="mb-3">
                        <label for="update-post-password" class="form-label">비밀번호</label>
                        <input type="password" v-model="post.password" class="form-control" id="update-post-password" placeholder="비밀번호">
                    </div>
                    <!-- <div class="mb-3">
                        <label for="category" class="form-label">카테고리</label>
                        <select class="form-select" id="category" name="category">
                            <option value="" selected disabled>카테고리를 선택하세요.</option>
                            <option value="1">공지사항</option>
                            <option value="2">Q&A</option>
                            <option value="3">자유게시판</option>
                        </select>
                    </div> -->
                    <div class="mb-3">
                        <label for="update-post-content" class="form-label">내용</label>
                        <textarea v-model="post.content" class="form-control no-resize" id="update-post-content" placeholder="내용" rows="10"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">수정</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { reactive } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const router = useRouter();
    const route = useRoute();

    const defaultPost = {
        id: '',
        password: '',
        content: ''
    }

    const post = reactive({ ...defaultPost });

    fetchPost();

    async function fetchPost() {
        const response = await axios.get(`/api/post/detail/${route.params.id}`);
        Object.assign(post, response.data.post);
    }

    async function updatePost() {
        if (post.title.length === 0) {
            alert("제목을 입력해주세요.");
            return;
        }

        if (post.password.length === 0) {
            alert("비밀번호를 입력해주세요.");
            return;
        }

        if (post.content.length === 0) {
            alert("내용을 입력해주세요.");
            return;
        }

        if (!confirm("게시글을 수정하시겠습니까?")) return;

        try {
            await axios.post(`/api/post/update/${route.params.id}`, post);
            alert("게시글 수정이 성공했습니다.");
            router.push({ name: 'postDetail', params: { id: route.params.id}});
        } catch (e) {
            alert("게시글 수정이 실패했습니다.");
        }
    }
</script>
