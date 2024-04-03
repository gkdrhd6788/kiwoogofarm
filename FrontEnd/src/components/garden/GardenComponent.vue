<template>
  <div class="section">

      <div class="flex flex-row pt-[10%] text-xl ml-8 font-Notosans">
        <input @keyup.enter="searchLocation" type="text" v-model="searchQuery" class="w-56 h-8 bg-[#F0F0F0] rounded-md text-sm pl-3" placeholder="지역명 검색" />
        <div class="flex flex-row text-sm cursor-pointer ml-8 mt-0.5 " @click="moveToSavedLocation">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15 10.5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
            <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1 1 15 0Z" />
          </svg>
          <span class="mt-0.5 ml-1">내 위치</span>
        </div>
      </div>

    <!--지도-->
    <div id="map" class="absolute left-0 top-[25%] w-full h-[75%] z-10"></div>

    <!--정보창-->
    <div v-if="isDivVisible" class="absolute top-[25%] right-0 w-[23%] h-[80%] bg-white z-20 font-Notosans">
      <img alt="" src="@/assets/close.svg" class="h-8 w-8 absolute right-3 top-3 cursor-pointer" @click="handleCloseClick">
      <div class="flex  w-full h-[30%] bg-blue-200 ">
        <img alt="" src="@/assets/garden.png" class="object-cover w-full h-full">
      </div>

      <div class="flex flex-col items-center">
        <span class="font-bold text-xl mt-6">{{ gardenName }}</span>
        <span class="font-light text-base text-[#696969] mt-3">{{ gardenAddress }}</span>
      </div>

      <div class="flex flex-col ml-[15%]">
        <div class="flex flex-row mt-8">
          <span class="font-medium text-base">운영주체</span>
          <span class="font-light text-[#696969] text-base ml-6">{{ gardenOwner }}</span>
        </div>
        <div class="flex flex-row mt-4">
          <span class="font-medium text-base">부대시설</span>
          <span class="font-light text-[#696969] text-base ml-6">{{ gardenExtra }}</span>
        </div>
        <div class="flex flex-row mt-4">
          <span class="font-medium text-base">신청방법</span>
          <span class="font-light text-[#696969] text-base ml-6">{{ gardenApply }}</span>
        </div>
        <div class="flex flex-row mt-4">
          <span class="font-medium text-base">분양면적</span>
          <span class="font-light text-[#696969] text-base ml-6">{{ `${gardenSize}평` }}</span>
        </div>
      </div>

      <div class="flex flex-row mt-[12%] justify-center" >
        <div  class="cursor-pointer border-[1px] border-[#EC6B6B] text-[#EC6B6B]  rounded-lg w-28 h-12 flex flex-row items-center justify-center " @click="handleSaveClick(gardenId)">
          <img v-if="!isSave" alt="" src="@/assets/heart.svg" class="h-6 w-6 mr-1 ">
          <img v-else alt="" src="@/assets/fullheart.svg" class="h-6 w-6 mr-1" >
          찜하기
        </div>
        <div class="cursor-pointer ml-6 w-36 h-12 bg-[#86BF60] text-white  rounded-lg flex items-center justify-center" @click="openNewWindow">상세정보</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue';
