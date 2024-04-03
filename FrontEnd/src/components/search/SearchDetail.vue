<template>
    <div class="section">
        <div>
            <v-icon icon="mdi-chevron-left" style="font-size: 50px; cursor: pointer;" @click="goBack">
            </v-icon>
        </div>
        <div class="section-1" style="margin-left: 10%; margin-top: 2%;">
            <div>
                <div style="width: 360px; height: 300px;">

                    <img referrerpolicy="no-referrer" :src="cropData.imageUrl"
                        style="width: 100%; height: 100%; object-fit: cover;">
                </div>
            </div>

            <div class="crop">
                <div class="flex flex-row justify-between w-full">
                    <div class="crop-title">
                        {{ cropData.name }}
                    </div>
                    <div v-if="email" class="flex flex-col items-center">
                        <div>
                            <v-icon v-if="heartCheck" @click="checkcheck()" style="cursor: pointer; color: #FF4081;"
                                icon="mdi-heart">
                            </v-icon>
                            <v-icon v-else @click="checkcheck()" style="cursor: pointer; color: #FF4081;"
                                icon="mdi-heart-outline">
                            </v-icon>
                        </div>
                        <div class="text-center text-[14px]">{{ cropData.likes }}</div>
                    </div>
                </div>
                <div class="whitespace-pre-wrap mt-6 text-[#4F4F4F] ">
                    <div>

                        {{ cropData.cropInfo }}
                    </div>
                </div>

                <div class="window-frame">
                    <div class="quadrant top-left">
                        <div>
                            <v-icon icon="mdi-watering-can-outline"
                                style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                        </div>
                        <div class="frame-title">
                            물주기
                        </div>
                        <div class="whitespace-pre-wrap text-[#4F4F4F] text-sm ">
                            {{ cropData.waterPeriodInfo }}
                        </div>
                    </div>
                    <div class="quadrant top-right">
                        <div>
                            <v-icon icon="mdi-white-balance-sunny"
                                style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                        </div>
                        <div class="frame-title">
                            일조량
                        </div>
                        <div class="whitespace-pre-wrap  text-[#4F4F4F] text-sm">
                            {{ cropData.sunshineInfo }}
                        </div>
                    </div>
                    <div class="quadrant bottom-left">
                        <div>
                            <v-icon icon="mdi-water" style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                        </div>
                        <div class="frame-title">
                            습도
                        </div>
                        <div class="whitespace-pre-wrap  text-[#4F4F4F] text-sm">
                            {{ cropData.humidityInfo }}
                        </div>
                    </div>
                    <div class="quadrant bottom-right">
                        <div>
                            <v-icon icon="mdi-thermometer-lines"
                                style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                        </div>
                        <div class="frame-title">
                            온도
                        </div>
                        <div class="whitespace-pre-wrap text-[#4F4F4F] text-sm">
                            {{ cropData.temperatureInfo }}
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="healthy-section">
            <div class="healthy-section-subtitle">
                기능성
            </div>
            <div class="healthy-section-title">
                건강상의 유익성
            </div>
            <div style="margin-top: 2%;">
                <img src="@/assets/healthy.png" alt="">
            </div>
            <div style="font-size: 17px; margin-top: 5%; width: 50%; white-space: wrap; text-align: center;">
                {{ cropData.effectInfo }}
            </div>
            <div style="margin-top: 5%;">

            </div>
            <div class="healthy-section-subtitle">
                키우는 방법
            </div>
            <div class="healthy-section-title">
                자세히 알아보기
            </div>
            <div style="font-size: 17px; margin-top: 5%; margin-bottom: 5%; white-space: wrap; text-align: center;">
                <a :href="cropData.link" style="font-weight: bold; border-bottom: solid 1px black;">{{ cropData.link
                    }}</a>
            </div>

            <div class="flex flex-col items-center my-10">
              <div class="healthy-section-subtitle">
                레시피
              </div>
              <div class="healthy-section-title">
                {{ cropData.name }}를 활용한 추천 레시피
              </div>

                <div @click="router.push(`/recipe`)" class="self-end my-5 cursor-pointer flex flex-ro">
                    다양한 레시피 추천 받기
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-4 h-6 ml-1">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M17.25 8.25 21 12m0 0-3.75 3.75M21 12H3" />
                  </svg>

                </div>
                <div class="flex flex-row mt-3">
                    <div v-for="(recipe, index) in recipeAboutCrop.slice(0, 3)" :key="recipe.id">
                        <div class="flex flex-col items-center mx-6 cursor-pointer"
                            @click="router.push(`/recipedetail/${recipe.id}`)">
                            <v-avatar size="180">
                                <v-img :src="recipe.imageUrl" alt="recipes" class="object-cover"></v-img>
                            </v-avatar>
                            <div class="mt-3 text-lg">{{ recipe.name }}</div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup>
