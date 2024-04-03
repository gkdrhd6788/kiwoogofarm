<template>
  <div class="flex flex-col items-center w-full h-full font-Notosans" style="margin-top: 10%;">
    <v-avatar size="80">
      <v-img alt="John" :src="image"></v-img>
    </v-avatar>
    <div class="mt-3 text-2xl font-medium">{{ nickname }}</div>
    <v-card class="w-full h-full mt-5">
      <v-tabs v-model="tab" align-tabs="center" color="#00B564" @change="handleChangeTab">
        <v-tab :value="1">찜한 농작물</v-tab>
        <v-tab :value="2">찜한 레시피</v-tab>
        <v-tab :value="3">찜한 텃밭</v-tab>
      </v-tabs>
      <v-window v-model="tab">
        <v-window-item v-for="n in 3" :key="n" :value="n">
          <v-container fluid class="h-full ">
            <v-row class="flex items-center justify-start h-full mt-5">
              <v-col v-for="(item, index) in items[n]" :key="index" cols="3">
                <div class="flex flex-col items-center my-4 cursor-pointer" @click="handleClick(n, item.id)">
                  <v-avatar size="180">
                    <v-img :src="getImageUrl(item)" alt="" class="object-cover"></v-img>
                  </v-avatar>
                  <div class="mt-3 text-lg">{{getName(item)}}</div>
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-window-item>
      </v-window>
    </v-card>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import axios from "axios";
import { onMounted, ref, watch } from 'vue';
import { useRouter } from "vue-router";

const userStore = useUserStore();
const router = useRouter();
const tab = ref(1);
const route = ref(null)

const recipeFavorites = ref([]);
const cropFavorites = ref([]);
const gardenFavorites = ref([]);
const nickname = ref(null);
const email = ref(null)
const image = ref(null);


function getImageUrl(item)  {
  return item.imageUrl || item.image_url || "https://img.freepik.com/premium-vector/plant-and-wooden-basket_188544-2920.jpg";
}

function getName(item)  {
  return item.name || item.gardenName || null;
}
const items = ref({ 1: cropFavorites, 2: recipeFavorites, 3: gardenFavorites });
// 찜한 농작물 조회 API
async function fetchCropFavorites() {
  try {
    const response = await axios.get(`https://j10b303.p.ssafy.io/api/crop/myFavorites/${email.value}`);
    cropFavorites.value = response.data;
  } catch (error) {
    console.error('Error fetching recipe favorites:', error);
  }
}
// 찜한 레시피 조회 API
async function fetchRecipeFavorites() {
  try {
    const response = await axios.get(`https://j10b303.p.ssafy.io/api/recipe/myFavorites/${email.value}`);
    recipeFavorites.value = response.data;
  } catch (error) {
    console.error('Error fetching recipe favorites:', error);
  }
}
async function fetchGardenFavorites() {
  try {
    const response = await axios.get(`https://j10b303.p.ssafy.io/api/garden/myGardenFavorites/${email.value}`);
    gardenFavorites.value = response.data;
  } catch (error) {
    console.error('Error fetching recipe favorites:', error);
  }
}
watch(() => userStore.nickname, (newVal) => {
  nickname.value = newVal
})

watch(() => userStore.email, (newVal) => {
  email.value = newVal
})

watch(() => userStore.image_url, (newVal) => {
  image.value = newVal
})

onMounted(() => {
  nickname.value = userStore.nickname;
  email.value = userStore.email;
  image.value = userStore.image_url;
  fetchRecipeFavorites();
  fetchCropFavorites();
  fetchGardenFavorites();
});

function handleChangeTab(newTab) {
  tab.value = newTab;
}

//router주소가 달라서 분기처리함
function handleClick(n, id) {
  if (n === 1) {
    route.value = `/search/${id}`
  } else if (n === 2) {
    route.value = `/recipedetail/${id}`
  } else {
  }
  router.push(route.value);
}
</script>
