import { defineStore } from "pinia";
import { computed, ref } from "vue";

export const useUserStore = defineStore('user', () => {
    const email = ref(localStorage.getItem('email'))
    const nickname = ref(localStorage.getItem('nickname'))
    const id = ref(localStorage.getItem('id'))
    const image_url = ref(localStorage.getItem('image'))

    function updateInfo(e, n, u, i) {
        email.value = e
        nickname.value = n
        id.value = u
        image_url.value = i
    }

    return { email, nickname, id, image_url, updateInfo, }
})