<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">회원가입</div>
            <div class="card-body">
                <form @submit.prevent="register" id="register-form">
                    <div class="mb-3">
                        <label for="register-id" class="form-label">아이디</label>
                        <div class="input-group">
                            <input type="text" v-model="member.id" @input="checkIdValidity"
                                :class="getValidityClass('id')" class="form-control"
                                id="register-id" placeholder="아이디">
                            <button type="button" @click="checkIdDuplicate" class="btn btn-outline-primary">중복확인</button>
                        </div>
                        <div v-show="invalidity.id" v-text="invalidity.id" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-password" class="form-label">비밀번호</label>
                        <input type="password" v-model="member.password" @input="checkPasswordValidity"
                            :class="getValidityClass('password')" class="form-control"
                            id="register-password" placeholder="비밀번호">
                        <div v-show="invalidity.password" v-text="invalidity.password" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-password-check" class="form-label">비밀번호 확인</label>
                        <input type="password" v-model="passwordCheck" @input="checkPasswordCheckValidity"
                            :class="getValidityClass('passwordCheck')" class="form-control"
                            id="register-password-check" placeholder="비밀번호확인">
                        <div v-show="invalidity.passwordCheck" v-text="invalidity.passwordCheck" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-name" class="form-label">이름</label>
                        <input type="text" v-model="member.name" @input="checkNameValidity"
                            :class="getValidityClass('name')" class="form-control"
                            id="register-name" placeholder="이름">
                        <div v-show="invalidity.name" v-text="invalidity.name" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-email" class="form-label">이메일주소</label>
                        <input type="email" v-model="member.email" @input="checkEmailValidity"
                            :class="getValidityClass('email')" class="form-control"
                            id="register-email" placeholder="이메일주소">
                        <div v-show="invalidity.email" v-text="invalidity.email" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-birthdate" class="form-label">생년월일</label>
                        <input type="date" v-model="member.birthDate" @change="checkBirthDateValidity"
                            :class="getValidityClass('birthDate')" class="form-control"
                            id="register-birthdate" placeholder="생년월일">
                        <div v-show="invalidity.birthDate" v-text="invalidity.birthDate" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label d-block">성별</label>
                        <div class="input-group">
                            <div class="form-check form-check-inline">
                                <label class="form-check-label" for="register-radio-male">남자</label>
                                <input v-model="member.gender" :class="getValidityClass('gender')" @change="checkGenderValidity"
                                class="form-check-input" type="radio" id="register-radio-male" value="M">
                            </div>
                            <div class="form-check form-check-inline">
                                <label class="form-check-label" for="register-radio-female">여자</label>
                                <input v-model="member.gender" :class="getValidityClass('gender')" @change="checkGenderValidity"
                                class="form-check-input" type="radio" id="register-radio-female" value="F">
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="register-phone-number" class="form-label">전화번호</label>
                        <input type="tel" v-model="member.phoneNumber" @input="checkPhoneNumberValidity"
                            :class="getValidityClass('phoneNumber')" class="form-control"
                            id="register-phone-number" placeholder="전화번호">
                        <div v-show="invalidity.phoneNumber" v-text="invalidity.phoneNumber" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-post-code" class="form-label">우편번호</label>
                        <input type="text" v-model="member.postCode" @input="checkPostCodeValidity"
                            :class="getValidityClass('postCode')" class="form-control"
                            id="register-post-code" placeholder="우편번호">
                        <div v-show="invalidity.postCode" v-text="invalidity.postCode" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-address" class="form-label">주소</label>
                        <input type="text" v-model="member.address" @input="checkAddressValidity"
                            :class="getValidityClass('address')" class="form-control"
                            id="register-address" placeholder="주소">
                        <div v-show="invalidity.address" v-text="invalidity.address" class="invalid-feedback d-block"></div>
                    </div>
                    <div class="mb-3">
                        <label for="register-detail-address" class="form-label">상세주소</label>
                        <input type="text" v-model="member.detailAddress" @input="checkDetailAddressValidity"
                            :class="getValidityClass('detailAddress')" class="form-control"
                            id="register-detail-address" placeholder="상세주소">
                        <div v-show="invalidity.detailAddress" v-text="invalidity.detailAddress" class="invalid-feedback d-block"></div>
                    </div>
                    <button type="submit" class="btn btn-primary" id="register-button">회원가입</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { reactive, ref } from 'vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const defaultMember = {
        id: '',
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
    const touched = reactive({
        id: false,
        password: false,
        passwordCheck: false,
        name: false,
        email: false,
        birthDate: false,
        gender: false,
        phoneNumber: false,
        postCode: false,
        address: false,
        detailAddress: false
    });

    const member = reactive({ ...defaultMember });
    const invalidity = reactive({ ...defaultMember, passwordCheck: '' });
    const passwordCheck = ref('');

    // TODO: 유효성 검사
    let validChecked = false;
    let validId = "";

    async function register() {
        try {
            await axios.post('/api/member/register', member);
            alert("회원가입이 성공했습니다.");
            router.push({ name: 'home' });
        } catch (e) {
            alert('회원가입이 실패했습니다.');
        }
    }

    async function checkIdDuplicate() {
        if (8 <= member.id.length && member.id.length <= 20) {
            const memberId = member.id;
            const response = await axios.get("/api/member/isExistMemberId", {
                params: { memberId }
            });
            if (!response.data.isExist) {
                validId = memberId;
                if (memberId === member.id) {
                    validChecked = true;
                    invalidity.id = "";
                }
            }
        }
    }

    function getValidityClass(key) {
        if (!touched[key]) {
            return '';
        }
        return invalidity[key] ? 'is-invalid' : 'is-valid';
    }

    function checkIdValidity() {
        touched.id = true;

        if (member.id.length < 8 || member.id.length > 20) {
            invalidity.id = "아이디는 8자 이상 20자 이하로 입력하세요.";
            return;
        }

        if (!validChecked || validId === "" || validId !== member.id) {
            invalidity.id = "아이디 중복을 검사해주세요";
            return;
        }

        invalidity.id = "";
    }

    function checkPasswordValidity() {
        touched.password = true;

        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$/;
        if (!passwordPattern.test(member.password)) {
            invalidity.password = "비밀번호가 유효하지 않습니다.";
            return;
        }

        invalidity.password = "";
    }

    function checkPasswordCheckValidity() {
        touched.passwordCheck = true;

        if (member.password !== passwordCheck.value) {
            invalidity.passwordCheck = "비밀번호 확인이 일치하지 않습니다.";
            return;
        }

        invalidity.passwordCheck = "";
    }

    function checkNameValidity() {
        touched.name = true;

        if (member.name.length === 0) {
            invalidity.name = "이름이 유효하지 않습니다.";
            return;
        }

        invalidity.name = "";
    }

    function checkEmailValidity() {
        touched.email = true;

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(member.email)) {
            invalidity.email = "유효한 이메일 주소를 입력해주세요.";
            return;
        }

        invalidity.email = "";
    }

    function checkBirthDateValidity() {
        touched.birthDate = true;

        if (new Date(member.birthDate) > new Date()) {
            invalidity.birthDate = "생년월일이 유효하지 않습니다.";
            return;
        }

        invalidity.birthDate = "";
    }

    function checkGenderValidity() {
        touched.gender = true;

        if (member.gender.length === 0) {
            invalidity.gender = "성별을 선택해주세요.";
            return;
        }

        invalidity.gender = "";
    }

    function checkPhoneNumberValidity() {
        touched.phoneNumber = true;

        const phonePattern = /^((01[016789])|(0\d{1,2}))-?\d{3,4}-?\d{4}$/;
        if (!phonePattern.test(member.phoneNumber)) {
            invalidity.phoneNumber = "전화번호가 유효하지 않습니다.";
            return;
        }

        invalidity.phoneNumber = "";
    }

    function checkPostCodeValidity() {
        touched.postCode = true;

        const postCodePattern = /^\d{5}$/;
        if (!postCodePattern.test(member.postCode)) {
            invalidity.postCode = "우편번호가 유효하지 않습니다.";
            return;
        }

        invalidity.postCode = "";
    }

    function checkAddressValidity() {
        touched.address = true;

        if (member.address.length === 0) {
            invalidity.address = "주소가 유효하지 않습니다.";
            return;
        }

        invalidity.address = "";
    }

    function checkDetailAddressValidity() {
        touched.detailAddress = true;

        if (member.detailAddress.length === 0) {
            invalidity.detailAddress = "상세주소가 유효하지 않습니다.";
            return;
        }

        invalidity.detailAddress = "";
    }

    // const registerForm = document.querySelector("#register-form");
    // const validCheckers = new Map([
    //     ["id", checkIdValidity],
    //     ["password", checkPasswordValidity],
    //     ["name", checkNameValidity],
    //     ["email", checkEmailValidity],
    //     ["birthDate", checkBirthDateValidity],
    //     ["phoneNumber", checkPhoneNumberValidity],
    //     ["postCode", checkPostCodeValidity],
    //     ["address", checkAddressValidity],
    //     ["detailAddress", checkDetailAddressValidity]
    // ]);
    // const fields = new Map(Array.from(validCheckers.keys()).map(field => [field, registerForm.querySelector(`input[name="${field}"`)]));
    // const validCheckButton = registerForm.querySelector("#valid-check-button");
    // const passwordCheck = registerForm.querySelector("#register-password-check");
    // const genderChecked = registerForm.querySelector('input[name="gender"]:checked');

    // window.addEventListener("pageshow", init);
    // registerForm.addEventListener("submit", register);
    // validCheckButton.addEventListener("click", checkIdDuplicate);
    // fields.forEach((input, field) => {
    //     input.addEventListener("input", () => {
    //         validCheckers.get(field)();
    //         if (input.checkValidity()) {
    //             setValid(input);
    //         } else {
    //             setInvalid(input);
    //         }
    //     })
    //     input.addEventListener("invalid", (e) => {
    //         e.preventDefault();
    //         setInvalid(input);
    //     })
    // });
    // passwordCheck.addEventListener("input", () => {
    //     checkPasswordCheckValidity();
    //     if (passwordCheck.checkValidity()) {
    //         setValid(passwordCheck);
    //     } else {
    //         setInvalid(passwordCheck);
    //     }
    // });
    // passwordCheck.addEventListener("invalid", (e) => {
    //     e.preventDefault();
    //     setInvalid(passwordCheck);
    // });

    // registerForm.querySelectorAll('input[name="gender"]').forEach(input => {
    //     input.addEventListener("change", () => {
    //         fields.set("gender", input)
    //     });

    //     input.addEventListener("invalid", (e) => {
    //         e.preventDefault();
    //     });
    // });

    // const gender = registerForm.querySelector('input[name="gender"]');
    // gender.addEventListener("invalid", (e) => {
    //     setInvalid(gender);
    // });

    // async function register(e) {
    //     e.preventDefault();

    //     // const hobbies = form.querySelectorAll("input[name='hobbies']:checked");
    //     // if (hobbies.length === 0) {
    //     //     alert("하나 이상의 취미를 선택해주세요.");
    //     //     return;
    //     // }

    //     if (!confirm("회원 가입하시겠습니까?")) return;

    //     const body = { gender: genderChecked.value };
    //     Array.from(fields.entries()).forEach(([field, element]) => {body[field] = element.value});

    //     const response = await fetch("register", {
    //         method: 'post',
    //         headers: { 'Content-Type': 'application/json;charset=utf-8' },
    //         body: JSON.stringify(body)
    //     });

    //     const json = await response.json();
    //     if (json.status === "ok") {
    //         alert("회원가입이 성공했습니다.");
    //         location.href = 'login';
    //         return;
    //     }
    //     alert("회원가입이 실패했습니다.");
    // }
</script>
