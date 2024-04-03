<template>
    <div id="recommendation" class="section">
        <div class="recommendation-box">

            <div v-if="userId!=null" id="box-title">
                <p id="nickname">{{nickname}}</p>
                <p>님을 위한 농작물 추천</p>
            </div>
            <div v-else id="box-title">
              <p>좋아요 수 Top3 농작물</p>
            </div>

            <div v-if="userId!=null" class="mb-16 text-[#8D8D8D]">좋아요를 누르신 농작물들과 유사한 농작물들입니다</div>
            <div v-else class="mb-16 text-[#8D8D8D]"></div>

            <v-row
                cols="4"
                class="recommendation-vegetable-list delay-animate"
                style="justify-content: space-evenly; width: 60%; margin-bottom: 3%;"
            >
                <v-col v-for="(crop, index) in cropRecommends" :key="index" style="margin-left: 5%;">
                    <div style="display: flex; justify-content: center;" class="cursor-pointer" @click="router.push(`/search/${crop.id}`)">
                      <v-avatar size="160">
                        <v-img :src="crop.image_url" alt="" class="object-cover"></v-img>
                      </v-avatar>
                    </div>
                    <div style="display: flex;  justify-content: center;" class="flex-col items-center">
                      <p class="font-Notosans font-medium text-xl mt-3">{{ crop.name }}</p>
                      <div class="flex flex-row mt-3">
                        <v-icon
                            @click="checkcheck"
                            style="cursor: pointer; color: #FF4081;"
                            icon="mdi-heart"
                        >
                        </v-icon>
                        <span class="ml-1 font-normal"> {{ crop.likes }}</span>
                      </div>
                    </div>

                </v-col>
            </v-row>

          <router-link to="/recommendation" class="recommendation-button delay-animate">
            더 많은 추천 받으러 가기 →
          </router-link>
        </div>
    </div>
</template>

<script setup>
import {onMounted,watch, ref} from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/auth.js";
import { useUserStore } from '@/stores/user'

const router = useRouter()
const authStore = useAuthStore();
const userStore = useUserStore();
const userId = ref(null);
const nickname = ref(null);
const cropRecommends = ref([]);

async function fetchCropRecommends(userId) {
  try {
    if(userId===null) userId = 0;
    const response = await axios.get(`https://j10b303.p.ssafy.io/recommendapi/crop/${userId}`);
    cropRecommends.value = response.data.recommended_crop;
  } catch (error) {
    console.error('Error fetching recipe favorites:', error);
  }
}

watch(() => userStore.id, (newVal) => {
  userId.value = newVal
})

watch(() => userStore.nickname, (newVal) => {
  nickname.value = newVal
})


onMounted(() => {
  userId.value = userStore.id;
  nickname.value = userStore.nickname;
  fetchCropRecommends(userId.value);
});


</script>

<style scoped>
.section #recommendation {
    justify-content: center;
    align-items: center;
    height: 100vh;
    position: absolute;
}

.recommendation-box {
    display: flex;
    margin-left: 10%;
    margin-top: 5%;
    width: 100vw;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-family: Noto Sans KR, sans-serif;
    
    
}

.recommendation-box #box-title {
    display: flex;
    font-size: 36px;
    margin-bottom: 8px;
    font-weight: 600;
}

.recommendation-box #box-title > p {
    margin-right: 10px;
}

.recommendation-box #box-title #nickname {
    color: #00B564;
    border-bottom: 3px solid #00B564;
}

.recommendation-button {
    color: #00B564;
    font-size: 24px;
    font-weight: 500;
    margin-left: auto;
    margin-right: 10%;
    width: fit-content;
}



.delay-animate {
    transition: opacity 0.5s ease-out;
    opacity: 0; /* 초기 상태, 보이지 않음 */
}

.aos-animate .recommendation-vegetable-list.delay-animate {
    opacity: 1;
    transition-delay: 1.1s; /* 첫 번째 딜레이 */
}

.aos-animate .recommendation-button.delay-animate {
    opacity: 1;
    transition-delay: 1.8s; /* 두 번째 딜레이 */
}

</style>