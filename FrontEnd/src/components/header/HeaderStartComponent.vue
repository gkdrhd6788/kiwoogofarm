<template>
    <router-link :to="{ name: 'home'}">
        <img src="@/assets/키우고팜logo.png" alt="로고 이미지" class="logo__img">
    </router-link>
</template>

<script setup>

</script>

<style scoped>
.logo__img {
    margin-top: 20px;
    height: 90px;
    -ms-user-select: none;
    -moz-user-select: -moz-none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    user-select: none;
  }
</style>