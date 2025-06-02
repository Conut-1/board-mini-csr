<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">게시물 목록</div>
            <div class="card-body">
                <Paging v-bind="paging">
                    <template v-slot:content>
                        <table class="table table-bordered table-hover">
                            <thead class="table-light">
                                <tr>
                                    <th>번호</th>
                                    <th>제목</th>
                                    <th>작성일</th>
                                    <th>조회수</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-if="postList.length === 0">
                                    <td class="text-center" colspan="4" >검색결과가 없습니다</td>
                                </tr>
                                <PostItem v-for="post in postList" :key="post.postNo" v-bind="post"></PostItem>
                            </tbody>
                        </table>
                    </template>
                    <template v-slot:bottomRight>
                        <router-link :to="{ name: 'postCreate' }" class="btn btn-primary">글쓰기</router-link>
                    </template>
                </Paging>
            </div>
        </div>
    </div>
</template>

<script setup>
    import Paging from '@/components/paging/Paging.vue';
    import PostItem from '@/components/post/PostItem.vue';
    import { server } from '@/config/server';
    import axios from 'axios';
    import { omit } from 'lodash';
    import { ref, watch } from 'vue';
    import { useRoute } from 'vue-router';

    const route = useRoute();

    const postList = ref([]);
    const paging = ref({
        size: 0,
        empty: true,
        firstGroup: true,
        lastGroup: true,
        startPage: 0,
        endPage: 0,
        number: 0
    });

    fetchPostList({});
    watch(() => route.query, (current) => {
        fetchPostList(current);
    })

    async function fetchPostList(params) {
        const response = await axios.get(server.route.post.list, {
            params
        });
        postList.value = response.data.paging.content;
        paging.value = omit(response.data.paging, ['content']);
    }
</script>
