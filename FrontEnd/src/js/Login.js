import { localaxios } from "./BaseAxios";

const local = localaxios();

function loginKakao(success, fail) {
    local.get('/oauth2/authorization/kakao')
        .then(success)
        .catch(fail);
}

function loginNaver(success, fail) {
    local.get('/oauth2/authorization/naver')
        .then(success)
        .catch(fail)
}

function logout(success, fail) {
    local.get('/api/v1/users/logout')
        .then(success)
        .catch(fail)
}