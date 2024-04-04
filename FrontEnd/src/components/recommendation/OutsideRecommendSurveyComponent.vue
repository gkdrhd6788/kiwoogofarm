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
    <button @click="prevPage" v-if="currentPage > 1" class="absolute w-40 h-40 left-96">&lt; </button>

    <!--    첫번째 질문-->
    <div v-if="currentPage === 1" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(1/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">농작물을 키워본<br> 경험이 있나요?</span>
      <img alt="Survey1" class="mt-3 ml-6 w-96 h-80" src="../../assets/outsideImage1.jpg">

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['difficulty'].value === '쉬움' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('difficulty', '쉬움')">아니요, 처음입니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['difficulty'].value === '보통' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('difficulty', '보통')">많이는 아니지만, 소소하게 키워봤습니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['difficulty'].value === '어려움' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('difficulty', '어려움')">꽤 많이 키워봤습니다</div>

      <div class="py-10"></div>
    </div>

    <!--    두번째 질문-->
    <div v-if="currentPage === 2" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(2/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">어느 계절에 <br> 재배하실 계획이신가요?</span>
      <img alt="Survey2" class="mt-5 mb-3 w-96 h-80" src="@/assets/outsideImage2.jpg">

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['grow_start'].value === '봄' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_start', '봄')">봄</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['grow_start'].value === '여름' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_start', '여름')">여름</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['grow_start'].value === '가을' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_start', '가을')">가을</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['grow_start'].value === '겨울' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_start', '겨울')">겨울</div>

      <div class="py-10"></div>
    </div>

    <!--    세번째 질문-->
    <div v-if="currentPage === 3" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(3/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">어느 정도의 재배 기간을<br>고려하고 계신가요?</span>
      <img alt="Survey3" class="mt-5 w-96 h-72" src="@/assets/outsideImage3.jpg">

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['grow_time'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_time', '하')">단기 재배(1~3개월)</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['grow_time'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_time', '중')">중기 재배(3~6개월)</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['grow_time'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('grow_time', '상')">장기 재배(6개월 이상)</div>

      <div class="py-10"></div>
    </div>

    <!--    네번째 질문-->
    <div v-if="currentPage === 4" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(4/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">당신의 텃밭은<br>하루에 얼마나 햇빛을 받나요?</span>
      <img alt="Survey4" class="mt-8 w-80 h-72" src="@/assets/outsideImage4.jpg">

      <div
        class="cursor-pointer mt-8 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['sunshine'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('sunshine', '상')">6시간 이상 직접적인 햇빛을 받습니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['sunshine'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('sunshine', '중')">4~6시간 정도 햇빛을 받습니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['sunshine'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('sunshine', '하')">4시간 미만의 햇빛을 받습니다</div>

      <div class="py-10"></div>
    </div>

    <!--    다섯번째 질문-->
    <div v-if="currentPage === 5" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(5/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">일주일에 몇 번 <br>물을 주실수 있으신가요?</span>
      <img alt="Survey5" class="mt-3 w-80 h-80" src="../../assets/outsideImage5.jpg">

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['water_period'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_period', '하')">매일 줄 수 있습니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['water_period'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_period', '중')">2~3일에 한 번 줄 수 있습니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['water_period'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_period', '상')">주 1회정도만 가능합니다</div>

      <div class="py-10"></div>
    </div>

    <!--    여섯번째 질문-->
    <div v-if="currentPage === 6" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(6/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">토양의 물빠짐 정도는<br>어느 정도인가요?</span>
      <img alt="Survey6" class="mt-6 w-80 h-72" src="@/assets/outsideImage6.jpg">

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['water_exit'].value === '상' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_exit', '상')">물빠짐이 빠릅니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['water_exit'].value === '중' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_exit', '중')">보통의 물빠짐을 가집니다</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['water_exit'].value === '하' ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('water_exit', '하')">물빠짐이 느립니다</div>


      <div class="py-10"></div>
    </div>

    <!--    일곱번째 질문-->
    <div v-if="currentPage === 7" class="flex flex-col items-center justify-center">
      <span class="font-bold font-Notosans text-xl text-[#00B564]">(7/7)</span>
      <span class="mt-5 text-4xl font-bold text-center font-Notosans">수경재배를 활용하여<br>키우실 생각이신가요?</span>
      <img alt="Survey7" class="mt-6 w-72 h-72" src="@/assets/outsideImage7.jpg">

      <div
        class="cursor-pointer mt-6 rounded-full w-[400px] text-center py-5  text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['is_hydroponics'].value === 1 ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('is_hydroponics', 1)">네</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['is_hydroponics'].value === 0 ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('is_hydroponics', 0)">아니요</div>

      <div
        class="cursor-pointer mt-5 rounded-full w-[400px] text-center py-5 text-[#444444] font-Notosans font-medium text-base"
        :class="isSelected['is_hydroponics'].value === 0.5 ? 'bg-[#00B562] text-white' : 'bg-[#F6F6F3] text-[#444444]'"
        @click="handleClick('is_hydroponics', 0.5)">잘 모르겠습니다</div>

      <div class="py-10"></div>
    </div>

    <!--오른쪽 이동버튼-->
    <button @click="nextPage" v-if="currentPage < 7" class="absolute w-40 h-40 right-96"> &gt;</button>

    <!--추천 버튼-->
    <div>
      <button v-if="isAllSelected() && currentPage === 7" @click="handleRecommendation"
        class="absolute flex items-center justify-center w-40 h-40 text-xl font-bold cursor-pointer font-Notosans right-80">
        추천 받기
        <v-icon icon="mdi-arrow-right" start></v-icon>
      </button>
    </div>
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
  'grow_start': ref({ value: '0' }),
  'grow_time': ref({ value: '0' }),
  'sunshine': ref({ value: '0' }),
  'water_period': ref({ value: '0' }),
  'water_exit': ref({ value: '0' }),
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

// 모든 질문지에 대한 대답이 완료되었는지 체크하는 함수
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


// 추천 요청을 보내는 함수
const handleRecommendation = async () => {
  try {
    // isSelected 객체의 값을 추출하여 API 요청에 필요한 데이터로 변환
    const requestData = {
      liked_crops: [{
        "difficulty": isSelected.difficulty.value,
        "grow_start": isSelected.grow_start.value,
        "grow_time": isSelected.grow_time.value,
        "sunshine": isSelected.sunshine.value,
        "water_exit": isSelected.water_exit.value,
        "water_period": isSelected.water_period.value
      }]
    };

    // API 요청 보내기
    const response = await axios.post('/recommendapi/outsideCrop', requestData, {
      // 필요한 옵션 설정
    });

    // API 요청 성공 시 페이지 이동
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
      // 실패한 경우에 대한 처리 추가
    }
  } catch (error) {
    console.error('API 요청 중 오류 발생:', error);
    // 오류 발생 시에 대한 처리 추가
  }
}
</script>
