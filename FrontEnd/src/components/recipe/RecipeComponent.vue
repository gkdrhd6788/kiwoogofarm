<template>
  <div class="flex justify-center items-center flex-col">
    <h2 class="text-3xl mt-32 font-Notosans font-bold text-center">이걸로 만들 수 있는 요리는<br>무엇이 있을까?</h2>
    <img class="w-72 h-72 mt-5" alt="" src="@/assets/recipe_main_img.png">
    <div class="text-xl mt-8 text-center font-Notosans font-regular">
      특정 농작물을 활용한 다양한 요리를 안내해 드립니다.<br>
      요리 방법과 실력에 따라 더 자세한 추천을 제공합니다.
    </div>
    <router-link to="/recipeselect">
      <div class="rounded-full bg-[#00B564] px-24 py-3 mt-8  text-lg font-Notosans font-bold text-white">시작하기</div>
    </router-link>
  </div>
</template>

<script setup>

</script>
