<template>
    <div class="container mt-3 mb-3">
        <div class="card">
            <div class="card-header">게시물 목록</div>
            <div class="card-body">
                <!-- <form action="${pageContext.request.contextPath}/post/list" class="d-flex gap-2 mb-3">
                    <div class="input-group">
                        <input type="text" class="form-control" name="searchValue" id="searchValue" value="${param.searchValue}">
                        <input class="btn btn-outline-primary" type="submit" value="검색">
                    </div>
                    <select class="form-select w-6rem" name="size" id="size">
                        <c:forTokens items="10,20,30,50,100" delims="," var="size">
                            <option value="${ size }" ${pageResponse.size == size ? 'selected' : ''}>${ size }개</option>
                        </c:forTokens>
                    </select>
                </form> -->
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
                        <tr v-for="post in postList" :key="post.postNo">
                            <td v-text="post.postNo"></td>
                            <td><router-link :to="{ name: 'postDetail', params: { id: post.postNo } }" v-text="post.title"></router-link></td>
                            <td v-text="post.createDate"></td>
                            <td v-text="post.views"></td>
                        </tr>
                    </tbody>
                </table>
                <!-- <div class="d-flex">
                    <div class="col"></div>
                    <nav class="col-auto">
                        <ul class="pagination mb-0">
                            <li class="page-item ${ pageResponse.firstGroup ? 'disabled' : '' }">
                                <a class="page-link" href="${pageContext.request.contextPath}/post/list?page=${ pageResponse.startPage - 1 }&size=${ pageResponse.size }&searchValue=${param.searchValue}">이전</a>
                            </li>
                            <c:forEach var="pageNo" begin="${ pageResponse.startPage }" end="${ pageResponse.endPage }">
                                <li class="page-item ${ pageNo == pageResponse.pageNo ? 'active' : '' }"><a class="page-link" href="${pageContext.request.contextPath}/post/list?page=${ pageNo }&size=${ pageResponse.size }&searchValue=${param.searchValue}">${ pageNo }</a></li>
                            </c:forEach>
                            <li class="page-item ${ pageResponse.lastGroup ? 'disabled' : '' }">
                                <a class="page-link" href="${pageContext.request.contextPath}/post/list?page=${ pageResponse.endPage + 1 }&size=${ pageResponse.size }&searchValue=${param.searchValue}">다음</a>
                            </li>
                        </ul>
                    </nav>
                    <div class="d-flex col justify-content-end">
                        <a href="${pageContext.request.contextPath}/post/create" class="btn btn-primary">글쓰기</a>
                    </div>
                </div> -->
            </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { reactive } from 'vue';

    const postList = reactive([]);

    fetchPostList();

    async function fetchPostList() {
        const response = await axios.get('/api/post/list');
        postList.push(...response.data.postList);
    }

    // const contextDiv = document.body.querySelector("#context");
    // const contextPath = contextDiv.dataset.contextPath;

    // const size = document.querySelector("#size");
    // const searchValue = document.querySelector("#searchValue")
    // size.addEventListener("change", e => {
    //     location.href = contextPath + "/post/list?size=" + size.value + "&searchValue=" + searchValue.value;	
    // });
</script>
