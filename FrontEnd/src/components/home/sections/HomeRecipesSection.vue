<template>
  <div id="recipes" class="flex flex-col section">
    <RouterLink to="/recipe" class="flex flex-row self-start ml-[420px] font-Notosans font-bold text-4xl">
      <span class="flex items-center">다양한 레시피를 찾아보세요</span>
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1 " stroke="currentColor"
        class="w-12 h-12 ml-2">
        <path strokeLinecap="round" strokeLinejoin="round" d="M13.5 4.5 21 12m0 0-7.5 7.5M21 12H3" />
      </svg>
    </RouterLink>

    <div class="flex flex-row w-1/3 gap-10 mt-12">
      <div v-for="recipe in topThreeRecipe" :key="recipe.id" class="flex flex-col text-left font-Notosans">
        <div class="w-56 h-48 cursor-pointer" @click="router.push(`/recipedetail/${recipe.id}`)">
          <img :src="recipe.imageUrl" alt="recipeImage" class="w-56 h-48">
          <div class="mt-5 text-xl font-bold">{{ recipe.name }}</div>
          <div class="mt-3 text-lg font-light">{{ recipe.info.substring(0, 60) + '...' }} </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import * as Recipe from '@/js/Recipe';
import { onMounted, ref } from 'vue';
import { RouterLink, useRouter } from 'vue-router';

const router = useRouter()
const topThreeRecipe = ref([])

onMounted(() => {
  Recipe.searchRecipes(
    (success) => {
      topThreeRecipe.value = success.data.slice(5, 8)
    },
    (error) => {
      console.error(error)
    }
  )
})


</script>

<style scoped></style>