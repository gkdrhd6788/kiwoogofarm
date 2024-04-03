import { localaxios } from "./BaseAxios";

const local = localaxios()

async function getCrops(success, fail) {
    await local.get('/crop/all')
        .then(success)
        .catch(fail)
}

async function getCropById(id, success, fail) {
    await local.get(`/crop/${id}`)
        .then(success)
        .catch(fail)
}

async function getCropBySearch(name, success, fail) {
    await local.get(`/crop/search/${name}`)
        .then(success)
        .catch(fail)
}

async function cropRecommend(value, success, fail) {
    await local.post(`/crop/recommend`, JSON.stringify(value))
        .then(success)
        .catch(fail)
}

async function cropLikedCheck(cropId, email, success, fail) {
    await local.get(`/crop/checkCropIsLiked/${cropId}/${email}`)
        .then(success)
        .catch(fail)
}

async function cropLike(cropId, email, success, fail) {
    await local.post(`/crop/${cropId}/favorites/${email}`)
        .then(success)
        .catch(fail)
}

export {
    getCrops,
    getCropById,
    getCropBySearch,
    cropRecommend,
    cropLikedCheck,
    cropLike,
}