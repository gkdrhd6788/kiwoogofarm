<template>
    <div class="header-login">
        <LoginButton />
    </div>
</template>

<script setup>
import LoginButton from '@/components/buttons/LoginButton.vue'

</script>

<style scoped>
.header-login {
    display: flex;
    -ms-user-select: none;
    -moz-user-select: -moz-none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    user-select: none;
    cursor: pointer;
}


</style>