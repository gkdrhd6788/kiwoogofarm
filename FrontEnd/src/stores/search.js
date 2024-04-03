import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useSearchStore = defineStore('search', () => {
    const searchBox = ref('')

    function setSearchBox(newVal) {
        try {
            searchBox.value = newVal
        } catch (error) {
            console.error("에러: ", error)
        }
    }
    return { searchBox, setSearchBox, }
})
