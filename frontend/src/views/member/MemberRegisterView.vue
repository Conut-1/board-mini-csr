<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">회원가입</div>
            <div class="card-body">
                <form @submit.prevent="register" id="register-form">
                    <div class="mb-3">
                        <label for="register-id" class="form-label">아이디</label>
                        <div class="input-group">
                            <input type="text" v-model="member.id" class="form-control" id="register-id" placeholder="아이디">
                            <button type="button" @click="checkIdDuplicate" class="btn btn-outline-primary">중복확인</button>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="register-password" class="form-label">비밀번호</label>
                        <input type="password" v-model="member.password" class="form-control" id="register-password" placeholder="비밀번호">
                    </div>
                    <div class="mb-3">
                        <label for="register-password-check" class="form-label">비밀번호 확인</label>
                        <input type="password" v-model="passwordCheck" class="form-control" id="register-password-check" placeholder="비밀번호확인">
                    </div>
                    <div class="mb-3">
                        <label for="register-name" class="form-label">이름</label>
                        <input type="text" v-model="member.name" class="form-control" id="register-name" placeholder="이름">
                    </div>
                    <div class="mb-3">
                        <label for="register-email" class="form-label">이메일주소</label>
                        <input type="email" v-model="member.email" class="form-control" id="register-email" placeholder="이메일주소">
                    </div>
                    <div class="mb-3">
                        <label for="register-birthdate" class="form-label">생년월일</label>
                        <input type="date" v-model="member.birthDate" class="form-control" id="register-birthdate" placeholder="생년월일">
                    </div>
                    <div class="mb-3">
                        <label class="form-label d-block">성별</label>
                        <div class="input-group">
                            <div class="form-check form-check-inline">
                                <label class="form-check-label" for="register-radio-male">남자</label>
                                <input class="form-check-input" v-model="member.gender" type="radio" id="register-radio-male" value="M">
                            </div>
                            <div class="form-check form-check-inline">
                                <label class="form-check-label" for="register-radio-female">여자</label>
                                <input class="form-check-input" v-model="member.gender" type="radio" id="register-radio-female" value="F">
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="register-phone-number" class="form-label">전화번호</label>
                        <input type="tel" v-model="member.phoneNumber" class="form-control" id="register-phone-number" placeholder="전화번호">
                    </div>
                    <div class="mb-3">
                        <label for="register-post-code" class="form-label">우편번호</label>
                        <input type="text" v-model="member.postCode" class="form-control" id="register-post-code" placeholder="우편번호">
                    </div>
                    <div class="mb-3">
                        <label for="register-address" class="form-label">주소</label>
                        <input type="text" v-model="member.address" class="form-control" id="register-address" placeholder="주소">
                    </div>
                    <div class="mb-3">
                        <label for="register-detail-address" class="form-label">상세주소</label>
                        <input type="text" v-model="member.detailAddress" class="form-control" id="register-detail-address" placeholder="상세주소">
                    </div>
                    <!-- <div class="mb-3">
                        <fieldset>
                            <legend class="col-form-label pt-0">취미(복수 선택 가능)</legend>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="hobbies" value="reading" id="hobby_reading">
                                <label class="form-check-label" for="hobby_reading">독서</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="hobbies" value="traveling" id="hobby_traveling">
                                <label class="form-check-label" for="hobby_traveling">여행</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="hobbies" value="sports" id="hobby_sports">
                                <label class="form-check-label" for="hobby_sports">운동</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="hobbies" value="music" id="hobby_music">
                                <label class="form-check-label" for="hobby_music">음악감상</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="hobbies" value="movies" id="hobby_movies">
                                <label class="form-check-label" for="hobby_movies">영화</label>
                            </div>
                        </fieldset>
                    </div> -->
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

    const member = reactive({ ...defaultMember });
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
            const response = await axios.get("/api/member/isExistMemberId", {
                params: { memberId: member.id }
            });
            if (!response.data.isExist) {
                validChecked = true;
                validId = member.id;
            }
        }
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

    // function init() {
    //     validChecked = false;
    //     validId = "";
    //     checkIdValidity();
    // }

    // async function checkIdDuplicate() {
    //     const id = fields.get("id");
    //     if (8 <= id.value.length && id.value.length <= 20) {
    //         const response = await fetch(`isExistMemberId?memberId=${id.value}`, {
    //             method: 'get'
    //         });
        
    //         const json = await response.json();
        
    //         if (!json.isExist) {
    //             validChecked = true;
    //             validId = id.value;
    //         }
    //     }

    //     checkIdValidity();
    //     if (id.checkValidity()) {
    //         setValid(id);
    //     } else {
    //         setInvalid(id);
    //     }
    // }

    // function checkIdValidity() {
    //     const id = fields.get("id");
    //     const value = id.value;
    //     if (value.length < 8 || value.length > 20) {
    //         id.setCustomValidity("아이디는 8자 이상 20자 이하로 입력하세요.");
    //         return;
    //     }

    //     if (!validChecked || validId === "" || validId !== value) {
    //         id.setCustomValidity("아이디 중복을 검사해주세요");
    //         return;
    //     }

    //     id.setCustomValidity("");
    // }

    // function checkPasswordValidity() {
    //     const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$/;
    //     const password = fields.get("password");
    //     const value = password.value;
    //     if (!passwordPattern.test(value)) {
    //         password.setCustomValidity("비밀번호가 유효하지 않습니다.");
    //         return;
    //     }

    //     password.setCustomValidity("");
    // }

    // function checkPasswordCheckValidity() {
    //     const password = fields.get("password");
    //     const value = passwordCheck.value;
    //     if (password.value !== value) {
    //         passwordCheck.setCustomValidity("비밀번호 확인이 일치하지 않습니다.");
    //         return;
    //     }

    //     passwordCheck.setCustomValidity("");
    // }

    // function checkNameValidity() {
    //     const name = fields.get("name");
    //     const value = name.value;
    //     if (value.length === 0) {
    //         name.setCustomValidity("이름이 유효하지 않습니다.");
    //         return;
    //     }

    //     name.setCustomValidity("");
    // }

    // function checkEmailValidity() {
    //     const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    //     const email = fields.get("email");
    //     const value = email.value;
    //     if (!emailPattern.test(value)) {
    //         email.setCustomValidity("유효한 이메일 주소를 입력해주세요.");
    //         return;
    //     }

    //     email.setCustomValidity("");
    // }

    // function checkBirthDateValidity() {
    //     const today = new Date();
    //     const birthDate = fields.get("birthDate");
    //     const value = birthDate.value;
    //     if (new Date(value) > today) {
    //         birthDate.setCustomValidity("생년월일이 유효하지 않습니다.");
    //         return;
    //     }

    //     birthDate.setCustomValidity("");
    // }

    // function checkPhoneNumberValidity() {
    //     const phonePattern = /^((01[016789])|(0\d{1,2}))-?\d{3,4}-?\d{4}$/;
    //     const phoneNumber = fields.get("phoneNumber");
    //     const value = phoneNumber.value;
    //     if (!phonePattern.test(value)) {
    //         phoneNumber.setCustomValidity("전화번호가 유효하지 않습니다.");
    //         return;
    //     }

    //     phoneNumber.setCustomValidity("");
    // }

    // function checkPostCodeValidity() {
    //     const postCodePattern = /^\d{5}$/;
    //     const postCode = fields.get("postCode");
    //     const value = postCode.value;
    //     if (!postCodePattern.test(value)) {
    //         postCode.setCustomValidity("우편번호가 유효하지 않습니다.");
    //         return;
    //     }

    //     postCode.setCustomValidity("");
    // }

    // function checkAddressValidity() {
    //     const address = fields.get("address");
    //     const value = address.value;
    //     if (value.length === 0) {
    //         address.setCustomValidity("주소가 유효하지 않습니다.");
    //         return;
    //     }

    //     address.setCustomValidity("");
    // }

    // function checkDetailAddressValidity() {
    //     const detailAddress = fields.get("detailAddress");
    //     const value = detailAddress.value;
    //     if (value.length === 0) {
    //         detailAddress.setCustomValidity("상세주소가 유효하지 않습니다.");
    //         return;
    //     }

    //     detailAddress.setCustomValidity("");
    // }

    // function setValid(field) {
    //     field.classList.remove("is-invalid");
    //     field.classList.add("is-valid");

    //     const container = field.closest('.input-group') || field;
    //     const next = container.nextElementSibling;
    //     if (next && next.classList.contains("invalid-feedback")) {
    //         next.remove();
    //     }
    // }

    // function setInvalid(field) {
    //     field.classList.remove("is-valid");
    //     field.classList.add("is-invalid");

    //     const container = field.closest('.input-group') || field;
    //     const next = container.nextElementSibling;
    //     if (next && next.classList.contains("invalid-feedback")) {
    //         next.remove();
    //     }

    //     const message = field.validationMessage;
    //     const div = document.createElement("div");
    //     div.className = "invalid-feedback d-block";
    //     div.textContent = message;

    //     container.insertAdjacentElement("afterend", div);
    // }
</script>
