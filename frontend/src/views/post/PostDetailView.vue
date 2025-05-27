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
                    <!-- TODO: 관리자 삭제 -->
                    <button type="button" class="btn btn-danger" id="admin-delete-post-button">관리자 삭제</button>
                    <!-- TODO: 사용자 삭제 -->
                    <button type="button" class="btn btn-danger" id="delete-post-button">삭제</button>
                    <input type="password" class="form-control w-6rem" id="delete-post-password">
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { reactive } from 'vue';
    import { useRoute } from 'vue-router';

    const route = useRoute();

    const post = reactive({});

    fetchPost();

    async function fetchPost() {
        const response = await axios.get(`/api/post/detail/${route.params.id}`);
        Object.assign(post, response.data.post);
    }

    // const adminDeletePostButton = document.querySelector("#admin-delete-post-button");
    // if (adminDeletePostButton) {
    //     adminDeletePostButton.addEventListener("click", adminUpdatePost);
    // }

    // async function adminUpdatePost(e) {
    //     e.preventDefault();

    //     const postNo = document.querySelector("#delete-post-post-no");

    //     if (!confirm("게시글을 삭제하시겠습니까?")) return;

    //     const body = {
    //         postNo: postNo.value,
    //     };

    //     const response = await fetch("delete", {
    //         method: 'post',
    //         headers: { 'Content-Type': 'application/json;charset=utf-8' },
    //         body: JSON.stringify(body)
    //     });

    //     const json = await response.json();
    //     if (json.status === "ok") {
    //         alert("게시글 삭제가 성공했습니다.");
    //         location.href = "list";
    //         return;
    //     }
    //     alert("게시글 삭제가 실패했습니다.");
    // }

    // const deletePostButton = document.querySelector("#delete-post-button");
    // if (deletePostButton) {
    //     deletePostButton.addEventListener("click", updatePost);
    // }

    // async function updatePost(e) {
    //     e.preventDefault();

    //     const postNo = document.querySelector("#delete-post-post-no");
    //     const password = document.querySelector("#delete-post-password");

    //     if (password.value.length === 0) {
    //         password.focus();
    //         return;
    //     }

    //     if (!confirm("게시글을 삭제하시겠습니까?")) return;

    //     const body = {
    //         postNo: postNo.value,
    //         password: password.value,
    //     };

    //     const response = await fetch("delete", {
    //         method: 'post',
    //         headers: { 'Content-Type': 'application/json;charset=utf-8' },
    //         body: JSON.stringify(body)
    //     });

    //     const json = await response.json();
    //     if (json.status === "ok") {
    //         alert("게시글 삭제가 성공했습니다.");
    //         location.href = "list";
    //         return;
    //     }
    //     alert("게시글 삭제가 실패했습니다.");
    // }
</script>
