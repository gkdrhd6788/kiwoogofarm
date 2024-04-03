<template>
    <div
        v-if="checkLogin"
        
    >
        <!-- logout -->
        <v-menu
            v-model="menu"
            transition="scale-transition"
            :close-on-content-click="false"
        >
            <template v-slot:activator="{ props }">
              <div v-bind="props" class="flex flex-row">
                <v-avatar size="40">
                  <v-img :src="image" alt="" class="object-cover"></v-img>
                </v-avatar>
                <div class="font-Notosans  text-lg ml-3 mt-1.5">{{ nickname }}</div>
              </div>
            </template>

            <v-card min-width="100" style="border-radius: 10%; margin-top: 10px">
                <v-list style="text-align: center;"  class="font-Notosans font-normal">
                    <v-list-item>
                        <div @click="router.push({ name: 'mypage' }), menu = false" class="cursor-pointer">
                           마이페이지
                        </div>
                    </v-list-item>

                    <v-list-item>
                        <div @click="logout" class="cursor-pointer">
                            로그아웃
                        </div>
                    </v-list-item>
                </v-list>
            </v-card>
        </v-menu>
    </div>
    <div
        v-else
    >
        <v-btn
            @click="openLoginModal"
        >
            LOGIN
        </v-btn>


        <LoginModal 
            :logins="logins"
            @update:logins="logins = $event"
        />
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import { useAuthStore } from '@/stores/auth';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
import LoginModal from '@/components/modal/LoginModal.vue'

const authStore = useAuthStore()
const userStore = useUserStore()
const router = useRouter()

const logins = ref(false)
const menu = ref(false)
const checkLogin = ref(false)
const email = ref(null)
const nickname = ref(null)
const id = ref(null)
const image = ref(null)

const logout = () => {
    authStore.clearToken()
  router.push({ path: '/' })
}

const openLoginModal = () => {
    logins.value = !logins.value
}


watch(() => authStore.isAuthenticated, (newVal, oldVal) => {
    checkLogin.value = newVal
})

watch(() => userStore.email, (newVal) => {
    email.value = newVal
})
 
watch(() => userStore.nickname, (newVal) => {
    nickname.value = newVal
})
 
watch(() => userStore.id, (newVal) => {
    id.value = newVal
})

watch(() => userStore.image_url, (newVal) => {
    image.value = newVal
})
 
    

onMounted(() => {
    checkLogin.value = authStore.isAuthenticated
    nickname.value = userStore.nickname
    email.value = userStore.email
    id.value = userStore.id
    image.value = userStore.image_url
})

</script>

<style scoped>

</style>