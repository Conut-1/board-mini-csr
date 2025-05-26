<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">회원상세보기</div>
            <div class="card-body">
                <dl class="row mb-0">
                    <dt class="col-sm-3">아이디</dt>
                    <dd class="col-sm-9" v-text="state.member.id"></dd>

                    <dt class="col-sm-3">이름</dt>
                    <dd class="col-sm-9" v-text="state.member.name"></dd>

                    <dt class="col-sm-3">이메일주소</dt>
                    <dd class="col-sm-9" v-text="state.member.email"></dd>

                    <dt class="col-sm-3">생년월일</dt>
                    <dd class="col-sm-9" v-text="state.member.birthDate"></dd>

                    <dt class="col-sm-3">성별</dt>
                    <dd class="col-sm-9" v-text="state.member.gender"></dd>

                    <dt class="col-sm-3">휴대전화번호</dt>
                    <dd class="col-sm-9" v-text="state.member.phoneNumber"></dd>

                    <dt class="col-sm-3">우편번호</dt>
                    <dd class="col-sm-9" v-text="state.member.postCode"></dd>

                    <dt class="col-sm-3">주소</dt>
                    <dd class="col-sm-9" v-text="state.member.address"></dd>

                    <dt class="col-sm-3">상세주소</dt>
                    <dd class="col-sm-9" v-text="state.member.detailAddress"></dd>

                    <!-- <dt class="col-sm-3">취미</dt>
                    <dd class="col-sm-9">독서, 여행, 운동</dd> -->
                </dl>
                <!-- TODO: 해당 멤버 로그인 상태일 때, 어드민일 때 출력 -->
                <router-link :to="{ name: 'memberUpdate', params: { id: state.member.id } }" class="btn btn-primary">수정</router-link>
                <!-- TODO: 해당 멤버 로그인 상태일 때 출력 -->
                <button type="button" class="btn btn-danger" id="withdraw-member-button">탈퇴</button>
            </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { reactive } from 'vue';
    import { useRoute } from 'vue-router';

    const state = reactive({ member: {}});

    fetchMember();

    async function fetchMember() {
        const route = useRoute();
        const response = await axios.get(`/api/member/detail/${route.params.id}`)
        state.member = response.data.member;
    }
    // const withdraw = document.body.querySelector("#withdraw-member-button");

    // withdraw.addEventListener("click", async () => {
    //     if (!confirm("정말 탈퇴하시겠습니까?")) return;

    //     const response = await fetch("withdraw", {
    //         method: 'post'
    //     });

    //     const json = await response.json();
    //     if (json.status === "ok") {
    //         location.href = "/board";
    //     }
    // });
</script>
