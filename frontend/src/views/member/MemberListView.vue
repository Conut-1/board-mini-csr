<template>
    <div class="container my-3">
        <!-- <form th:action="@{/member/list}" class="d-flex gap-2 mb-3">
            <div class="input-group">
                <input type="text" class="form-control" name="searchValue" id="searchValue" th:value="${param.searchValue}">
                <input class="btn btn-outline-primary" type="submit" value="검색">
            </div>
            <select class="form-select w-6rem" name="size" id="size" th:with="sizes=${new int[]{10,20,30,50,100}}">
                <option th:each="size: ${sizes}" th:value="${size}" th:text="|${size}개|" th:selected="${paging.size == size}"></option>
            </select>
        </form> -->
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
                <tr v-for="member in memberList" :key="member.memberNo">
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
        <!-- <div th:if="${!paging.isEmpty()}">
            <ul class="pagination justify-content-center">
                <li class="page-item" th:classappend="${paging.firstGroup} ? 'disabled'">
                    <a class="page-link" th:href="@{|?page=${paging.startPage - 1}|}">
                        <span>이전</span>
                    </a>
                </li>
                <li th:each="page: ${#numbers.sequence(paging.startPage, paging.endPage)}"
                th:classappend="${page == paging.number} ? 'active'" class="page-item">
                    <a th:text="${page}" class="page-link" th:href="@{|?page=${page}|}"></a>
                </li>
                <li class="page-item" th:classappend="${paging.lastGroup} ? 'disabled'">
                    <a class="page-link" th:href="@{|?page=${paging.endPage + 1}|}">
                        <span>다음</span>
                    </a>
                </li>
            </ul>
        </div> -->
    </div>
</template>

<script setup>
    import { reactive } from 'vue';
    import axios from 'axios';

    const memberList = reactive([]);

    fetchMemberList();

    async function fetchMemberList() {
        // TODO: status 확인 후에 처리
        const response = await axios.get('/api/member/list');
        memberList.push(...response.data.memberList);
    }

    async function unlock(member) {
        // TODO: status 확인 후에 처리
        const response = await axios.post(`/api/member/unlock/${member.id}`);
        member.locked = false;
    }

	// const size = document.querySelector("#size");
	// const searchValue = document.querySelector("#searchValue")
	// const unlockButtons = document.querySelectorAll(".unlock-button");

	// size.addEventListener("change", e => {
	// 	location.href = "[[@{/member/list}]]" + "?size=" + size.value + "&searchValue=" + searchValue.value;	
	// });
	// unlockButtons.forEach(unlockButton => {
	// 	unlockButton.addEventListener("click", async (e) => {
	// 		e.preventDefault();

	// 		await fetch("[[@{/member/unlock}]]" + `?memberId=${unlockButton.getAttribute("target")}`, {
	// 			method: 'post'
	// 		});

	// 		location.href = "";
	// 	})
	// });
</script>
