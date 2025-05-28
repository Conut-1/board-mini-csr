<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">게시물 목록</div>
            <div class="card-body">
                <div class="d-flex gap-2 mb-3">
                    <div class="input-group">
                        <input type="text" v-model="searchValue" class="form-control">
                        <button @click="search" class="btn btn-outline-primary">검색</button>
                    </div>
                    <select v-model="size" @change="changeSize" class="form-select w-6rem">
                        <option v-for="option in sizeOptions" :value="option" v-text="`${option}개`" :class="{ selected: paging.size === option}"></option>
                    </select>
                </div>
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
                        <tr v-if="paging.content.length === 0">
                            <td class="text-center" colspan="4" >검색결과가 없습니다</td>
                        </tr>
                        <PostItem v-for="post in paging.content" :key="post.postNo" v-bind="post"></PostItem>
                    </tbody>
                </table>
                <div class="d-flex">
                    <div class="col"></div>
                    <div v-if="!paging.empty" class="col-auto">
                        <ul class="pagination justify-content-center mb-0">
                            <li class="page-item" :class="{ disabled: paging.firstGroup }">
                                <router-link :to="{ name: 'postList', query: { ...route.query, page: paging.startPage - 1} }" class="page-link">이전</router-link>
                            </li>
                            <li v-for="page in range(paging.startPage, paging.endPage + 1)" class="page-item" :class="{ active: page == paging.number }">
                                <router-link :to="{ name: 'postList', query: { ...route.query, page } }" v-text="page" class="page-link"></router-link>
                            </li>
                            <li class="page-item" :class="{ disabled: paging.lastGroup }">
                                <router-link :to="{ name: 'postList', query: { ...route.query, page: paging.endPage + 1 } }" class="page-link">다음</router-link>
                            </li>
                        </ul>
                    </div>
                    <div class="d-flex col justify-content-end">
                        <router-link :to="{ name: 'postCreate' }" class="btn btn-primary">글쓰기</router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import PostItem from '@/components/post/PostItem.vue';
    import axios from 'axios';
    import { range } from 'lodash';
    import { ref, watch } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
    const router = useRouter();
    const sizeOptions = [10, 20, 30, 50, 100];

    const paging = ref({ content: []});
    const searchValue = ref(route.query.searchValue ?? '');
    const size = ref(sizeOptions[0]);

    fetchPaging({});
    watch(() => route.query, (current) => {
        fetchPaging(current);
    })

    async function fetchPaging(params) {
        const response = await axios.get('/api/post/list', {
            params
        });
        paging.value = response.data.paging;
    }

    function search() {
        const query = { searchValue: searchValue.value};
        if (route.query.size) {
            query.size = route.query.size;
        }
        router.push({ name: 'postList', query });
    }

    function changeSize() {
        const query = { ...route.query };
        query.size = size.value;
        router.push({ name: 'postList', query });
    }
</script>
