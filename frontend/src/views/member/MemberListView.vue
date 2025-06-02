<template>
    <div class="container my-3">
        <Paging v-bind="paging">
            <template v-slot:content>
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
                        <tr v-if="memberList.length === 0">
                            <td class="text-center" colspan="7" >검색결과가 없습니다</td>
                        </tr>
                        <MemberItem v-for="(member, index) in memberList"
                            :key="member.memberNo" v-bind="member"
                            :index="index" @unlock="setMemberLocked" />
                    </tbody>
                </table>
            </template>
        </Paging>
    </div>
</template>

<script setup>
    import MemberItem from '@/components/member/MemberItem.vue';
    import Paging from '@/components/paging/Paging.vue';
    import axios from 'axios';
    import { omit } from 'lodash';
    import { ref, watch } from 'vue';
    import { useRoute } from 'vue-router';

    const route = useRoute();

    const memberList = ref([]);
    const paging = ref({
        size: 0,
        empty: true,
        firstGroup: true,
        lastGroup: true,
        startPage: 0,
        endPage: 0,
        number: 0,
    });

    fetchMemberList({});
    watch(() => route.query, (current) => {
        fetchMemberList(current);
    });

    function setMemberLocked(index, locked) {
        memberList.value[index].locked = locked;
    }

    async function fetchMemberList(params) {
        // TODO: status 확인 후에 처리
        const response = await axios.get('/api/member/list', {
            params
        });
        memberList.value = response.data.paging.content;
        paging.value = omit(response.data.paging, ['content']);
    }
</script>
