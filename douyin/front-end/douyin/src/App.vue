<script setup>
import { defineComponent } from 'vue';
import DefaultLayout from './views/layout/index.vue'
defineComponent({
  DefaultLayout
})
</script>

<template>
  <DefaultLayout />
</template>

<!-- 去掉 Vuetify v-btn 在聚焦（button:focus、button:focus-visible）时出现的默认蓝色边框（focus ring） -->
<style lang="scss">
.v-btn:focus,
.v-btn:focus-visible {
  outline: none !important;
  box-shadow: none !important;
}
</style>