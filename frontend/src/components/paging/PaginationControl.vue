<template>
    <ul class="pagination justify-content-center mb-0">
        <li class="page-item" :class="{ disabled: paging.firstGroup }">
            <router-link :to="{ name: route.name, query: { ...route.query, page: paging.startPage - 1} }" class="page-link">이전</router-link>
        </li>
        <li v-for="page in range(paging.startPage, paging.endPage + 1)" class="page-item" :class="{ active: page == paging.number }">
            <router-link :to="{ name: route.name, query: { ...route.query, page } }" v-text="page" class="page-link"></router-link>
        </li>
        <li class="page-item" :class="{ disabled: paging.lastGroup }">
            <router-link :to="{ name: route.name, query: { ...route.query, page: paging.endPage + 1 } }" class="page-link">다음</router-link>
        </li>
    </ul>
</template>

<script setup>
    import { range } from 'lodash';
    import { useRoute } from 'vue-router';

    const route = useRoute();

    const paging = defineProps({
        firstGroup: { type: Boolean, required: true},
        lastGroup: { type: Boolean, required: true},
        startPage: { type: Number, required: true },
        endPage: { type: Number, required: true },
        number: { type: Number, required: true }
    });
</script>
