<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">회원정보수정</div>
            <div class="card-body">
                <form @submit.prevent="updateMember" id="update-form">
                    <div class="mb-3">
                        <label for="update-id" class="form-label">아이디</label>
                        <input type="text" :value="route.params.id" class="form-control" id="update-id" placeholder="아이디" disabled>
                    </div>
                    <div class="mb-3">
                        <label for="update-password" class="form-label">비밀번호</label>
                        <input type="password" v-model="member.password" class="form-control" id="update-password" placeholder="비밀번호">
                    </div>
                    <div class="mb-3">
                        <label for="update-name" class="form-label">이름</label>
                        <input type="text" v-model="member.name" class="form-control" id="update-name" placeholder="이름">
                    </div>
                    <div class="mb-3">
                        <label for="update-email" class="form-label">이메일주소</label>
                        <input type="email" v-model="member.email" class="form-control" id="update-email" placeholder="이메일주소">
                    </div>
                    <div class="mb-3">
                        <label for="update-birthdate" class="form-label">생년월일</label>
                        <input type="date" v-model="member.birthDate" class="form-control" id="update-birthdate" placeholder="생년월일">
                    </div>
                    <div class="mb-3">
                        <label class="form-label d-block">성별</label>
                        <div class="form-check form-check-inline">
                            <label class="form-check-label" for="update-radio-male">남자</label>
                            <input type="radio" v-model="member.gender" class="form-check-input" id="update-radio-male" value="M">
                        </div>
                        <div class="form-check form-check-inline">
                            <label class="form-check-label" for="update-radio-female">여자</label>
                            <input type="radio" v-model="member.gender" class="form-check-input" id="update-radio-female" value="F">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="update-phone-number" class="form-label">전화번호</label>
                        <input type="tel" v-model="member.phoneNumber" class="form-control" id="update-phone-number" placeholder="전화번호">
                    </div>
                    <div class="mb-3">
                        <label for="update-post-code" class="form-label">우편번호</label>
                        <input type="text" v-model="member.postCode" class="form-control" id="update-post-code" placeholder="우편번호">
                    </div>
                    <div class="mb-3">
                        <label for="update-address" class="form-label">주소</label>
                        <input type="text" v-model="member.address" class="form-control" id="update-address" placeholder="주소">
                    </div>
                    <div class="mb-3">
                        <label for="update-detail-address" class="form-label">상세주소</label>
                        <input type="text" v-model="member.detailAddress" class="form-control" id="update-detail-address" placeholder="상세주소">
                    </div>
                    <button type="submit" class="btn btn-primary" id="update-button">수정</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { reactive } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
    const router = useRouter();

    const defaultMember = {
        password: '',
        name: '',
        email: '',
        birthDate: '',
        gender: '',
        phoneNumber: '',
        postCode: '',
        address: '',
        detailAddress: '',
    };

    const member = reactive({ ...defaultMember });

    fetchMember();

    async function fetchMember() {
        const route = useRoute();
        const response = await axios.get(`/api/member/detail/${route.params.id}`);
        Object.assign(member, response.data.member);
    }

    async function updateMember() {
        // TODO: 포커스 처리
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$/
        if (!passwordPattern.test(member.password)) {
            alert("비밀번호가 유효하지 않습니다.");
            return;
        }

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(member.email)) {
            alert("유효한 이메일 주소를 입력해주세요.");
            return;
        }

        const today = new Date();
        if (new Date(member.birthDate) > today) {
            alert("생년월일이 유효하지 않습니다.");
            return;
        }

        const phonePattern = /^((01[016789])|(0\d{1,2}))-?\d{3,4}-?\d{4}$/;
        if (!phonePattern.test(member.phoneNumber)) {
            alert("전화번호가 유효하지 않습니다.");
            return;
        }

        const postCodePattern = /^\d{5}$/;
        if (!postCodePattern.test(member.postCode)) {
            alert("우편번호가 유효하지 않습니다.");
            postCode.focus();
            return;
        }

        if (member.address.length === 0) {
            alert("주소가 유효하지 않습니다.");
            return;
        }

        if (member.detailAddress.length === 0) {
            alert("주소가 유효하지 않습니다.");
            return;
        }

        if (!confirm("회원 정보를 수정하시겠습니까?")) return;

        const response = await axios.post("/api/member/update", member);

        if (response.data.status === "ok") {
            alert("회원 정보 수정이 성공했습니다.");
            router.push({ name: 'memberDetail', params: { id: route.params.id}});
            return;
        }
        alert("회원 정보 수정이 실패했습니다.");
    }
</script>
