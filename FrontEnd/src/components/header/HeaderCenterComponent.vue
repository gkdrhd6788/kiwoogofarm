<template>
  <v-container>
      <v-row>
        <template
          v-for="(item, i) in items"
          :key="i"
      >
          <v-col
              cols="3"
              style="white-space: nowrap;"
              class="d-flex justify-center align-center"
          >
              <v-hover v-slot="{isHovering, props}">
                  <div 
                      class="v-text"
                      :elevation="isHovering ? 12 : 2"
                      :class="[{ 'on-hover': isHovering }, { 'active-header': item.name === checkHeader }]"
                      v-bind="props"
                      @click="router.push({ name: item.name })"
                      
                  >
                      {{ item.item }}
                  </div>
              </v-hover>
          </v-col>
      </template>
      </v-row>
    </v-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const checkHeader = computed(() => route.name)

const items = ([
{ item: '농작물 정보', name: 'search'},
{ item: '농작물 추천', name: 'recommendselect'},
{ item: '요리 추천', name: 'recipe'},
{ item: '내 주변 텃밭', name: 'garden'},
])

</script>

<style scoped>
.v-text {
  transition: opacity .25s ease-in-out;
  width: 55%;
  text-align: center;
  font-family: Noto Sans KR, sans-serif;
  font-weight: 700;
  font-size: 20px;
  line-height: 52px;
  cursor: pointer;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.v-text:after {
  content: '';
  display: block;
  margin-top: 5px;
  border-bottom: solid 3px #00B564;
  transform: scaleX(0);
  transition: transform .25s ease-in-out;
}

.v-text:hover:after, .v-text.on-hover:after, .active-header.v-text:after {
  transform: scaleX(1);
}

.v-text:not(.on-hover), .v-text:not(.active-header) {
  opacity: 0.4;
}

.v-text:hover, .v-text.on-hover, .active-header {
  opacity: 1 !important;
}

</style>