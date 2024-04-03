<script setup>
import HeaderStartComponent from '@/components/header/HeaderStartComponent.vue';
import HeaderCenterComponent from '@/components/header/HeaderCenterComponent.vue';
import HeaderEndComponent from '@/components/header/HeaderEndComponent.vue';

</script>

<template>
  <v-app-bar elevation="0" class="header__appbar" :height="110">
    <!-- start section -->
    <div class="app-bar-section start">
      <HeaderStartComponent />
    </div>

    <!-- center section -->
    <div class="app-bar-section center">
      <HeaderCenterComponent />
    </div>

    <!-- end section -->
    <div class="app-bar-section end">
      <HeaderEndComponent />
    </div>
  </v-app-bar>
</template>


<style scoped>
  .header__appbar {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    position: fixed;
    width: 100%;
    height: auto !important;
    border-block: 1px solid rgba(0,0,0,0.1);
  }


  
  .app-bar-section {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: auto;
  }

  .app-bar-section.start{
   width: calc(100% / 3);
   margin-right: 3%;
  }

  .app-bar-section.end {
    width: calc(100% / 3);
    
  }



  .show-btns {
    color: rgba(255, 255, 255, 1) !important;
  }



</style>
