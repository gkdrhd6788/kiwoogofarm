<template>
    <div>
        <v-dialog
            v-model="props.logins"
            width="auto"
            height="420px"
            @click:outside="closeModal"
        >
            <v-card
                class="login-card"
                style="border-radius: 10px;"
            >   
                <template
                    v-slot:append
                >
                    <v-icon 
                        icon="mdi-close" 
                        style="cursor: pointer;"
                        @click="closeModal"
                    >
                    </v-icon>
                </template>

                <div
                    class="modal-text"
                >로그인
                </div>

                <v-sheet>
                    <v-card-text class="text-center">
                        <button @click="loginWithKakao">
                            <img src="@/assets/카카오_로그인.png" width="400" style="border-radius: 30px;"/>
                        </button>
                    </v-card-text>
                </v-sheet>
            </v-card>
            
        </v-dialog>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'

const props = defineProps({
    logins: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:logins'])

function closeModal() {
    emit('update:logins', false)
}

const loginWithKakao = () => {

    window.location.href = "http://j10b303.p.ssafy.io:8080/oauth2/authorization/kakao"
}


</script>

<style scoped>
.login-card {
    width: 500px;
    height: 340px;
    
}

.modal-text {
    text-align: center;
    font-family: 'Noto Sans KR', sans-serif;
    font-size: 26px;
    font-weight: 400;
    margin-top: 10%;
    margin-bottom: 12%;
}

</style>