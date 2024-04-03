<template>
  <div v-if="isLoading" class="flex flex-col items-center justify-center">
    <div class="mt-72">
      <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
        style="margin: auto; background: transparent; display: block; shape-rendering: auto;" width="100px"
        height="100px" viewBox="0 0 100 100" preserveAspectRatio="xMidYMid">
        <circle cx="50" cy="50" fill="none" stroke="#00b564" stroke-width="10" r="35"
          stroke-dasharray="164.93361431346415 56.97787143782138">
          <animateTransform attributeName="transform" type="rotate" repeatCount="indefinite" dur="1s"
            values="0 50 50;360 50 50" keyTimes="0;1"></animateTransform>
        </circle>
      </svg>
    </div>
    <div class="mt-3 text-lg font-medium font-Notosans">분석중입니다..</div>
  </div>

  <div v-else class="flex flex-row items-center justify-center mt-40">
    <!--왼쪽 이동 버튼-->
    <button @click="prevPage" v-if="currentPage > 1"
      class="fixed w-40 h-40 font-medium font-bold left-button font-Notosans top-96 left-8">
      <v-icon icon="mdi-arrow-left"></v-icon> 이전
    </button>

    <!-- 첫번째 질문 -->
    <div v-if="currentPage === 1" class="flex flex-col items-center justify-center" style="width: 1100px;">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(1/3)</span>
      <span class="mt-3 text-4xl font-bold text-center font-Notosans" style="line-height: 1.3;">요리에 사용할<br>농작물을
        선택해주세요</span>
      <span class="mt-2 text-lg text-center font-Notosans font-medium text-[#666666]"> 중복 선택 가능합니다</span>
      <!-- <img alt="Survey1" class="mt-3 mb-3 w-80 h-80" src="../../assets/insideImage1.jpg"> -->

      <div class="flex flex-row items-center justify-between w-full">
        <!-- 선택된 재료 뱃지 -->
        <div class="flex flex-row flex-wrap justify-start w-3/4 gap-3 my-4 ml-24 mr-8 font-Notosans">
          <div v-for="selectedIngredient in isSelected.ingredients.value" :key="selectedIngredient">
            <v-chip variant="outlined" class="flex items-center justify-between">
              {{ selectedIngredient }}
              <v-icon @click="toggleIngredient(selectedIngredient)">mdi-close</v-icon>
            </v-chip>
          </div>
        </div>

        <!-- 검색창 -->
        <div class="self-end w-1/4 mr-12 ">
          <v-text-field v-model="searchText" prepend-inner-icon="mdi-magnify" variant="outlined" rounded label="농작물 검색"
            class="custom-label"></v-text-field>
          <!--          <v-text-field v-model="searchText" label="농작물 이름을 입력해 주세요." variant="solo-filled"-->
          <!--            prepend-inner-icon="mdi-magnify"></v-text-field>-->
        </div>
      </div>

      <!-- 재료 목록 -->
      <div class="grid grid-cols-5 my-6 gap-x-20 gap-y-5">
        <div v-for="crop in filteredCropData" :key="crop.id" class="flex flex-col items-center m-1 cursor-pointer"
          @click="
    toggleIngredient(crop.name)">
          <div
            :class="isSelectedIngredient(crop.name) ? 'ring-2 ring-green-500 rounded-full  p-0.5 relative' : 'rounded-full p-0.5 relative'">
            <div style="position: relative;">
              <img referrerpolicy="no-referrer" :src="crop.thumbnailUrl" alt="crop.name"
                class="object-cover rounded-full w-28 h-28"
                :class="isSelectedIngredient(crop.name) ? 'opacity-50 ' : ''" />
              <v-icon v-if="isSelectedIngredient(crop.name)" icon="mdi-check" class="text-green-500"
                style="font-size: 50px; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);"></v-icon>
            </div>
          </div>
          <div class="mt-2 text-center font-Notosans">{{ crop.name }}</div>
        </div>
      </div>
      <!-- 선택 시 좌측 상단에 선택된 농작물 목록 표시, X 버튼 클릭 시 선택한 농작물 취소 -->
      <!-- 우측 상단 검색바에서 농작물 이름 검색 -->

      <div class="py-10"></div>
    </div>

    <!-- 두번째 질문 -->
    <!-- 6개씩 가로 정렬, 사진이 원 내부에만 있도록 설정 -->
    <div v-if="currentPage === 2" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(2/3)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">조리 방법을 선택해주세요</span>

      <div class="grid max-w-6xl grid-cols-6 mt-12 text-center gap-x-12 gap-y-6">
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '굽기' ? ' border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '굽기')">
            <img src="../../assets/fry.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '굽기' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '굽기' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">굽기</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '끓이기' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '끓이기')">
            <img src="../../assets/boil.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '끓이기' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '끓이기' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">끓이기</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '데치기' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '데치기')">
            <img src="../../assets/dachim.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '데치기' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '데치기' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">데치기</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '무침' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '무침')">
            <img src="../../assets/muchim.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '무침' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '무침' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">무침</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '볶음' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '볶음')">
            <img src="../../assets/boggeum.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '볶음' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '볶음' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">볶음</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '부침' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '부침')">
            <img src="../../assets/buchim.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '부침' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '부침' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">부침</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '비빔' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '비빔')">
            <img src="../../assets/bibim.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '비빔' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '비빔' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">비빔</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '삶기' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '삶기')">
            <img src="../../assets/samgi.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '삶기' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '삶기' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">삶기</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '절임' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '절임')">
            <img src="../../assets/jullim.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '절임' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '절임' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">절임</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '조림' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '조림')">
            <img src="../../assets/jorim.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '조림' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '조림' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">조림</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '찜' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '찜')">
            <img src="../../assets/jjim.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '찜' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '찜' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">찜</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '튀김' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '튀김')">
            <img src="../../assets/fried.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '튀김' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '튀김' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">튀김</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '회' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '회')">
            <img src="../../assets/sushi.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '회' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '회' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">회</p>
        </div>
        <div class="text-base font-medium text-center cursor-pointer cook font-Notosans">
          <div class="relative overflow-hidden rounded-full image-container"
            :class="isSelected['cookingmethod'].value === '기타' ? 'border-[3px] border-[#00B564]' : 'opacity-100'"
            @click="handleClick('cookingmethod', '기타')">
            <img src="../../assets/cook.png" class="align-middle contain"
              :class="isSelected['cookingmethod'].value === '기타' ? 'opacity-50 ' : 'opacity-100'">
            <div class="absolute icon-container">
              <v-icon icon="mdi-check" class="check-icon"
                :class="isSelected['cookingmethod'].value === '기타' ? 'opacity-100' : 'opacity-0'"></v-icon>
            </div>
          </div>
          <p class="mt-3">기타</p>
        </div>
      </div>
      <div class="py-10"></div>
    </div>

    <!-- 세번째 질문 -->
    <!-- 가로 정렬 -->
    <div v-if="currentPage === 3 && (!isLoading)" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(3/3)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">조리 난이도를 선택해주세요</span>


      <div class="flex flex-row mt-12" style="justify-content: space-evenly; margin-bottom: 3%;">
        <div
          class="container h-72 cursor-pointer text-center font-Notosans font-medium text-base mx-5  rounded-md shadow-[rgba(0,_0,_0,_0.25)_0px_4px_15px_0px] outline-2 outline-gray-900"
          :class="isSelected['difficulty'].value === '아무나' ? 'border-solid border-2 border-[#00B564]' : 'opacity-100'"
          @click="handleClick('difficulty', '아무나')">
          <img src="../../assets/everyone2.png" class="mt-2">
          <p class="my-6">아무나</p>
        </div>


        <div
          class="container h-72 cursor-pointer text-center font-Notosans font-medium text-base mx-5 rounded-md shadow-[rgba(0,_0,_0,_0.25)_0px_4px_15px_0px]"
          :class="isSelected['difficulty'].value === '초급' ? 'border-solid border-2 border-[#00B564]' : 'opacity-100'"
          @click="handleClick('difficulty', '초급')">
          <img src="../../assets/beginner.png" class="mt-2">
          <p class="my-6">초급</p>
        </div>

        <div
          class="container h-72 cursor-pointer text-center font-Notosans font-medium text-base mx-5 rounded-md shadow-[rgba(0,_0,_0,_0.25)_0px_4px_15px_0px]"
          :class="isSelected['difficulty'].value === '중급' ? 'border-solid border-2 border-[#00B564]' : 'opacity-100'"
          @click="handleClick('difficulty', '중급')">
          <img src="../../assets/intermediate.png" class="mt-2">
          <p class="my-6">중급</p>
        </div>

        <div
          class="container h-72 cursor-pointer text-center font-Notosans font-medium text-base mx-5 rounded-md shadow-[rgba(0,_0,_0,_0.25)_0px_4px_15px_0px]"
          :class="isSelected['difficulty'].value === '고급' ? 'border-solid border-2 border-[#00B564]' : 'opacity-100'"
          @click="handleClick('difficulty', '고급')">
          <img src="../../assets/expert2.png" class="mt-2">
          <p class="my-6">고급</p>
        </div>

        <!-- <div
          class="container h-72 cursor-pointer text-center font-Notosans font-medium text-base mx-5 rounded-md shadow-[rgba(0,_0,_0,_0.25)_0px_4px_15px_0px]"
          :class="isSelected['difficulty'].value === '신의경지' ? 'border-solid border-2 border-[#00B564]' : 'opacity-100'"
          @click="handleClick('difficulty', '신의경지')">
          <img src="../../assets/god.png" class="rounded-t-md ">
          <p class="my-8">신의 경지</p>
        </div> -->
      </div>



      <div class="py-10"></div>
    </div>

    <!--    오른쪽 이동버튼-->
    <button @click="nextPage" v-if="currentPage < 3"
      class="fixed w-40 h-40 font-medium font-bold right-button font-Notosans top-96 right-8"> 다음
      <v-icon icon="mdi-arrow-right"></v-icon>
    </button>
  </div>
  <!--    레시피 추천 받기 버튼-->
  <div>
    <button v-if="isAllSelected() && currentPage === 3 && !isLoading" @click="handleRecipeRecommendation"
      class="absolute flex items-center justify-center text-lg font-light rounded-md cursor-pointer w-36 h-14 font-Notosans bottom-10 right-80 "
      :class="isSelected['difficulty'].value != '0' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'">
      <span class="mr-2">추천받기</span>
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor"
        class="w-6 h-6">
        <path strokeLinecap="round" strokeLinejoin="round" d="M17.25 8.25 21 12m0 0-3.75 3.75M21 12H3" />
      </svg>

    </button>


  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
    };
  },

  methods: {
    nextPage() {
      if (this.currentPage < 3) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
  },

};
</script>

