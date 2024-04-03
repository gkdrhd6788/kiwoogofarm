<template>
    <v-container class="main-container">
        <RecipeComponent />
    </v-container>
</template>

<script setup>
import RecipeComponent from '@/components/recipe/RecipeComponent.vue';
</script>

<style scoped>
.main-content {
  flex: 1;
  max-width: max-content;
  padding: 1em;
}
</style>