export default {
  name: "test",
  setup() {
    const userStore = useUserStore();

    const nickname = ref('');
    const email = ref('');
    const image = ref('');
    const isSave = ref(false);
    onMounted(() => {
      nickname.value = userStore.nickname;
      email.value = userStore.email;
      image.value = userStore.image_url;
    });
    const checkHeart = (gardenId) => {
      if(email.value===null){
        isSave.value = false;
        return;
      }
      return axios.get(`https://j10b303.p.ssafy.io/api/garden/${gardenId}/checkGardenIsLiked/${email.value}`)
          .then(response => {
            isSave.value = response.data;
          })
          .catch(error => {
            console.error('데이터를 가져오는 중 에러 발생:', error);
            throw error;
          });
    };
    const handleSaveClick = (gardenId) =>{
      const setHeart = (gardenId) => {
        if(email.value===null){
          alert("로그인 후 사용가능한 기능입니다")
          return;
        }
        return new Promise((resolve, reject) => {
          axios.post(`https://j10b303.p.ssafy.io/api/garden/${gardenId}/gardenfavorite/${email.value}`)
              .then(response => {
                resolve();
              })
              .catch(error => {
                console.error('데이터를 가져오는 중 에러 발생:', error);
                reject(error);
              });
        });
      };
      const promise = setHeart(gardenId);
      if (!promise) {
        return; // 아무 작업도 하지 않고 함수 종료
      }
      promise
          .then(() => {
            isSave.value = !(isSave.value);
          })
          .catch(error => {
            console.error('checkHeart 함수 실행 중 에러 발생:', error);
          });
    }

    return {
      nickname,
      email,
      image,
      isSave,
      checkHeart,
      handleSaveClick
    };
  },
  data() {
    return {
      map: null,
      markers: [],
      newMarkers: [],
      latitude: 0,
      longitude: 0,
      isDivVisible: false,
      gardenId: 0,
      gardenName: "",
      gardenOwner: "",
      gardenExtra: "",
      gardenAddress: "",
      gardenApply: "",
      gardenSize: 0,
      searchQuery: '',
    };
  },
  mounted() {
    if (!("geolocation" in navigator)) {
      return;
    }

    // get position
    navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.latitude = pos.coords.latitude;
          this.longitude = pos.coords.longitude;

          if (window.kakao && window.kakao.maps) {
            this.initMap();
          } else {
            const script = document.createElement("script");
            script.onload = () => kakao.maps.load(this.initMap);
            script.src =
                "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=7d1ba57eb57e1922b3275d2bcb6791e8&libraries=services";
            document.head.appendChild(script);
          }
        },
        (err) => {
          alert(err.message);
        }
    );

    this.fetchData();
  },
  methods: {
    fetchData() {
      axios.get('https://j10b303.p.ssafy.io/api/garden/all')
          .then(response => {
            this.newMarkers = response.data;
            this.displayMarker(this.newMarkers.map(marker => [marker.gardenLat, marker.gardenLong]));
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },


    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.latitude, this.longitude),
        level: 6,
      };
      this.map = new kakao.maps.Map(container, options);

      this.displayMarker([[this.latitude, this.longitude]]);


    },

    moveToSavedLocation() {
      const center = new kakao.maps.LatLng(this.latitude, this.longitude);
      this.map.panTo(center); // 저장된 위치로 지도 이동
    },

    searchLocation() {
      if (this.searchQuery.trim() === '') {
        return;
      }

      const geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(this.searchQuery, (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          this.map.setCenter(coords); // 검색된 위치로 지도 이동
        } else {
          alert('검색 결과가 없습니다.');
        }
      });
    },

    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
        this.markers = [];
      }

      const positions = markerPositions.map(
          (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        this.markers = positions.map((position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions.reduce(
            (bounds, latlng) => bounds.extend(latlng),
            new kakao.maps.LatLngBounds()
        );

      }

      const imageSrc =
          "https://i.ibb.co/ch1GjBH/marker.png";

      for (let i = 0; i < this.newMarkers.length; i++) {
        let imageSize = new kakao.maps.Size(40, 54);
        let markerImage;
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        let marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(this.newMarkers[i].gardenLat, this.newMarkers[i].gardenLong),
          title: this.newMarkers[i].gardenName,
          image: markerImage,
        });

        kakao.maps.event.addListener(marker, "click", () => {
          // 클릭된 마커에 대한 처리를 수행하는 함수 호출
          this.handleMarkerClick(i);
        });
      }
    },


    handleMarkerClick(i) {
      const fetchGardenInfo = () => {
        return axios.get(`https://j10b303.p.ssafy.io/api/garden/${this.newMarkers[i].id}`)
            .then(response => {
              const gardenInfo = response.data;
              this.gardenName = gardenInfo.gardenName;
              this.gardenAddress = gardenInfo.gardenAddress;
              this.gardenId = gardenInfo.id;
              this.gardenOwner = gardenInfo.gardenOwner;
              this.gardenExtra = gardenInfo.gardenExtra;
              this.gardenApply = gardenInfo.gardenApply;
              this.gardenSize = gardenInfo.gardenSize;
            })
            .catch(error => {
              console.error('텃밭 정보를 가져오는 중 에러 발생:', error);
              throw error;
            });
      };



      fetchGardenInfo()
          .then(() => {
            return this.checkHeart(this.newMarkers[i].id);
          })
          .then(() => {
            this.isDivVisible = true;
            this.gardenId = this.newMarkers[i].id;
            this.gardenOwner = this.newMarkers[i].gardenOwner;
            this.gardenExtra = this.newMarkers[i].gardenExtra;
            this.gardenApply = this.newMarkers[i].gardenApply;
            this.gardenSize = this.newMarkers[i].gardenSize;
          })
          .catch(error => {
            console.error('처리 중 에러 발생:', error);
          });
    },


    handleCloseClick() {
    this.isDivVisible = false;
  },

  openNewWindow() {
    // 새 창 열기
    window.open(
        'http://soil.rda.go.kr/geoweb/soilInfoPopup.do?soilsign=SqC',
        'dialog',
        'modal=yes,dialog=yes,width=1000,height=600'
    );
  }
}


};
</script>

<style scoped>
.test {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
