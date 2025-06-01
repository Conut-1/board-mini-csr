<template>
    <tr>
        <td v-text="post.postNo"></td>
        <td><router-link :to="{ name: 'postDetail', params: { id: post.postNo } }" v-text="post.title"></router-link></td>
        <td v-text="createDate"></td>
        <td v-text="post.views"></td>
    </tr>
</template>

<script setup>
    import format from 'date-format';
    import { computed } from 'vue';

    const post = defineProps({
        postNo: { type: Number, required: true},
        title: { type: String, required: true},
        createDate: { type: String, required: true},
        views: { type: Number, required: true}
    });

    const createDate = computed(() => format("yyyy-MM-dd", new Date(post.createDate)));
</script>