<script setup>
import * as Crop from '@/js/Crop';
import * as Recipe from '@/js/Recipe';
import { useRecipeRecommendationStore } from "@/stores/recipeRecommend";
import { useSearchStore } from "@/stores/search";
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const searchStore = useSearchStore()
const recipeRecommendationStore = useRecipeRecommendationStore()
const isLoading = ref(false);
const router = useRouter();
//재료 선택 관련
const searchText = ref(searchStore.searchBox)
const cropData = ref([])

const filteredCropData = computed(() => {
  if (!searchText.value) {
    return cropData.value
  }
  return cropData.value.filter(crop => crop.name.includes(searchText.value))
})

onMounted(() => {
  Crop.getCrops(
    (success) => {
      cropData.value = success.data
    },
    (error) => {
      console.error(error)
    }
  )
})

const isSelected = {
  'ingredients': ref([]),
  'cookingmethod': ref({ value: '0' }),
  'difficulty': ref({ value: '0' }),
};

// 모든 질문지에 대한 대답이 완료되었는지 체크하는 함수
const isAllSelected = () => {
  for (let key in isSelected) {
    if (key == "ingredients") {
      if (isSelected['ingredients'].value.length === 0) {
        return false
      }
    }
    else {
      if (isSelected[key].value.value === '0') {
        return false;
      }
    }
  }
  return true;
};

