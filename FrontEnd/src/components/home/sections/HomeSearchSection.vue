<template>
    <div id="search" class="section">
        <div class="search-box">
            <div
                style="display: flex; margin-bottom: 20px;"
            >
                <p 
                    :class="{ 'dynamic': userInput.length == 0 }" 
                    :style="{ left: userInput.length > 0 ? inputWidth + 'px' : '0px' }"
                >
                </p>
                <input 
                    id="input1" 
                    type="text" 
                    :value="userInput" 
                    :style="{ width: inputWidth + 'px' }"
                    @input="changeInput"
                    @keyup.enter="goSearch"
                />
                <p 
                    :class="{ 'dynamic': userInput.length > 0 }" 
                    :style="{ left: userInput.length > 0 ? inputWidth + 'px' : '0px' }"
                >
                </p>

                <p>를</p>

            </div>
            <div class="lines-container" v-for="(line, index) in lines" :key="index">
                <p>{{ line }}</p>
            </div>
        </div>
    </div>
    
</template>

<script setup>
import { ref, watch, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import { useSearchStore } from '@/stores/search'

const router = useRouter()
const searchStore = useSearchStore()

const userInput = ref('');
const inputWidth = ref(300);
const lines = ref(['잘 키우는 방법이', '궁금하다.']);

const goSearch = () => {
    if ( userInput.value ) {
        searchStore.setSearchBox(userInput.value)
        router.push( { name: 'search' })
    }
}

const calculateTextWidth = (text) => {
  const element = document.createElement('span');
  element.style.position = 'absolute';
  element.style.whiteSpace = 'pre';
  element.style.font = 'bold 45px Noto Sans KR, sans-serif';
  element.textContent = text;
  document.body.appendChild(element);
  const width = element.offsetWidth;
  document.body.removeChild(element);
  return width;
};

const changeInput = (e) => {
    userInput.value = e.target.value;
};

watch(userInput, async (newValue) => {
    await nextTick();
    const minWidth = 300;
    const maxWidth = 600;
    const textWidth = calculateTextWidth(newValue);

    inputWidth.value = newValue.length > 0 ? Math.min(textWidth + 20, maxWidth) : minWidth;
});

</script>

<style scoped>
.section #search{
    position: absolute;
}

.search-box {
    width: 20%;
    height: 20%;
    white-space: nowrap;
    display: flex;
    justify-content: center;
    flex-direction: column;
}

.search-box > div > p {
    font-family: Noto Sans KR, sans-serif;
    font-weight: 700;
    font-size: 50px;
}

@keyframes blink {
    0%, 100% { opacity: 1; }
    50% { opacity: 0; }
}

.lines-container {
  text-align: left;
  margin-bottom: 20px;
}

.dynamic::after {
    content: "|";
    animation: blink 1s step-start 0s infinite;
    
    top: 10px;
    left: 0;
    font-size: 50px;
    font-weight: 300;
    height: 20px;
    color: #00B564;
}

#input1 {
    width: 400px;
    height: 85px;
    border-top: none;
    border-left: none;
    border-right: none;
    outline: none;
    border-bottom: 3px solid black;
    font-size: 45px;
    color: #00B564;
    font-weight: bold;
    caret-color: transparent;
    padding-left: 10px;
}
</style>