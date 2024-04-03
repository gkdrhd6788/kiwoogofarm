<template>
  <div class="section">
    <p class="section-title">어떤 농작물을 찾으세요?</p>

    <div>
       <div>
         <v-btn
           variant="outlined"
           class="search-btn"
           rounded
           v-for="(search, i) in searchCheckBox"
           :key="i"
           :active="search.isActivate"
           @click="checkBox(i)"
         >
           {{ search.name }}
         </v-btn>
         <v-icon 
          v-if="showIcon(searchCheckBox)"
          icon="mdi-close" 
          style="cursor: pointer;"
          @click="resetCheckBox(searchCheckBox)"
        >
        </v-icon>
       </div>

       <p class="divider"></p>
      
      <div>
        <v-btn
          variant="outlined"
          class="search-btn"
          rounded
          v-for="(search1, i) in searchCheckBox2"
          :key="i"
          :active="search1.isActivate"
          @click="checkBox2(i)"
        >
          {{ search1.name }}
        </v-btn>
        <v-icon 
          v-if="showIcon(searchCheckBox2)"
          icon="mdi-close" 
          style="cursor: pointer;"
          @click="resetCheckBox(searchCheckBox2)"
        >
        </v-icon>
      </div>
      <div class="text-field-container">
        <v-text-field
          v-model="searchText"
          variant="plain"
          class="search-text"
          prepend-inner-icon="mdi-magnify"
          label="농작물 이름을 입력해 주세요."
          single-line
        >

        </v-text-field>
      </div>
      <div class="crop-container">
        <v-row>
          <v-col
            class="crop-col"
            cols="3"
            v-for="(crop, i) in filteredCropData"
            :key="i"
          >
            <div class="image-container">
              <img 
                referrerpolicy="no-referrer" 
                :src="crop.thumbnailUrl" 
                style="cursor: pointer;"
                @click="router.push(`/search/${crop.id}`)"
              >
            </div>
            <div>
              {{ crop.name }}
            </div>
          </v-col>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch, computed } from "vue"
import { useRoute, useRouter } from "vue-router"
import { useSearchStore } from "@/stores/search"
import * as Crop from '@/js/Crop';

const route = useRoute()
const router = useRouter()
const searchStore = useSearchStore()

const searchText = ref(searchStore.searchBox)
const cropData = ref([])

const filteredCropData = computed(() => {
  let tempFilteredData = searchText.value ? cropData.value.filter(crop => crop.name.includes(searchText.value)) : cropData.value;

  if (activeCategories.value.length > 0) {
    tempFilteredData = tempFilteredData.filter(crop => activeCategories.value.includes(crop.category))
  }

  searchCheckBox2.value.forEach(item => {
    if (item.isActivate) {
      switch (item.name) {
        case '초보자용':
          tempFilteredData = tempFilteredData.filter(crop => crop.difficulty === '쉬움');
          break;
        case '빛이 적어도 되는':
          tempFilteredData = tempFilteredData.filter(crop => crop.sunshine === '하');
          break;
        case '수경재배가 되는':
          tempFilteredData = tempFilteredData.filter(crop => crop.isHydroponics === 1);
          break;
        case '물주기가 긴':
          tempFilteredData = tempFilteredData.filter(crop => crop.waterPeriod === '상');
          break;
      }
    }
  });

  return tempFilteredData
})

const activeCategories = computed(() => {
  return searchCheckBox.value.filter(item => item.isActivate).map(item => item.category);
});

const searchCheckBox = ref([
{ name: '채소류', category: '채소', isActivate: false }, 
{ name: '콩류', category: '콩류', isActivate: false }, 
{ name: '서류', category: '서류', isActivate: false },
{ name: '곡류', category: '곡류', isActivate: false },
{ name: '과실류', category: '과실류', isActivate: false }, 
{ name: '특용작물', category: '특용작물', isActivate: false }, 
{ name: '화훼작물', category: '화훼작물', isActivate: false }, 
])

const searchCheckBox2 = ref([
{ name: '초보자용', isActivate: false },
{ name: '빛이 적어도 되는', isActivate: false },
{ name: '수경재배가 되는', isActivate: false },
{ name: '물주기가 긴', isActivate: false },
])

const checkBox = (i) => {
searchCheckBox.value[i].isActivate = !searchCheckBox.value[i].isActivate
}

const checkBox2 = (i) => {
searchCheckBox2.value[i].isActivate = !searchCheckBox2.value[i].isActivate
}

const resetCheckBox = (box) => {
box.forEach(item => item.isActivate = false)
}

const showIcon = (box) => {
if (box) {
  return box.some((item) => item.isActivate)
}
return false
}

onMounted(() => {
Crop.getCrops(
  (success) => {
    cropData.value = success.data
  },
  (error) => {
    console.error(error)
  }
)
})

</script>

<style scoped>
.section {
  display: flex;
  justify-content: left;
  height: 100vh;
  margin-top: 15%;
  white-space: nowrap;
  flex-direction: column;
  font-family: 'Noto Sans KR', sans-serif;
}

.section-title {
  margin-bottom: 5%;
  font-weight: 500;
  font-size: 40px;
}

button {
  font-weight: 400;
}

.text-field-container, .crop-container {
  margin-top: 3%;
}

.text-field-container {
  border-bottom: solid 1px black;
}

.search-btn {
  color: medium-emphasi;
  margin-inline: 8px;
}

.divider {
  border-bottom: solid 1px black;
  margin-block: 2%;
}

.search-text {
  height: 60px;
}

.crop-col {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.image-container {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

</style>
