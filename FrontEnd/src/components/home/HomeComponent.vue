<template>
  <div class="container">
    <aside class="sidebar">
      <nav class="navigation">
        <ul>
          <li 
              v-for="item in navItems"
              :key="item.id"
              @click="scrollToSection(item.id)"
              :class="{ 'active-nav': item.id === currentState.activeSectionId }"
          >
            <v-hover v-slot="{ isHovering }">
              <a>
                  <span></span>
              </a>

              <div
                class="nav-text"
                :class="[{ 'on-hover': isHovering }]"
              >
                  {{ item.title }}
              </div>
            </v-hover>
          </li>
        </ul>
      </nav>
    </aside>


    <v-container class="main-content">
      <HomeSearchSection />
      <HomeRecommendationSection />
      <HomeRecipesSection />
      <HomeGardensSection />
  </v-container>
</div>
</template>

<script setup>
import { onMounted, ref, reactive, onUnmounted } from 'vue';
import { useRoute } from 'vue-router';
import HomeSearchSection from './sections/HomeSearchSection.vue';
import HomeRecommendationSection from './sections/HomeRecommendationSection.vue';
import HomeRecipesSection from './sections/HomeRecipesSection.vue';
import HomeGardensSection from './sections/HomeGardensSection.vue';
import router from '@/router';

const route = useRoute();

const sections = ref([]);

const currentState = ref({
  activeSectionId: null,
  isScrolling: false,
});

const navItems = ref([
  { id: 'search', title: '농작물 검색' },
  { id: 'recommendation', title: '농작물 추천 받기' },
  { id: 'recipes', title: '레시피' },
  { id: 'gardens', title: '내 주변 텃밭' },
])


const findSectionIndexById = (id) => sections.value.findIndex(section => section.id === id)

const moveToSection = (direction) => {
  if (currentState.value.isScrolling) return;

const currentIndex = findSectionIndexById(currentState.value.activeSectionId);
let targetIndex = currentIndex + direction;

if (targetIndex >= 0 && targetIndex < sections.value.length) {
    currentState.value.isScrolling = true;
    const targetSection = sections.value[targetIndex];
    scrollToSection(targetSection.id);
  }
}

const handleWheelEvent = (e) => {
  e.preventDefault();
  const direction = e.deltaY > 0 ? 1 : -1;
  moveToSection(direction);
};

const scrollToSection = (id) => {
  if (currentState.value.activeSectionId === id) return;

  const section = document.getElementById(id);
  if (section) {
    resetAnimationStates();

    section.scrollIntoView({ behavior: 'smooth' });
    
    currentState.value.activeSectionId = id;

    window.setTimeout(() => {
      section.classList.add('aos-animate');
      currentState.value.isScrolling = false;
    }, 500);

    window.history.pushState(null, null, `#${id}`);
  }
};

const resetAnimationStates = () => {
  sections.value.forEach(section => {
    section.classList.remove('aos-animate');
  });
};

onMounted(() => {
  if (route.path === '/') {
    sections.value = Array.from(document.querySelectorAll('.section'))

    window.addEventListener('wheel', handleWheelEvent, { passive: false })

    if (window.location.hash) {
      window.location.replace(window.location.pathname)
    }


    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add('aos-animate');
          currentState.value.activeSectionId = entry.target.id;
        } else {
          entry.target.classList.remove('aos-animate');
        }
      });
    }, {
      rootMargin: '0px',
      threshold: 0.1
    });

    sections.value.forEach(section => {
      observer.observe(section);
    });
  }
});

onUnmounted(() => {
  window.removeEventListener('wheel', handleWheelEvent);
})

</script>



<style scoped>
.container {
  display: flex;
}

.sidebar {
  position: fixed;
  top: 50%;
  left: 8.5vw;
  transform: translateY(-50%);
  font-family: 'Noto Sans KR', sans-serif;
  font-weight: 500;
  font-size: 15px;
  width: 165px;
  overflow-y: auto;
  z-index: 1000;
  padding: 1em;
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.navigation ul li {
  display: flex;
  align-items: center;
  position: relative;
  margin: 14px 0;
  white-space: nowrap;
  opacity: 0.4;
  transition: color .4s ease-in-out, opacity .4s ease-in-out;
  padding: 0.5em 0;
  cursor: pointer;
}

.navigation ul li:hover,
.navigation ul li.on-hover,
.navigation ul li.active-nav {
  color: #00B564;
  opacity: 1 !important;
}


.navigation ul li a {
  display: flex;
  min-width: 35px;
  align-items: center;
  margin-right: 10px;
}

.navigation ul li a span {
  width: 35px;
}

.navigation ul li a span::after {
  content: '';
  display: block;
  border-bottom: solid 3px;
  transform: scaleX(0.5) scaleY(0.6);
  transform-origin: 0% 50%;
  transition: transform .4s ease-in-out;
}

.navigation ul li:hover a span::after,
.navigation ul li.active-nav a span::after {
  transform: scaleX(1) scaleY(1);
}

.main-content {
  flex: 1;
  max-width: max-content;
  padding: 1em;
}

.section {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  height: 100vh;
  opacity: 0;
  transition: opacity .5s ease-out .5s;
}

.aos-animate {
  opacity: 1;
}

</style>
