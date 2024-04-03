<template>
  <div>
    <!--결과 화면-->
    <div v-if="result.length === 0" class="flex flex-col justify-center items-center">
      <h2 class="mt-[12%] text-3xl font-medium font-Notosans">죄송합니다. 조건에 맞는 레시피가 없습니다.</h2>
      <img src="@/assets/NoResult.jpg" alt="" class="w-96 h-64 mt-12">
      <router-link to="/recipeselect">
        <div class="rounded-full bg-[#00B564] px-24 py-3 mt-16  text-lg font-Notosans font-normal text-white">다시 추천받기</div>
      </router-link>
    </div>
    <div v-else class="flex flex-col items-center justify-center">
      <h2 class="mt-40 text-3xl font-medium font-Notosans">당신을 위한 레시피는</h2>

      <!--추천하는 레시피 리스트-->
      <swiper :effect="'coverflow'" :grabCursor="true" :centeredSlides="true" :slidesPerView="'auto'" :coverflowEffect="{
        rotate: 50,
        stretch: 0,
        depth: 100,
        modifier: 1,
        slideShadows: true,
      }" :pagination="true" :spaceBetween="20">
        <swiper-slide v-for="(recipe, index) in result" :key="index">
          <div class="flex flex-col items-center slide-content" @click="router.push(`/recipedetail/${recipe.id}`)">
            <img :src="recipe.imageUrl" :alt="`Image ${index + 1}`" referrerpolicy="no-referrer" />
            <div class="mt-8 px-5 text-xl font-semibold font-Notosans ">{{ recipe.name }}</div>
            <div class="flex flex-row items-center mt-auto mb-5">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"  class="w-5 h-5 mt-1 fill-current text-[#EC6B6B]">
                <path d="m9.653 16.915-.005-.003-.019-.01a20.759 20.759 0 0 1-1.162-.682 22.045 22.045 0 0 1-2.582-1.9C4.045 12.733 2 10.352 2 7.5a4.5 4.5 0 0 1 8-2.828A4.5 4.5 0 0 1 18 7.5c0 2.852-2.044 5.233-3.885 6.82a22.049 22.049 0 0 1-3.744 2.582l-.019.01-.005.003h-.002a.739.739 0 0 1-.69.001l-.002-.001Z" />
              </svg>
              <span class="font-Notosans font-normal text-sm ml-1 mt-0.5">{{recipe.likes}}</span>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="mt-1 ml-5 w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
              </svg>
              <span class="font-Notosans font-normal text-sm ml-1 mt-0.5">
                {{ recipe.cookTime ? recipe.cookTime : '&nbsp;&nbsp;-&nbsp;&nbsp;' }}
              </span>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="mt-1 ml-5 w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
              </svg>
              <span class="font-Notosans font-normal text-sm ml-1 mt-0.5">
                {{ recipe.servings ? recipe.servings : '&nbsp;&nbsp;-&nbsp;&nbsp;' }}
              </span>
            </div>
          </div>
        </swiper-slide>
      </swiper>
    </div>
  </div>
</template>

<script setup>
import { useRecipeRecommendationStore } from '@/stores/recipeRecommend';
import "swiper/swiper.min.css";
import { Swiper, SwiperSlide } from "swiper/vue";
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const recipeRecommendationStore = useRecipeRecommendationStore();
const router = useRouter();

const route = useRoute();
const isLoading = ref(true);
const result = ref([]);

onMounted(() => {
  result.value = recipeRecommendationStore.recipeRecommendationData;
});

</script>

<style scoped>
.swiper {
  width: 100%;
  padding-top: 50px;
  padding-bottom: 50px;
}

.swiper-slide {
  background-size: cover;
  width: 300px;
  height: 360px ;
  font-size: 24px;
  font-weight: 700;
  display: flex;
  justify-content: center;
  background: white;
  border-radius: 0.375rem;
  box-shadow: rgba(0, 0, 0, 0.25) 0px 4px 15px 0px;
}

.slide-content img {
  width: 300px;
  height: 230px;
  object-fit: cover;
}

</style>