//재료 선택 토글 함수
const toggleIngredient = (cropName) => {
  const index = isSelected['ingredients'].value.findIndex(ingredient => ingredient === cropName);
  if (index !== -1) {
    isSelected['ingredients'].value.splice(index, 1);
  } else {
    isSelected['ingredients'].value.push(cropName);
  }
  searchText.value = ''
}

//해당 재료가 선택되었는지 확인하는 함수
const isSelectedIngredient = (cropName) => {
  return isSelected['ingredients'].value.includes(cropName)
}

const handleClick = (index, value) => {
  isSelected[index].value = value;
};

// 요리 추천 요청을 보내는 함수
const handleRecipeRecommendation = async () => {
  try {
    // isSelected 객체의 값을 추출하여 API 요청에 필요한 데이터로 변환
    const recipeInfo = {
      ingredients: isSelected.ingredients.value,
      cook: isSelected.cookingmethod.value, // 요리 방법 정보가 없을 경우 빈 문자열 또는 null 등으로 설정합니다.
      difficulty: isSelected.difficulty.value // 난이도 정보가 없을 경우 빈 문자열 또는 null 등으로 설정합니다.
    };
    await Recipe.recommendRecipe(recipeInfo, (success) => {
      recipeRecommendationStore.setRecipeRecommendationData(success.data);
      isLoading.value = true;
      setTimeout(() => {
        router.push({
          name: 'reciperecommendresult',
        });
      }, 3000);
    }, (error) => {
      console.error('API 요청 실패', error)
    });
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    // 오류 발생 시에 대한 처리 추가
  }
}


</script>

<style scoped>
.cook>img {
  height: 200px;
  width: 200px;
  overflow: hidden;
}

.image-container {
  line-height: 136px;
  height: 136px;
  width: 136px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-container>img {
  object-fit: cover;
  height: 100%;
  width: auto;
}

.checkmark {
  height: 50px;
  width: 50px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #00B562;
}

.check-icon {
  font-size: 50px;
  color: #00B562;
}

.difficulty-container {
  height: 200px;
  width: 200px;
  overflow: hidden;
}

.container>img {
  height: 200px;
  width: 200px;
}
</style>