import * as Crop from '@/js/Crop';
import * as Recipe from '@/js/Recipe';
import { useUserStore } from '@/stores/user';
import { computed, onMounted, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const userStore = useUserStore()

const route = useRoute()
const router = useRouter()
const cropData = ref([])
const recipeAboutCrop = ref([])
const email = ref(null)

const heartCheck = ref(false)

const infoData = computed(() => ({
    cropId: route.params.cropId,
    like: heartCheck.value,
    email: email.value,
}))

const goBack = () => {
    router.back();
}

function checkcheck() {
    heartCheck.value = !heartCheck.value
    Crop.cropLike(
        route.params.cropId,
        email.value,
        (success) => {
            Crop.getCropById(
                route.params.cropId,
                (success) => {
                    cropData.value = success.data
                },
                (error) => {
                    console.error(error)
                }
            )
        },
        (error) => {
            console.error(error)
        }
    )
}

watch(() => userStore.email, (newVal) => {
    email.value = newVal
})

onMounted(() => {
    email.value = userStore.email

    Crop.getCropById(
        route.params.cropId,
        (success) => {
            cropData.value = success.data
        },
        (error) => {
            console.error(error)
        }
    )

    Recipe.getRecipeAboutCrop(
        route.params.cropId,
        (success) => {
            recipeAboutCrop.value = success.data
        },
        (error) => {
            console.error(error)
        }
    )
    if ( email.value !== null ) {
        Crop.cropLikedCheck(
            route.params.cropId,
            email.value,
            (success) => {
                heartCheck.value = success.data
            },
            (error) => {
                console.error(error)
            }
        )
    }
})

</script>

<style scoped>
.section {
    display: flex;
    justify-content: left;
    height: 100vh;
    margin-top: 15%;
    white-space: nowrap;
    flex-direction: column;
    font-family: 'Noto Sans KR', sans-serif;
}

.section-1 {
    display: inline-flex;
}

.healthy-section {
    margin-top: 10%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.healthy-section-subtitle {
    font-size: 12px;
    color: #00B564;
}

.healthy-section-title {
    margin-top: 1%;
    font-size: 20px;
    font-weight: 500;
}

.crop {
    margin-left: 8%;
}

.crop-title {
    font-weight: 700;
    font-size: 28px;
}

.window-frame {
    position: relative;
    margin-top: 5%;
    width: 100%;
    height: 300px;
    border-block: 1px solid #E0E0E0;
}

.window-frame::before,
.window-frame::after {
    content: '';
    position: absolute;
    background: #E0E0E0;
}

.window-frame::before {
    /* 세로선 */
    left: 50%;
    width: 1px;
    height: 100%;
}

.window-frame::after {
    /* 가로선 */
    top: 50%;
    height: 1px;
    width: 100%;
}

.quadrant {
    position: absolute;
    width: 50%;
    height: 50%;
    display: flex;
    flex-direction: column;
}

.top-left {
    top: 1%;
    left: 3%;
    padding-right: 5%;
}

.top-right {
    top: 1%;
    right: -3%;
    padding-right: 5%;
}

.bottom-left {
    bottom: -1%;
    left: 3%;
    padding-right: 5%;
}

.bottom-right {
    bottom: -1%;
    right: -3%;
    padding-right: 5%;
}

.frame-title {
    font-weight: bold;
    margin-bottom: 5px;
}
</style>

