<template>
    <div class="d-flex gap-2 mb-3">
        <div class="input-group">
            <input type="text" v-model="searchValue" class="form-control">
            <button @click="search" class="btn btn-outline-primary">검색</button>
        </div>
        <select v-model="size" @change="changeSize" class="form-select w-6rem">
            <option v-for="option in sizeOptions" :value="option" v-text="`${option}개`" :class="{ selected: paging.size === option}"></option>
        </select>
    </div>
    <slot name="content"></slot>
    <div class="d-flex mb-3">
        <div class="col"></div>
        <div v-if="!paging.empty" class="col-auto">
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
        </div>
        <div class="d-flex col justify-content-end">
            <slot name="bottomRight"></slot>
        </div>
    </div>
</template>

<script setup>
    import { range } from 'lodash';
    import { ref } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
    const router = useRouter();
    const sizeOptions = [10, 20, 30, 50, 100];

    const paging = defineProps({
        size: { type: Number, required: true },
        empty: { type: Boolean, required: true},
        firstGroup: { type: Boolean, required: true},
        lastGroup: { type: Boolean, required: true},
        startPage: { type: Number, required: true },
        endPage: { type: Number, required: true },
        number: { type: Number, required: true }
    });

    const searchValue = ref(route.query.searchValue ?? '');
    const size = ref(sizeOptions[0]);

    function search() {
        const query = { searchValue: searchValue.value};
        if (route.query.size) {
            query.size = route.query.size;
        }
        router.push({ name: route.name, query });
    }

    function changeSize() {
        const query = { ...route.query };
        query.size = size.value;
        router.push({ name: route.name, query });
    }
</script>
