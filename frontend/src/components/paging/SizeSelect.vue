<template>
    <select v-model="size" @change="changeSize" class="form-select w-6rem">
        <option v-for="option in sizeOptions" :value="option" v-text="`${option}개`" :class="{ selected: pageSize === option}"></option>
    </select>
</template>

<script setup>
    import { ref } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
    const router = useRouter();
    const sizeOptions = [10, 20, 30, 50, 100];

    const { pageSize } = defineProps({ pageSize: { type: Number, required: true } });

    const size = ref(sizeOptions[0]);

    function changeSize() {
        const query = { ...route.query };
        query.size = size.value;
        router.push({ name: route.name, query });
    }
</script>
