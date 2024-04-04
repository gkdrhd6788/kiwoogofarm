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
    <button @click="prevPage" v-if="currentPage > 1" class="absolute w-40 h-40 left-96">&lt; </button>

    <!--    첫번째 질문-->
    <div v-if="currentPage === 1" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(1/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">농작물을 키워본<br>경험이 있나요?</span>
      <img alt="Survey1" class="mt-3 mb-3 w-80 h-80" src="../../assets/insideImage1.jpg">

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base !important"
        :class="isSelected['difficulty'].value === '쉬움' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('difficulty', '쉬움')">아니요, 처음입니다.</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['difficulty'].value === '보통' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('difficulty', '보통')">많이는 아니지만, 소소하게 키워봤습니다.</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['difficulty'].value === '어려움' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('difficulty', '어려움')">꽤 많이 키워봤습니다.</div>

      <div class="py-10"></div>
    </div>

    <!--    두번째 질문-->
    <div v-if="currentPage === 2" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(2/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">농작물을 키울 장소의<br>온도를 알려주세요</span>
      <img alt="Survey2" class="w-96 h-80" src="@/assets/insideImage2.jpg">

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 font-Notosans font-medium text-base"
        :class="isSelected['temperature'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('temperature', '상')"> 25°C 이상</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['temperature'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('temperature', '중')">16°C ~ 25°C</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['temperature'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('temperature', '하')">10°C ~ 16°C</div>
      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['temperature'].value === '최하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('temperature', '최하')">10°C 이하</div>

      <div class="py-10"></div>
    </div>

    <!--    세번째 질문-->
    <div v-if="currentPage === 3" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(3/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">어느 정도의 재배 기간을<br>고려하고 계신가요?</span>
      <img alt="Survey3" class="mt-6 w-80 h-80" src="../../assets/insideImage3.jpg">

      <div class="cursor-pointer mt-8 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['grow_time'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_time', '하')">단기 재배(1~3개월)</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['grow_time'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_time', '중')">중기 재배(3~6개월)</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 font-Notosans font-medium text-base"
        :class="isSelected['grow_time'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_time', '상')">장기 재배(6개월 이상)</div>

      <div class="py-10"></div>
    </div>

    <!--    네번째 질문-->
    <div v-if="currentPage === 4" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(4/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">농작물을 키울 장소의<br>하루 평균적인 햇빛 노출량은?</span>
      <img alt="Survey4" class="mt-8 w-80 h-80" src="@/assets/insideImage4.jpg">

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['sunshine'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('sunshine', '상')">6시간 이상</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['sunshine'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('sunshine', '중')">3시간에서 6시간 사이</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['sunshine'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('sunshine', '하')">3시간 미만</div>

      <div class="py-10"></div>
    </div>

    <!--    다섯번째 질문-->
    <div v-if="currentPage === 5" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(5/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">일주일에 몇 번<br>물을 주실수 있으신가요?</span>
      <img alt="Survey5" class="mt-8 w-80 h-80" src="@/assets/insideImage5.jpg">

      <div class="cursor-pointer mt-8 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['water_period'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_period', '하')">매일 줄 수 있습니다</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['water_period'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_period', '중')">2~3일에 한 번 줄 수 있습니다</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['water_period'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_period', '상')">주 1회정도만 가능합니다</div>

      <div class="py-10"></div>
    </div>

    <!--    여섯번째 질문-->
    <div v-if="currentPage === 6" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(6/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">농작물을 키울 장소의<br>습도는 어느 정도인가요?</span>
      <img alt="Survey6" class="mt-8 w-80 h-72" src="@/assets/insideImage6.jpg">

      <div class="cursor-pointer mt-8 rounded-full w-[400px] text-center py-5 font-Notosans font-medium text-base"
        :class="isSelected['humidity'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('humidity', '상')">매우 습합니다</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['humidity'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('humidity', '중')">습하지도 건조하지도 않습니다</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['humidity'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('humidity', '하')">건조합니다</div>

      <div class="py-10"></div>
    </div>

    <!--    일곱번째 질문-->
    <div v-if="currentPage === 7" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(7/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">수경재배를 활용하여<br>키우실 생각이신가요?</span>
      <img alt="Survey7" class="mt-8 w-80 h-80" src="@/assets/insideImage7.jpg">

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['is_hydroponics'].value === 1 ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('is_hydroponics', 1)">네</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['is_hydroponics'].value === 0 ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('is_hydroponics', 0)">아니요</div>

      <div class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  font-Notosans font-medium text-base"
        :class="isSelected['is_hydroponics'].value === 0.5 ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('is_hydroponics', 0.5)">잘 모르겠습니다</div>

      <div class="py-10"></div>
    </div>

    <!--    오른쪽 이동버튼-->
    <button @click="nextPage" v-if="currentPage < 7" class="absolute w-40 h-40 right-96"> &gt;</button>

    <!--    추천 받기 버튼-->
    <button v-if="isAllSelected() && currentPage === 7" @click="handleRecommendation"
      class="absolute flex items-center justify-center w-40 h-40 text-xl font-bold cursor-pointer font-Notosans right-80">
      추천 받기
      <v-icon icon="mdi-arrow-right" start></v-icon>
    </button>

  </div>
</template>

<script setup>
import { useRecommendationStore } from '@/stores/recommend';
import axios from "axios";
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const recommendationStore = useRecommendationStore();
const router = useRouter();
const isLoading = ref(false);
const currentPage = ref(1);
const isSelected = {
  'difficulty': ref({ value: '0' }),
  'temperature': ref({ value: '0' }),
  'grow_time': ref({ value: '0' }),
  'sunshine': ref({ value: '0' }),
  'water_period': ref({ value: '0' }),
  'humidity': ref({ value: '0' }),
  'is_hydroponics': ref({ value: 0 }),
};

const nextPage = () => {
  if (currentPage.value < 7) {
    currentPage.value++;
  }
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const isAllSelected = () => {
  for (let key in isSelected) {
    if (isSelected[key].value.value === '0') {
      return false;
    }
  }
  return Object.values(isSelected).every(item => item.value.value !== '0');
};

const handleClick = (index, value) => {
  isSelected[index].value = value;
  setTimeout(() => {
    if (currentPage.value < 7) {
      nextPage();
    }
  }, 125);
};

const handleRecommendation = async () => {
  try {
    const requestData = {
      liked_crops: [{
        "difficulty": isSelected.difficulty.value,
        "temperature": isSelected.temperature.value,
        "grow_time": isSelected.grow_time.value,
        "sunshine": isSelected.sunshine.value,
        "humidity": isSelected.humidity.value,
        "water_period": isSelected.water_period.value
      }]
    };
    const response = await axios.post('/recommendapi/insideCrop', requestData);
    if (response.status === 200) {
      recommendationStore.setRecommendationData(response.data.recommended_crop);
      isLoading.value = true;
      setTimeout(() => {
        router.push({
          name: 'recommendresult',
        });
      }, 3000);
    } else {
      console.error('API 요청 실패:', response.status);
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
  }
}
</script>
