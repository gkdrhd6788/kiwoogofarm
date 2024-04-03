<template>
  <div id="gardens" class="section">
    <div class="font-Notosans mt-16 absolute left-96">
      <div class="font-bold  text-left text-4xl">내 주변 텃밭 찾기</div>
      <div class="font-bold text-xl  text-left mt-20">텃밭에서 농작물을 키워보고 싶다면</div>
      <div class="font-light text-lg  text-left mt-3">근처에 있는 텃밭을 찾아 분양을 신청해보세요.<br>
        해당 텃밭의 토양 정보와 최적 작물에 대한<br>
        정보도 함께 얻을 수 있습니다.</div>
      <RouterLink to="/garden" class="bg-[#00B564] rounded-full w-48 font-semibold text-xl text-white h-14 mt-16 flex justify-center items-center ">
        시작하기
      </RouterLink>
    </div>
    <div  class="w-96 object-fill h-72 absolute right-80" >
      <img  src="@/assets/garden1.png" alt="" style="width: 100%; height: 100%;" >
    </div>
    <div  class="w-96 object-fill h-72 absolute right-12 bottom-24" >
      <img  src="@/assets/garden2.jpg" alt="" style="width: 100%; height: 100%;" >
    </div>


  </div>
</template>

<script>
import { RouterLink } from 'vue-router'

export default {
  components: {
    RouterLink
  }
}
</script>
<script setup>

</script>

<style scoped>

</style>