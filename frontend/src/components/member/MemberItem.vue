<template>
    <tr>
        <td v-text="member.memberNo"></td>
        <td><router-link :to="{ name: 'memberDetail', params: { id: member.id }}" v-text="member.id"></router-link></td>
        <td v-text="member.name"></td>
        <td v-text="member.email"></td>
        <td v-text="createDate"></td>
        <td v-text="member.deleteDate != null ? 'Y' : 'N'"></td>
        <td><button v-if="member.locked" @click="unlock(member)" class="unlock-button">잠금해제</button></td>
    </tr>
</template>

<script setup>
    import axios from 'axios';
    import format from 'date-format';
    import { computed } from 'vue';

    const member = defineProps({
        memberNo: { type: Number, required: true},
        id: { type: String, required: true},
        name: { type: String, required: true},
        email: { type: String, required: true},
        createDate: { type: String, required: true},
        deleteDate: { type: String },
        locked: { type: Boolean, required: true},
        index: { type: Number, required: true}
    });
    const emit = defineEmits(['unlock']);

    const createDate = computed(() => format("yyyy-MM-dd", new Date(member.createDate)));

    async function unlock(member) {
        // TODO: status 확인 후에 처리
        await axios.post(`/api/member/unlock/${member.id}`);
        emit('unlock', member.index, false);
    }
</script>
