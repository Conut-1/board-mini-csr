<template>
  <Nav />
  <router-view />
</template>

<script setup>
  import Nav from '@/components/Nav.vue';
  import axios from 'axios';
  import { ref } from 'vue';

  const id = ref('');

  fetchMe();

  async function fetchMe() {
    const response = await axios.get("/api/member/me");
    id.value = response.data.id;
  }
</script>
