import { defineStore } from "pinia";
import { ref } from "vue";

export const useRecipeRecommendationStore = defineStore(
  "recipeRecommendation",
  () => {
    const recipeRecommendationData = ref([]);
    function setRecipeRecommendationData(data) {
      recipeRecommendationData.value = data;
    }

    return { recipeRecommendationData, setRecipeRecommendationData };
  }
);
