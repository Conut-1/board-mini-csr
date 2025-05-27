<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">게시물 작성</div>
            <div class="card-body">
                <form @submit.prevent="createPost" id="create-post-form">
                    <div class="mb-3">
                        <label for="create-post-title" class="form-label">제목</label>
                        <input type="text" v-model="post.title" class="form-control" id="create-post-title" placeholder="제목">
                    </div>
                    <div class="mb-3">
                        <label for="create-post-password" class="form-label">비밀번호</label>
                        <input type="password" v-model="post.password" class="form-control" id="create-post-password" placeholder="비밀번호">
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
                        <label for="create-post-content" class="form-label">내용</label>
                        <textarea v-model="post.content" class="form-control no-resize" id="create-post-content" placeholder="내용" rows="10"></textarea>
                    </div>
                    <input type="submit" class="btn btn-primary" value="작성">
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { reactive } from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const defaultPost = {
        title: '',
        password: '',
        content: ''
    }

    const post = reactive({ ...defaultPost });

    async function createPost() {
        // TODO: 응답을 받아서 post deatil로 이동
        const response = await axios.post('/api/post/create', post);
        router.push({ name: 'postList' })
    }

    // const createPostForm = document.body.querySelector("#create-post-form");

    // createPostForm.addEventListener("submit", createPost);

    // async function createPost(e) {
    //     e.preventDefault();

    //     const title = createPostForm.querySelector("#create-post-title");
    //     const password = createPostForm.querySelector("#create-post-password");
    //     const content = createPostForm.querySelector("#create-post-content");

    //     if (title.value.length === 0) {
    //         alert("제목을 입력해주세요.");
    //         title.focus();
    //         return;
    //     }

    //     // if (!category) {
    //     //     alert("카테고리를 선택해주세요.");
    //     //     form.category.focus();
    //     //     return;
    //     // }

    //     const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$/;
    //     if (!passwordPattern.test(password.value)) {
    //         alert("비밀번호가 유효하지 않습니다.");
    //         password.focus();
    //         return;
    //     }

    //     if (content.value.length === 0) {
    //         alert("내용을 입력해주세요.");
    //         content.focus();
    //         return;
    //     }

    //     if (!confirm("글을 작성하시겠습니까?")) return;

    //     const body = {
    //         title: title.value,
    //         password: password.value,
    //         content: content.value,
    //     };

    //     const response = await fetch("create", {
    //         method: 'post',
    //         headers: { 'Content-Type': 'application/json;charset=utf-8' },
    //         body: JSON.stringify(body)
    //     });

    //     const json = await response.json();
    //     if (json.status === "ok") {
    //         alert("글 작성이 성공했습니다.");
    //         location.href = 'list';
    //         return;
    //     }
    //     alert("글 작성이 실패했습니다.");
    // }
</script>
