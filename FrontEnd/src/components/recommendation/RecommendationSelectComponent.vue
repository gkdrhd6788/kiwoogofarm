<template>
  <div class="flex justify-center items-center flex-col">
    <h2 class="text-3xl mt-40 font-Notosans font-bold">농작물을 키울 장소를 선택해주세요</h2>
    <div class="flex flex-row px-10 mt-14">

<!--      실내 공간 버튼-->
      <div class="flex flex-col cursor-pointer items-center bg-white w-64 h-80 rounded-md shadow-[rgba(0,_0,_0,_0.25)_0px_4px_15px_0px]"
           :class="{ 'border-2 border-[#00B564]': selectedOption === 1 }"
           @click="selectOption(1)">
        <img class="w-48 h-48 mt-5" alt="" src="@/assets/inside.jpg">
        <div class='font-Notosans mt-3 text-xl font-semibold rotate-[-0.03deg]'>실내 공간</div>
        <div class='text-center mt-2 font-Notosans text-sm rotate-[-0.03deg] text-[#666666]'>집 안, 실내농장</div>
      </div>

<!--      실외 공간 버튼-->
      <div class="flex flex-col cursor-pointer items-center bg-white w-64 h-80 ml-24 rounded-md shadow-[rgba(0,_0,_0,_0.25)_0px_4px_15px_0px]"
           :class="{ 'border-2 border-[#00B564]': selectedOption === 2 }"
           @click="selectOption(2)">
        <img class="w-64 h-48 mt-4" alt="" src="@/assets/outsie.jpg">
        <div class='font-Notosans mt-3 text-xl font-semibold rotate-[-0.03deg]'>실외 공간</div>
        <div class='text-center mt-2 font-Notosans text-sm rotate-[-0.03deg] text-[#666666]'>베란다, 테라스, 텃밭</div>
      </div>
    </div>

<!--    다음 버튼-->
    <router-link :to="`/recommendsurvey/${selectedOption}`" v-show="selectedOption !== 0">
      <div class="rounded-full bg-[#00B564] px-24 py-3 mt-16 text-lg font-Notosans font-bold text-white">다음</div>
    </router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const selectedOption = ref(0);

const selectOption = (option) => {
  selectedOption.value = option;
};
</script>
