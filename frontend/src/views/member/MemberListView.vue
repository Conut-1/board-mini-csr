<template>
    <div class="container my-3">
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
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>생성일자</th>
                    <th>탈퇴여부</th>
                    <th>잠금해제</th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="paging.content.length === 0">
                    <td class="text-center" colspan="7" >검색결과가 없습니다</td>
                </tr>
                <tr v-for="member in paging.content" :key="member.memberNo">
                    <td v-text="member.memberNo"></td>
                    <td><router-link :to="{ name: 'memberDetail', params: { id: member.id }}" v-text="member.id"></router-link></td>
                    <td v-text="member.name"></td>
                    <td v-text="member.email"></td>
                    <td v-text="member.createDate"></td>
                    <td v-text="member.deleteDate != null ? 'Y' : 'N'"></td>
                    <td><button v-if="member.locked" @click="unlock(member)" class="unlock-button">잠금해제</button></td>
                </tr>
            </tbody>
        </table>
        <div v-if="!paging.empty">
            <ul class="pagination justify-content-center">
                <li class="page-item" :class="{ disabled: paging.firstGroup }">
                    <router-link :to="{ name: 'memberList', query: { ...route.query, page: paging.startPage - 1} }" class="page-link">이전</router-link>
                </li>
                <li v-for="page in range(paging.startPage, paging.endPage + 1)" class="page-item" :class="{ active: page == paging.number }">
                    <router-link :to="{ name: 'memberList', query: { ...route.query, page } }" v-text="page" class="page-link"></router-link>
                </li>
                <li class="page-item" :class="{ disabled: paging.lastGroup }">
                    <router-link :to="{ name: 'memberList', query: { ...route.query, page: paging.endPage + 1 } }" class="page-link">다음</router-link>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
    import { ref, watch } from 'vue';
    import axios from 'axios';
    import { range } from 'lodash';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
    const router = useRouter();
    const sizeOptions = [10, 20, 30, 50, 100];

    const paging = ref({ content: [] });
    const searchValue = ref(route.query.searchValue ?? '');
    const size = ref(sizeOptions[0]);

    fetchPaging(0);
    watch(() => route.query, (current) => {
        fetchPaging(current);
    });

    async function fetchPaging(params) {
        // TODO: status 확인 후에 처리
        const response = await axios.get('/api/member/list', {
            params
        });
        paging.value = response.data.paging;
    }

    function search() {
        const query = { searchValue: searchValue.value};
        if (route.query.size) {
            query.size = route.query.size;
        }
        router.push({ name: 'memberList', query });
    }

    function changeSize() {
        const query = { ...route.query };
        query.size = size.value;
        router.push({ name: 'memberList', query });
    }

    async function unlock(member) {
        // TODO: status 확인 후에 처리
        const response = await axios.post(`/api/member/unlock/${member.id}`);
        member.locked = false;
    }
</script>
