<template>
  <div>
    <!--결과 화면-->
    <div class="flex flex-col items-center justify-center">
      <h2 class="mt-40 text-3xl font-medium font-Notosans">당신을 위한 농작물은</h2>

      <!--추천하는 농작물 리스트-->
      <swiper
          :effect="'coverflow'"
          :grabCursor="true"
          :centeredSlides="true"
          :slidesPerView="'auto'"
          :coverflowEffect="{
      rotate: 50,
      stretch: 0,
      depth: 100,
      modifier: 1,
      slideShadows: true,
    }"
          :pagination="true"
          :spaceBetween="20"
      >
        <swiper-slide v-for="(crop, index) in result" :key="index">
          <div class="flex flex-col items-center slide-content" @click="router.push(`/search/${crop.id}`)">
            <img :src="crop.image_url" :alt="`Image ${index + 1}`" referrerpolicy="no-referrer" />
            <div class="mt-5 text-xl font-normal font-Notosans ">{{crop.name}}</div>
          </div>
        </swiper-slide>
      </swiper>

      <router-link to="/recipe" class="ml-16 text-3xl mt-12 text-[#00B564] font-Notosans font-medium cursor-pointer">
        요리추천 받으러 가기
        <v-icon
            class="mb-2"
            icon="mdi-arrow-right"
            start
        ></v-icon>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { useRecommendationStore } from '@/stores/recommend';
import "swiper/swiper.min.css";
import { Swiper, SwiperSlide } from "swiper/vue";
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const recommendationStore = useRecommendationStore();
const router = useRouter();

  const route = useRoute();
const isLoading = ref(true);
const result = ref([]);

onMounted(() => {
    result.value = recommendationStore.recommendationData;
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
  height: 300px;
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