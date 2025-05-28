<template>
    <tr>
        <td v-text="post.postNo"></td>
        <td><router-link :to="{ name: 'postDetail', params: { id: post.postNo } }" v-text="post.title"></router-link></td>
        <td v-text="post.createDate"></td>
        <td v-text="post.views"></td>
    </tr>
</template>

<script setup>
    const post = defineProps({
        postNo: { type: Number, required: true},
        title: { type: String, required: true},
        createDate: { type: String, required: true},
        views: { type: Number, required: true}
    });
</script>
