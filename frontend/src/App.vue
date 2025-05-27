<template>
  <Nav />
  <router-view />
</template>

<script setup>
  import Nav from '@/components/Nav.vue';
  import axios from 'axios';
  import { computed, provide, reactive, ref } from 'vue';

  const id = ref('');
  const roles = reactive(['test']);

  provide('id', computed(() => id.value));
  provide('roles', computed(() => roles));
  provide('actions', { cleanMe, fetchMe });

  fetchMe();

  function cleanMe() {
    id.value = '';
    roles.length = 0;
  }

  async function fetchMe() {
    const response = await axios.get("/api/member/me");
    cleanMe();
    id.value = response.data.id;
    if (response.data.roles) {
      roles.push(...response.data.roles);
    }
  }
</script>
