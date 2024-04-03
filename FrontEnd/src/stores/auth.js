import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { jwtDecode } from 'jwt-decode'
import * as MyPage from '@/js/MyPage';
import { useUserStore } from './user';

export const useAuthStore = defineStore('auth', () => {
    const userStore = useUserStore()
    const isAuthenticated = ref(!!localStorage.getItem('accessToken'))

    function setToken(accessToken, refreshToken) {
        try {
            const token = jwtDecode(accessToken)
            localStorage.setItem('email', token.email)
            localStorage.setItem('accessToken', accessToken)
            localStorage.setItem('refreshToken',  refreshToken)
            isAuthenticated.value = !!localStorage.getItem('accessToken')
            MyPage.getUserinfo(
                token.email,
                (success) => {
                    localStorage.setItem('nickname', success.data.nickname)
                    localStorage.setItem('id', success.data.id)
                    localStorage.setItem('image', success.data.image_url)
                    const email = localStorage.getItem('email')
                    const nickname = localStorage.getItem('nickname')
                    const id = localStorage.getItem('id')
                    const image = localStorage.getItem('image')
                    userStore.updateInfo(email, nickname, id, image)
                },
                (error) => {
                    console.error(error)
                }
            )
        } catch (error) {
            console.error(error)
        }
    }

    function clearToken() {
        try {
            localStorage.removeItem('accessToken')
            localStorage.removeItem('refreshToken')
            localStorage.removeItem('email')
            localStorage.removeItem('id')
            localStorage.removeItem('nickname')
            localStorage.removeItem('image')
            isAuthenticated.value = !!localStorage.getItem('accessToken')
            userStore.updateInfo(null, null, null, null)
        }   catch (error) {
            console.error(error)
        }
    }
    
    return { isAuthenticated, setToken, clearToken, }
})