<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">회원상세보기</div>
            <div class="card-body">
                <dl class="row mb-0">
                    <dt class="col-sm-3">아이디</dt>
                    <dd class="col-sm-9" v-text="member.id"></dd>

                    <dt class="col-sm-3">이름</dt>
                    <dd class="col-sm-9" v-text="member.name"></dd>

                    <dt class="col-sm-3">이메일주소</dt>
                    <dd class="col-sm-9" v-text="member.email"></dd>

                    <dt class="col-sm-3">생년월일</dt>
                    <dd class="col-sm-9" v-text="member.birthDate"></dd>

                    <dt class="col-sm-3">성별</dt>
                    <dd class="col-sm-9" v-text="member.gender"></dd>

                    <dt class="col-sm-3">휴대전화번호</dt>
                    <dd class="col-sm-9" v-text="member.phoneNumber"></dd>

                    <dt class="col-sm-3">우편번호</dt>
                    <dd class="col-sm-9" v-text="member.postCode"></dd>

                    <dt class="col-sm-3">주소</dt>
                    <dd class="col-sm-9" v-text="member.address"></dd>

                    <dt class="col-sm-3">상세주소</dt>
                    <dd class="col-sm-9" v-text="member.detailAddress"></dd>

                    <!-- <dt class="col-sm-3">취미</dt>
                    <dd class="col-sm-9">독서, 여행, 운동</dd> -->
                </dl>
                <template v-if="id === route.params.id">
                    <router-link :to="{ name: 'memberUpdate', params: { id: member.id } }" class="btn btn-primary">수정</router-link>
                    <WithdrawMemberButton />
                </template>
            </div>
        </div>
    </div>
</template>

<script setup>
    import WithdrawMemberButton from '@/components/member/WithdrawMemberButton.vue';
    import axios from 'axios';
    import { inject, reactive } from 'vue';
    import { useRoute } from 'vue-router';

    const route = useRoute();

    const id = inject('id');

    const member = reactive({});

    fetchMember();

    async function fetchMember() {
        // TODO: 실패 처리?
        const response = await axios.get(`/api/member/detail/${route.params.id}`);
        Object.assign(member, response.data.member);
    }
</script>
