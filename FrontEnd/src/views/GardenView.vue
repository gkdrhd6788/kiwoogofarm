<template>
    <v-container class="main-container">
        <GardenComponent />
    </v-container>
</template>

<script setup>
import GardenComponent from '@/components/garden/GardenComponent.vue';
</script>

<style scoped>
.main-content {
  flex: 1;
  max-width: max-content;
  padding: 1em;
}
</style>