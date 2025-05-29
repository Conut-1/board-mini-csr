<template>
    <div class="input-group">
        <input type="text" v-model="searchValue" class="form-control">
        <button @click="search" class="btn btn-outline-primary">검색</button>
    </div>
</template>

<script setup>
    import { ref } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
    const router = useRouter();

    const searchValue = ref(route.query.searchValue ?? '');

    function search() {
        const query = { searchValue: searchValue.value };
        if (route.query.size) {
            query.size = route.query.size;
        }
        router.push({ name: route.name, query });
    }
</script>
