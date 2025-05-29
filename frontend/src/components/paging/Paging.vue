<template>
    <div class="d-flex gap-2 mb-3">
        <SearchInput />
        <SizeSelect :pageSize="paging.size" />
    </div>
    <slot name="content"></slot>
    <div class="d-flex mb-3">
        <div class="col"></div>
        <div v-if="!paging.empty" class="col-auto">
            <PaginationControl v-bind="omit(paging, ['size', 'empty'])" />
        </div>
        <div class="d-flex col justify-content-end">
            <slot name="bottomRight"></slot>
        </div>
    </div>
</template>

<script setup>
    import PaginationControl from '@/components/paging/PaginationControl.vue';
    import SizeSelect from '@/components/paging/SizeSelect.vue';
    import SearchInput from '@/components/paging/SearchInput.vue';
    import { omit } from 'lodash';

    const paging = defineProps({
        size: { type: Number, required: true },
        empty: { type: Boolean, required: true},
        firstGroup: { type: Boolean, required: true},
        lastGroup: { type: Boolean, required: true},
        startPage: { type: Number, required: true },
        endPage: { type: Number, required: true },
        number: { type: Number, required: true }
    });
</script>
