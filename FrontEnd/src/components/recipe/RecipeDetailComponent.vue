<template>
    <div class="section">
        <div>
            <v-icon icon="mdi-chevron-left" style="font-size: 50px; cursor: pointer;" @click="goBack">
            </v-icon>
        </div>
        <!-- 조리순서 제외한 정보 -->
        <div class="flex flex-row w-full" style="padding-left: 10%; margin-top: 2%;">
            <img referrerpolicy="no-referrer" :src="recipeData.imageUrl" style="width: 360px; height: 300px;">
            <!-- 이미지 제외한 정보 -->
            <div class="ml-[8%] w-full flex flex-col">
                <div class="flex flex-row justify-between w-full">
                    <div class="font-extrabold text-[28px]">{{ recipeData.name }}</div>
                    <div class="flex flex-col items-center">
                        <div>
                            <v-icon v-if="heartCheck && email !== null" @click="checkcheck()"
                                style="cursor: pointer; color: #FF4081;" icon="mdi-heart">
                            </v-icon>
                            <v-icon v-if="!heartCheck && email !== null" @click="checkcheck()"
                                style="cursor: pointer; color: #FF4081;" icon="mdi-heart-outline">
                            </v-icon>
                        </div>
                        <div v-if="email !== null" class="text-center text-[14px]">{{ recipeData.likes }}
                        </div>
                    </div>
                </div>
                <div class="whitespace-pre-wrap mt-6 text-[#4F4F4F] w-full">{{ recipeData.info }}</div>

                <div class="window-frame">
                    <!-- 재료 제외한 정보 -->
                    <div>
                        <div class="quadrant top-left">
                            <v-icon icon=" mdi-creation-outline"
                                style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                            <div class="frame-title">난이도</div>
                            <div class="whitespace-pre-wrap text-[#4F4F4F] text-sm ">{{ recipeData.difficulty }}</div>
                        </div>
                        <div class="quadrant top-right">
                            <v-icon icon="mdi-clock-time-four-outline"
                                style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                            <div class="frame-title">소요 시간</div>
                            <div class="whitespace-pre-wrap text-[#4F4F4F] text-sm ">
                                30분(임의)
                                <!-- {{ recipeData.cookTime }} -->
                            </div>
                        </div>

                        <div class="quadrant bottom-left">
                            <v-icon icon="mdi-gas-burner" style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                            <div class="frame-title">
                                조리 방법
                            </div>
                            <div class="whitespace-pre-wrap text-[#4F4F4F] text-sm ">
                                {{ recipeData.cook }}
                            </div>
                        </div>
                        <div class="quadrant bottom-right">
                            <v-icon icon="mdi-account-multiple"
                                style="font-size: 30px; margin-bottom: 3px; margin-top: 3px">
                            </v-icon>
                            <div class="frame-title">분량</div>
                            <div class="whitespace-pre-wrap text-[#4F4F4F] text-sm ">
                                1인분(임의)
                                <!-- {{ recipeData.servings }} -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="w-full mt-3 ml-5">
                    <div class="frame-title">
                        재료
                    </div>
                    <div class="flex flex-row flex-wrap justify-items-start " style="white-space: wrap;">
                        <div v-for="(ingredient, index) in ingredients" :key="index">
                            <div class="mr-3 w-fit">{{ ingredient }}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 조리 순서 -->
        <div class="pl-[10%] w-full mt-20">
            <div class="font-semibold text-[28px] text-center m-10">조리 순서</div>
            <div v-for="step in recipeData.recipeDetailList" :key="step.id" class="w-full">
                <div class="flex flex-row items-center justify-between my-10">
                    <div class="flex flex-row items-center w-2/3">
                        <div
                            class="font-semibold text-[18px] text-center bg-lime-500 w-[40px] h-[40px] rounded-full flex justify-center items-center">
                            {{ step.recipe_order }}
                        </div>
                        <div class="font-medium text-[18px] ml-3 text-pretty w-[660px]">
                            {{ step.info }}
                            <!-- 가지의 꼭지 부분은 자르고 통으로 두께 1~2cm 크기로 자른다.가지의 꼭지 부분은 자르고 통으로 두께 1~2cm 크기로 자른다. -->
                        </div>
                    </div>
                    <div class="flex justify-end w-1/3">
                        <img referrerpolicy="no-referrer" :src="step.image_url" alt="Recipe Image"
                            style="width: 300px; height: 200px;" class="rounded-md" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import * as Recipe from '@/js/Recipe';
import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const route = useRoute()
const router = useRouter()
const recipeData = ref([])
const ingredients = ref([]);
const userStore = useUserStore()
const email = ref(null)
const recipeId = route.params.recipeId;

const goBack = () => {
    router.back();
}

const heartCheck = ref(false)

function checkcheck() {
    Recipe.likeRecipe(recipeId, email.value,
        (success) => {
            heartCheck.value = !heartCheck.value
            Recipe.getRecipeById(
                recipeId,
                (success) => {
                    recipeData.value = success.data;
                },
                (error) => {
                    console.error(error);
                }
            );
        },
        (error) => {
            console.error(error)
        })
}


onMounted(() => {
    email.value = userStore.email
    Recipe.getRecipeById(
        route.params.recipeId,
        (success) => {
            recipeData.value = success.data
            ingredients.value = recipeData.value.ingredients?.replace(/\[([^\]]+)\]/g, '|').split("|").slice(1)
        },
        (error) => {
            console.error(error)
        }
    )
    if (email.value) {
        Recipe.isRecipeLiked(recipeId, email.value,
            (success) => {
                heartCheck.value = success.data
            },
            (error) => {
                console.error(error)
            })
    }
})
</script>

<style scoped>
.section {
    display: flex;
    justify-content: left;
    width: 1168px;
    margin-top: 15%;
    white-space: nowrap;
    flex-direction: column;
    font-family: 'Noto Sans KR', sans-serif;
}

.window-frame {
    position: relative;
    margin-top: 5%;
    width: 100%;
    height: 230px;
    border-block: 1px solid #E0E0E0;
}

.frame-title {
    font-weight: bold;
    margin-bottom: 5px;
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
</style>