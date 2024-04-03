import { localaxios } from "./BaseAxios";

const local = localaxios();

async function getUserinfo(email, success, fail) {
    await local.get(`/mypage/${email}`)
        .then((response) => {
            success(response);
        })
        .catch((error) => {
            fail(error);
        });
}

export {
    getUserinfo,
}